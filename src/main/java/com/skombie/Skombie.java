package com.skombie;
import com.skombie.utilities.JSONMapper;
import java.util.TimerTask;

public class Skombie extends TimerTask{
    House house;

    public Skombie(House house) {
        this.house = house;
    }

    public void run() {
        System.out.println("A skombie is attacking!");
        Location loc = house.grabJSONLocation("bedroom");
        if (loc.isHasSkunk()) {
        }
        else {
            loc.setHasSkunk(true);
        }
    }

}