package com.skombie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;

public class Skombie extends TimerTask{
    House house;
    List<String> skunkRooms = new ArrayList<>(List.of("bedroom", "basement", "bedroom", "office", "kitchen", "living room"));

    public Skombie(House house) {
        this.house = house;
    }

    public void run() {
        Collections.shuffle(skunkRooms);
        System.out.println("A skombie is attacking in room " + skunkRooms.get(0));
        Location loc = house.findLocationByName(skunkRooms.get(0));
        if (loc.isHasSkunk()) {
        }
        else {
            loc.setHasSkunk(true);
        }
    }

}