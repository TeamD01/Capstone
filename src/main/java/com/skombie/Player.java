package com.skombie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Player implements Speaker {
    private boolean isDead = false;
    private double health = 100.0;
    private List<Item> inventory = new ArrayList<>();
    private Weapon currentWeapon = null;
    private final List<String> dialogue = new ArrayList<String>(){
        {
            add("What's going on");
            add("Hey, how you feeling today?");
            add("Hey, these skunks are really getting on my last nerve.");
        }
    };

    public Player() {
    }

    //action methods
    public String look(Inspectable item) {
        return item.getDescription();
    }

    public void get(Item item){
        inventory.add(item);
    }

    public void drop(Item item){
        inventory.remove(item);
    }

    public String[] talk(Character friend){
        return new String[]{

                String.format("YOU: %s\n", getRandomDialogue()),
                String.format("%s: %s\n", friend.getName().toUpperCase(), friend.getRandomDialogue())
        };
    }

    //getters / setters
    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    @Override
    public String getRandomDialogue(){
        Random random = new Random();
        return dialogue.get(random.nextInt(dialogue.size()));
    }

}
