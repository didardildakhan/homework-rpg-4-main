package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;

import java.util.Random;

public class RaidEngine {

    private Random random = new Random(1L);

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(
            CombatNode teamA,
            CombatNode teamB,
            Skill teamASkill,
            Skill teamBSkill) {

        if (teamA == null || teamB == null || teamASkill == null || teamBSkill == null) {
            throw new IllegalArgumentException("Teams and skills must not be null");
        }

        RaidResult result = new RaidResult();

        int rounds = 0;
        int maxRounds = 50;

        while (teamA.isAlive() && teamB.isAlive() && rounds < maxRounds) {

            rounds++;

            result.addLine("Round " + rounds);

            teamASkill.cast(teamB);
            result.addLine(teamA.getName() + " casts " + teamASkill.getSkillName());

            if (!teamB.isAlive()) {
                break;
            }

            teamBSkill.cast(teamA);
            result.addLine(teamB.getName() + " casts " + teamBSkill.getSkillName());
        }

        result.setRounds(rounds);

        if (teamA.isAlive() && !teamB.isAlive()) {
            result.setWinner(teamA.getName());
        } else if (teamB.isAlive() && !teamA.isAlive()) {
            result.setWinner(teamB.getName());
        } else {
            result.setWinner("Draw");
        }

        return result;
    }
}