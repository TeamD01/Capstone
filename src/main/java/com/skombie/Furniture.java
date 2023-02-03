package com.skombie;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Furniture implements Inspectable, Serializable {
    private String name;
    private String description;
    private boolean isLocked;
    private List<Item> items;
    private List<Weapon> weapons;

    public Furniture(String name, String description, boolean isLocked, List<Item> items, List<Weapon> weapons) {
        this.name = name;
        this.description = description;
        this.isLocked = isLocked;
        this.items = items;
        this.weapons = weapons;
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

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Furniture furniture = (Furniture) o;
        return isLocked() == furniture.isLocked() && getName().equals(furniture.getName()) && getDescription().equals(furniture.getDescription()) && getItems().equals(furniture.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), isLocked(), getItems());
    }

    @Override
    public String toString() {
        return "Furniture{" +
                "name='" + name + '\'' +
                ", isLocked=" + isLocked +
                ", items=" + items +
                '}';
    }
}