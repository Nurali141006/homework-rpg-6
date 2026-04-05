package com.narxoz.rpg.command;

import com.narxoz.rpg.arena.ArenaFighter;

public class HealCommand implements ActionCommand {

    private final ArenaFighter target;
    private final int healAmount;
    private int actualHealApplied;

    public HealCommand(ArenaFighter target, int healAmount) {
        this.target = target;
        this.healAmount = healAmount;
        this.actualHealApplied = 0;
    }
    @Override
    public void execute() {
        if (target.getHealPotions() > 0) {
            int healthBefore = target.getHealth();
            target.heal(healAmount);
            actualHealApplied = target.getHealth() - healthBefore;

            System.out.println("[Heal] " + target.getName() + " restores " +
                    actualHealApplied + " HP. Current HP: " + target.getHealth() +
                    ". Potions left: " + target.getHealPotions());
        } else {
            actualHealApplied = 0;
            System.out.println("[Heal] " + target.getName() + " has no potions left!");
        }
    }
    @Override
    public void undo() {
        if (actualHealApplied > 0) {
            target.takeDamage(actualHealApplied);
            System.out.println("[Undo Heal] " + target.getName() +
                    " loses " + actualHealApplied + " HP from undo. Current HP: " +
                    target.getHealth());
            actualHealApplied = 0;
        }
    }
    @Override
    public String getDescription() {
        return "Heal for " + healAmount + " HP on " + target.getName();
    }
}