package com.skombie.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SkombieApp {

    public void execute() {
        getGameTitle();
        alertMessage();
        generateInstructions();
    }

    public void getGameTitle() {
        try (BufferedReader reader = new BufferedReader(new FileReader("images/title.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void generateInstructions() {
        printFile("data/intro");
    }
    public void alertMessage() {
        printFile("data/alertMsg.txt");
    }

    private void printFile(String fileName) {
        try {
            System.out.println(Files.readString(Path.of(fileName)));
        } catch
        (IOException e) {
            e.printStackTrace();
        }
    }

}