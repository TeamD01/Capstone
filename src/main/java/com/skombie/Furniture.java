package com.skombie;

import java.util.List;
import java.util.Objects;

public class Furniture {
    private String name;
    private boolean isLocked;
    private List<Item> items;

    public Furniture(String name, boolean isLocked, List<Item> items) {
        this.name = name;
        this.isLocked = isLocked;
        this.items = items;
    }


    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public boolean isLocked() {
        return isLocked;
    }

    private void setLocked(boolean locked) {
        isLocked = locked;
    }

    public List<Item> getItems() {
        return items;
    }

    private void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Furniture furniture = (Furniture) o;
        return isLocked() == furniture.isLocked() && getName().equals(furniture.getName()) && getItems().equals(furniture.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), isLocked(), getItems());
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