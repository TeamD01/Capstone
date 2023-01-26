package com.skombie.utilities;

import java.io.IOException;

public class Console {
    private static final String opSystem = System.getProperty("os.name").toLowerCase();

    public Console() {
    }

    public static void clear(){
        ProcessBuilder builder = opSystem.contains("windows") ?
                new ProcessBuilder("cmd", "/c", "cls"):
                new ProcessBuilder("clear");

        try {
            builder.inheritIO().start().waitFor();
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
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