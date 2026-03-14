package com.narxoz.rpg.composite;

import java.util.ArrayList;
import java.util.List;

public class PartyComposite implements CombatNode {

    private String name;
    private List<CombatNode> children = new ArrayList<>();

    public PartyComposite(String name) {
        this.name = name;
    }

    public void add(CombatNode node) {
        children.add(node);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<CombatNode> getChildren() {
        return children;
    }

    @Override
    public int getHealth() {

        int total = 0;

        for (CombatNode child : children) {
            total += child.getHealth();
        }

        return total;
    }

    @Override
    public int getAttackPower() {

        int total = 0;

        for (CombatNode child : children) {
            if (child.isAlive()) {
                total += child.getAttackPower();
            }
        }

        return total;
    }

    @Override
    public boolean isAlive() {

        for (CombatNode child : children) {
            if (child.isAlive()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void takeDamage(int amount) {

        List<CombatNode> alive = new ArrayList<>();

        for (CombatNode child : children) {
            if (child.isAlive()) {
                alive.add(child);
            }
        }

        if (alive.isEmpty()) return;

        int split = amount / alive.size();
        if (split <= 0) split = 1;

        for (CombatNode child : alive) {
            child.takeDamage(split);
        }
    }

    @Override
    public void printTree(String indent) {

        System.out.println(indent + "+ " + name +
                " [HP=" + getHealth() +
                ", ATK=" + getAttackPower() + "]");

        for (CombatNode child : children) {
            child.printTree(indent + "  ");
        }
    }
}