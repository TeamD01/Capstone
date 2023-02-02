package com.skombie.app;

import com.skombie.House;
import com.skombie.Location;
import com.skombie.Player;
import com.skombie.utilities.Console;
import com.skombie.utilities.PromptHelper;

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
    }

    public void getGameTitle() {
        printFile(TITLE, 500);
    }

    public void alertMessage() {
        printFile(ALERT, 600);
        Console.pause(5500);
        Console.clear();
    }

    public void generateInstructions() {
        printFile(INTRO);
        Console.pause(5000);
        Console.clear();
    }

    public void startGame() {
         while(true){
             house.printCurrLocationData();
             String userInput = prompter.prompt("Please enter a command to proceed.");
             house.manageCommand(userInput);
         }
    }
}