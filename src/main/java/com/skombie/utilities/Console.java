package com.skombie.utilities;

import com.skombie.Item;

import java.io.IOException;

public class Console {
    private static final String opSystem = System.getProperty("os.name").toLowerCase();

    public Console() {
    }

    public static void clear(){
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void pause(long time){
        try{
            Thread.sleep(time);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

    }
}