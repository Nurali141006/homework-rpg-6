package com.narxoz.rpg.tournament;

import com.narxoz.rpg.arena.ArenaFighter;
import com.narxoz.rpg.arena.ArenaOpponent;
import com.narxoz.rpg.arena.TournamentResult;
import com.narxoz.rpg.chain.*;
import com.narxoz.rpg.command.*;

import java.util.Random;

public class TournamentEngine {
    private final ArenaFighter hero;
    private final ArenaOpponent opponent;
    private Random random = new Random(1L);

    public TournamentEngine(ArenaFighter hero, ArenaOpponent opponent) {
        this.hero = hero;
        this.opponent = opponent;
    }
    public TournamentEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }
    public TournamentResult runTournament() {
        TournamentResult result = new TournamentResult();
        int round = 0;
        final int maxRounds = 20;
        DodgeHandler dodge = new DodgeHandler(hero.getDodgeChance(), random.nextLong());
        BlockHandler block = new BlockHandler(hero.getBlockRating() / 100.0);
        ArmorHandler armor = new ArmorHandler(hero.getArmorValue());
        HpHandler hp = new HpHandler();

        dodge.setNext(block).setNext(armor).setNext(hp);

        ActionQueue actionQueue = new ActionQueue();

        while (hero.isAlive() && opponent.isAlive() && round < maxRounds) {
            round++;
            actionQueue.enqueue(new AttackCommand(opponent, hero.getAttackPower()));
            actionQueue.enqueue(new HealCommand(hero, 15));     
            actionQueue.enqueue(new DefendCommand(hero, 0.1));   
            System.out.println("[Round " + round + "] Queued actions: " + actionQueue.getCommandDescriptions());

            actionQueue.executeAll();
            if (opponent.isAlive()) {
                System.out.println("[Round " + round + "] Opponent attacks!");
                dodge.handle(opponent.getAttackPower(), hero);
            }
            String logLine = "[Round " + round + "] Opponent HP: " + opponent.getHealth() +
                             " | Hero HP: " + hero.getHealth();
            System.out.println(logLine);
            result.addLine(logLine);
        }
        result.setWinner(hero.isAlive() ? hero.getName() : opponent.getName());
        result.setRounds(round);

        return result;
    }
}