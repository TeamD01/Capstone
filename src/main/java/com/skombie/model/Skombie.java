package com.skombie.model;

import java.util.*;


public class Skombie extends TimerTask {
    House house;
    int skombieCounter = 0;


    public Skombie(House house) {
        this.house = house;
    }

    // currently, allows for only 3 instances of skombie in 3 different rooms, if more we could end the game as house is overrun. Will not allow skombie in current player room.
    public void run() {
        if (house.isProgressedPastHelp()) {
            int skombieInc = 1;

            List<Location> locationNotSkunkNotCurrRoom = new ArrayList<>();
            for (Location skunkRoom : house.getRooms()) {
                // adds check to see if room secure or not
                if (house.getRooms() != null && skunkRoom != null && !skunkRoom.getName().toLowerCase(Locale.ROOT).equals("backyard") && !skunkRoom.getName().toLowerCase(Locale.ROOT).equals("attic") && !skunkRoom.getName().toLowerCase(Locale.ROOT).equals(house.getCurrLocation().getName().toLowerCase(Locale.ROOT)) && !skunkRoom.isHasSkunk() && !skunkRoom.isSecure()) {
                    locationNotSkunkNotCurrRoom.add(skunkRoom);
                }
            }
            Collections.shuffle(locationNotSkunkNotCurrRoom);
            house.setSkombieCounter(skombieInc);
            this.skombieCounter = house.getSkombieCounter();
            if (locationNotSkunkNotCurrRoom.get(0) != null && skombieCounter < 4) {
                locationNotSkunkNotCurrRoom.get(0).setHasSkunk(true);
                System.out.println(locationNotSkunkNotCurrRoom.get(0).getName() + " has a skombie");
                if (locationNotSkunkNotCurrRoom.get(0).getCharacters() == null) {
                    System.out.println("You hear a strange animal like noise. Better go investigate and find the source!");
                } else {
                    for (Character charac : locationNotSkunkNotCurrRoom.get(0).getCharacters()) {
                        charac.setAvailableMove(false);
                    }
                    System.out.println("You hear a noise that sounds like fighting. Perhaps a family member is in trouble. Better go help!");
                }
            }
        }
    }
}