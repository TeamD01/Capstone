package com.skombie.app;

import com.skombie.Location;
import com.skombie.utilities.InteractionParser;
import com.skombie.utilities.JSONMapper;
import com.skombie.utilities.PromptHelper;
import main.java.com.skombie.utilities.Console;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static main.java.com.skombie.utilities.Printer.printFile;

public class SkombieApp {
    private final PromptHelper prompter = new PromptHelper(new Scanner(System.in));
    private static final String TITLE = "src/main/resources/images/title.txt";
    private static final String INTRO = "src/main/resources/data/intro";
    private static final String ALERT = "src/main/resources/data/alertMsg.txt";
    private final String startingPoint = "Living Room";
    private final Location currLocation = JSONMapper.grabJSONLocation("Living Room");
    private final boolean gameOver = false;
    InteractionParser pars = new InteractionParser();

    public void execute() {
        getGameTitle();
        promptUserNew();
        alertMessage();
        generateInstructions();
        startGame();
        pars.goRoom(currLocation);
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
                Console.pause(5);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Console.clear();
    }

    public void generateInstructions() {
        printFile(INTRO);
        Console.pause(6);
    }

    public void alertMessage() {
        printFile(ALERT);
        Console.pause(1);
        Console.clear();
    }

    public void startGame(){
        //TODO: GAME LOGIC HERE
//        while(!gameOver){
        printCurrLocationData();
        //}
    }

    public void printCurrLocationData(){
        System.out.println("=======================");
        System.out.printf("Location: %s\n", currLocation.getName());
        System.out.printf("%s\n", currLocation.getDescription());

        if(!(currLocation.getFurniture() == null)) {
            System.out.println("\nFurniture:");
            currLocation.getFurniture().forEach(x -> System.out.printf("> %s\n", x));
        }
        if(!(currLocation.getCharacters() == null)){
            System.out.println("\nPeople:");
            currLocation.getCharacters().forEach(x -> System.out.printf("> %s\n", x));
        }
        if(!(currLocation.getItems() == null)){
            System.out.println("\nItems:");
            currLocation.getItems().forEach(x -> System.out.printf("> %s\n", x));
        }

        System.out.println("\nAvailable Locations:");
        currLocation.getAvailableRooms().forEach(x -> System.out.printf("> %s\n", x));

        System.out.println("=======================");
    }
}