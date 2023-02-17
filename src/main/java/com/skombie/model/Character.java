package com.skombie.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Character implements Speaker, Inspectable, Serializable {
    private String name;
    private String description;
    private List<String> dialogue;
    private boolean isAvailableMove;
    private boolean isDead;
    private double hitsCanTake;
    private int turnsInCombat = 0;
    //private final boolean isDeadDead = false;

    public Character(String name, String description, List<String> dialogue, boolean isAvailableMove, boolean isDead) {
        setName(name);
        setDescription(description);
        setDialogue(dialogue);
        setAvailableMove(isAvailableMove);
        setDead(isDead);
    }
    public int getTurnsInCombat() {
        return turnsInCombat;
    }

    public void setTurnsInCombat(int turnsInCombat) {
        this.turnsInCombat += turnsInCombat;
    }

    public boolean isAvailableMove() {
        return isAvailableMove;
    }

    public void setAvailableMove(boolean availableMove) {
        isAvailableMove = availableMove;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDialogue() {
        return dialogue;
    }

    private void setDialogue(List<String> dialogue) {
        this.dialogue = dialogue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return getName().equals(character.getName()) && getDescription().equals(character.getDescription()) && getDialogue().equals(character.getDialogue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getDialogue());
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dialogue=" + dialogue +
                '}';
    }

    @Override
    public String getRandomDialogue() {
        Random random = new Random();
        return dialogue.get(random.nextInt(dialogue.size()));
    }

    public double getHitsCanTake() {
        return hitsCanTake;
    }

    public void setHitsCanTake(double hitsCanTake) {
        this.hitsCanTake = hitsCanTake;
    }

}