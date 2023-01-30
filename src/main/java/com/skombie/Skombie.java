package com.skombie;
import com.skombie.utilities.JSONMapper;
import java.util.TimerTask;

public class Skombie extends TimerTask{
    JSONMapper map = JSONMapper.getInstance();



    public void run() {
        System.out.println("A skombie is attacking!");
        Location loc = map.grabJSONLocation("bedroom");
        if (loc.isHasSkunk()) {
        }
        else {
            loc.setHasSkunk(true);
        }
    }

}