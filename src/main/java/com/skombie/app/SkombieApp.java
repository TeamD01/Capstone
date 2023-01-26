package com.skombie.app;

import main.java.com.skombie.utilities.Console;
import com.skombie.utilities.PromptHelper;
import main.java.com.skombie.utilities.Printer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static main.java.com.skombie.utilities.Printer.printFile;

public class SkombieApp {
    private final PromptHelper prompter = new PromptHelper(new Scanner(System.in));
    private static final String TITLE = "src/main/resources/images/title.txt";
    private static final String INTRO = "src/main/resources/data/intro";
    private static final String ALERT = "src/main/resources/data/alertMsg.txt";



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
        try (BufferedReader reader = new BufferedReader(new FileReader(TITLE))) {
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
        printFile(INTRO);
    }

    public void alertMessage() {
        printFile(ALERT);
        Console.pause(6000);
        Console.clear();
    }



}