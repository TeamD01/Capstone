package com.skombie;

import java.util.Objects;

public class Weapon implements Inspectable{
    private String name;
    private String description;
    private double attackValue;

    public Weapon(String name, String description, double attackValue) {
        this.name = name;
        this.description = description;
        this.attackValue = attackValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(double attackValue) {
        this.attackValue = attackValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weapon weapon = (Weapon) o;
        return Double.compare(weapon.getAttackValue(), getAttackValue()) == 0 && getName().equals(weapon.getName()) && getDescription().equals(weapon.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getAttackValue());
    }

    @Override
    public String toString() {
        return "Weapon{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", attackValue=" + attackValue +
                '}';
    }
}