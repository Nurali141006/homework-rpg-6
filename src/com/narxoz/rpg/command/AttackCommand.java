package com.narxoz.rpg.command;

import com.narxoz.rpg.arena.ArenaOpponent;

public class AttackCommand implements ActionCommand {

    private final ArenaOpponent target;
    private final int attackPower;
    private int damageDealt;

    public AttackCommand(ArenaOpponent target, int attackPower) {
        this.target = target;
        this.attackPower = attackPower;
        this.damageDealt = 0;
    }

    @Override
    public void execute() {
        int targetHealthBefore = target.getHealth();
        int actualDamage = Math.min(attackPower, targetHealthBefore);

        target.takeDamage(actualDamage);
        damageDealt = actualDamage;

        System.out.println("[Attack] " + target.getName() + " takes " + actualDamage +
                " damage. Remaining HP: " + target.getHealth());
    }
    @Override
    public void undo() {
        if (damageDealt > 0) {
            target.restoreHealth(damageDealt);
            System.out.println("[Undo Attack] Restored " + damageDealt +
                    " HP to " + target.getName() + ". Current HP: " + target.getHealth());
            damageDealt = 0;
        }
    }
    @Override
    public String getDescription() {
        return "Attack for " + attackPower + " damage on " + target.getName();
    }
}