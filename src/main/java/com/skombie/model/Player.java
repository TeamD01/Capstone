package com.skombie.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player implements Speaker, Serializable {
    private boolean isDead = false;
    private double health = 100.0;
    private List<InventoryItem> inventory = new ArrayList<>();
    private Weapon currentWeapon;
    private final List<String> dialogue = new ArrayList<String>(){
        {
            add("What's going on");
            add("Hey, how you feeling today?");
            add("Hey, these skunks are really getting on my last nerve.");
        }
    };
    private boolean hasGasMask = false;
    private String reasonForDeath;


    public Player() {
    }

    //action methods
    public String look(Inspectable item) {
        return item.getDescription();
    }

    public void get(InventoryItem item){
        inventory.add(item);
    }

    public void drop(InventoryItem item){
        inventory.remove(item);
    }

    public String[] talk(Character friend){
        return new String[]{

                String.format("YOU: %s", getRandomDialogue()),
                String.format("%s: %s", friend.getName().toUpperCase(), friend.getRandomDialogue())
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

    public List<InventoryItem> getInventory() {
        return inventory;
    }

    public void setInventory(List<InventoryItem> inventory) {
        this.inventory = inventory;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public boolean hasGasMask() {
        return hasGasMask;
    }

    public void setHasGasMask(boolean hasGasMask) {
        this.hasGasMask = hasGasMask;
    }

    public String getReasonForDeath() {
        return reasonForDeath;
    }

    public void setReasonForDeath(String reasonForDeath) {
        this.reasonForDeath = reasonForDeath;
    }

    @Override
    public String getRandomDialogue(){
        Random random = new Random();
        return dialogue.get(random.nextInt(dialogue.size()));
    }

    public double getAttackValue() {
        double attackValue = 1;
        if (currentWeapon != null) {
            attackValue = currentWeapon.getAttackValue();
        }
        return attackValue;
    }

}
