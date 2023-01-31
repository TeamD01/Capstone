package com.skombie.app;

import com.skombie.House;
import com.skombie.Location;
import com.skombie.utilities.Console;
import com.skombie.utilities.InteractionParser;
import com.skombie.utilities.JSONMapper;
import com.skombie.utilities.PromptHelper;
import com.skombie.utilities.Console;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

import static com.skombie.utilities.Printer.printFile;
import static com.skombie.utilities.Printer.printFile;

public class SkombieApp implements Runnable{
    private static final String TITLE = "images/title.txt";
    private static final String ALERT = "data/alertMsg.txt";
    private static final String INTRO = "data/intro";

    private final PromptHelper prompter = new PromptHelper(new Scanner(System.in));
    private final InteractionParser pars = new InteractionParser();
    private final House house;

    private Location currLocation;

    public SkombieApp(House house) {
        this.house = house;
        this.currLocation = house.grabJSONLocation("Living Room");
    }

    public void run() {
        getGameTitle();
        promptUserNew();
        alertMessage();
        generateInstructions();
        startGame();
    }

    public void promptUserNew() {
        String input = prompter.prompt("\nWould you like to start a new game or continue?\n[N]ew Game\t[C]ontinue\n", "[nNcC]", "\nInvalid Entry\n");
        if ("N".equalsIgnoreCase(input)) {
            Console.clear();
        } else {
            //TODO: If user selects continue we will grab their data from somewhere
            // and add necessary code here.
            prompter.prompt("NOT A VALID SELECTION AT THIS TIME");
        }
    }

    public void getGameTitle() {
        printFile(TITLE, 500);
    }

    public void alertMessage() {
        printFile(ALERT, 500);
        Console.pause(3000);
        Console.clear();
    }

    public void generateInstructions() {
        printFile(INTRO);
        Console.pause(5000);
        Console.clear();
    }

    public void startGame() {
        //TODO: GAME LOGIC HERE
        String requestedLocation = null;
        while (true) {
            printCurrLocationData();
            String[] command = pars.verifyInput();
//            pars.useCommand(currLocation, command);
            if (command[0].equals("go")) {
                requestedLocation = pars.goRoom(currLocation, command);
                currLocation = house.grabJSONLocation(requestedLocation);
                checkForSkombie();
            }
            else if (command[0].equals("look")){
               // pars.look(house.grabJSONLocation(currLocation.getName()), command);
            }
        }
    }

    public void printCurrLocationData() {
        System.out.println("=======================");
        System.out.printf("Location: %s\n", currLocation.getName());
        System.out.printf("%s\n", currLocation.getDescription());

        if (!(currLocation.getFurniture() == null)) {
            System.out.println("\nFurniture:");
            currLocation.getFurniture().forEach(x -> System.out.printf("> %s\n", x.getName()));
        }
        if (!(currLocation.getCharacters() == null)) {
            System.out.println("\nPeople:");
            currLocation.getCharacters().forEach(x -> System.out.printf("> %s\n", x.getName()));
        }
        if (!(currLocation.getItems() == null)) {
            System.out.println("\nItems:");
            currLocation.getItems().forEach(x -> System.out.printf("> %s\n", x.getName()));
        }
        if (!(currLocation.getWeapons() == null)) {
            System.out.println("\nWeapons:");
            currLocation.getWeapons().forEach(x -> System.out.printf("> %s\n", x.getName()));
        }

        System.out.println("\nAvailable Locations:");
        currLocation.getAvailableRooms().forEach(x -> System.out.printf("> %s\n", x));

        System.out.println("=======================");
    }

    public Location getCurrLocation() {
        return currLocation;
    }

    private void checkForSkombie () {
        if (house.grabJSONLocation(currLocation.getName()).isHasSkunk()) {
            System.out.println("There is a skunk, get it, get it!!!");
        }
    }
}