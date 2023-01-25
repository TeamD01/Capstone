package com.skombie.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SkombieApp {

    public void execute() {
        getGameTitle();
    }

    public void getGameTitle() {
        try (BufferedReader reader = new BufferedReader(new FileReader("images/title.txt"))) {
            String line;
            while((line = reader.readLine()) != null){
                System.out.println(line);
                Thread.sleep(500);
            }
        }
        catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

}