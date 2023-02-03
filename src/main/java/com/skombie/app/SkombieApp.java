package com.skombie.app;

import com.skombie.House;
import com.skombie.Location;
import com.skombie.Player;
import com.skombie.utilities.Console;
import com.skombie.utilities.PromptHelper;

import java.util.Random;
import java.util.Scanner;

import static com.skombie.utilities.Printer.printFile;

public class SkombieApp implements Runnable{
    private final Scanner scanner = new Scanner(System.in);
    private final PromptHelper prompter = new PromptHelper(scanner);
    private static final String TITLE = "images/title.txt";
    private static final String ALERT = "data/alertMsg.txt";
    private static final String INTRO = "data/intro";
    private final House house;


    public SkombieApp(House house) {
        this.house = house;
    }

    public void run() {
        getGameTitle();
        promptUserNew();
        alertMessage();
        generateInstructions();
        startGame();
    }

    public void promptUserNew() {
        String input = prompter.prompt("\nWould you like to start a new game or continue?\n[N]ew Game\t[C]ontinue", "[nNcC]", "\nInvalid Entry\n");
        if ("N".equalsIgnoreCase(input)) {
            Console.clear();
        }
        else if ("C".equalsIgnoreCase(input)) {
            house.loadGame();
        }
    }

    public void getGameTitle() {
        printFile(TITLE, 5);
    }

    public void alertMessage() {
        printFile(ALERT, 6);
        Console.pause(5);
        Console.clear();
    }

    public void generateInstructions() {
        printFile(INTRO);
        Scanner myObj = new Scanner(System.in);
        System.out.println("[P]roceed?");
        while (true) {
            String userInput = myObj.next();
            if (userInput.matches("([pP])")) {
                break;
            }
            else {
                System.out.println("[P]roceed?");
            }
        }
        Console.clear();
    }

    public void startGame() {
         while(true){
             house.printCurrLocationData();
             String userInput = prompter.prompt("Please enter a command to proceed.");
             house.manageCommand(userInput);
             if (randGen() == 1) {
                 house.changeCharacterRoom();
             }
         }
    }

    private int randGen() {
        Random rand = new Random();
        int upperbound = 5;
        int int_random = rand.nextInt(upperbound);
        return int_random;
    }
}