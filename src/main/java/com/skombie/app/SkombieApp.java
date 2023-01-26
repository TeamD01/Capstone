package com.skombie.app;

import com.skombie.utilities.Console;
import com.skombie.utilities.PromptHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class SkombieApp {
    private final PromptHelper prompter = new PromptHelper(new Scanner(System.in));

    public void execute() {
        getGameTitle();
        promptUserNew();
        alertMessage();
        generateInstructions();
    }

    public void promptUserNew(){
        String input = prompter.prompt("\nWould you like to start a new game or continue?\n[N]ew Game\t[C]ontinue\n", "[nNcC]", "\nInvalid Entry\n");
        if("N".equalsIgnoreCase(input)){
            Console.clear();
        }
        else {
            //TODO: If user selects continue we will grab their data from somewhere
            // and add necessary code here.
            prompter.prompt("NOT A VALID SELECTION AT THIS TIME");
        }

    }

    public void getGameTitle() {
        try (BufferedReader reader = new BufferedReader(new FileReader("images/title.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                Console.pause(500);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Console.clear();
    }

    public void generateInstructions() {
        printFile("data/intro");
    }

    public void alertMessage() {
        printFile("data/alertMsg.txt");
        Console.pause(6000);
        Console.clear();
    }

    private void printFile(String fileName) {
        try {
            System.out.println(Files.readString(Path.of(fileName)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}