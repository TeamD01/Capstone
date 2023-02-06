package com.skombie.app;

import com.skombie.model.House;
import com.skombie.model.Player;
import com.skombie.utilities.Console;
import com.skombie.utilities.Music;
import com.skombie.utilities.Printer;
import com.skombie.utilities.PromptHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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
    private final InputStream MAIN_SONG = getFile("music/MonkeySpin.wav");
    private final InputStream EMERGENCY = getFile("music/emergency.wav");
    List<String> previousMessage = new ArrayList<>();

    public SkombieApp(House house) {
        this.house = house;
    }

    public void run() {
        Music.playSound(MAIN_SONG);
        getGameTitle();
        promptUserNew();
        Music.stopSound();
        Music.playSound(EMERGENCY);
        alertMessage();
        waitForUserResponse();
        Music.stopSound();
        generateInstructions();
        house.setProgressedPastHelp(true);
        startGame();
    }
    public void promptUserNew() {
        String input = prompter.prompt("\nWould you like to start a new game or continue?\n[N]ew Game\t[C]ontinue", "[nNcC]", "\nInvalid Entry\n");
        if ("N".equalsIgnoreCase(input)) {
            Console.clear();
        }
        else if ("C".equalsIgnoreCase(input)) {
            house.loadGame();
            house.setProgressedPastHelp(false);
        }
    }

    public void getGameTitle() {
        printFile(TITLE, 5);
    }

    public void alertMessage() {
        printFile(ALERT, 600); //600
    }

    public void generateInstructions() {
        printFile(INTRO);
        prompter.prompt("\n[P]roceed?", "[pP]", "Not Valid");
        Console.clear();
    }

    public void startGame() {
        Player player = house.getPlayer();
        if(previousMessage.size() == 0){
            house.gatherLocationData();
        }else {
            house.gatherLocationData(previousMessage);
        }
         while(!player.isDead()){
             String userInput = prompter.prompt("Please enter a command to proceed.");
             house.manageCommand(userInput);
             previousMessage.addAll(house.updateCharacterStatusList());
             if (randGen()) {
                 previousMessage = house.changeCharacterRoom();
             }
         }
         if(player.isDead()){
             printDeadMessage(player.getReasonForDeath());
         }
    }

    private boolean randGen() {
        return Math.random() < 0.3;
    }

    //If moved to util class we will pass generic so any class can use it
    //private <T> InputStream getFile(Class<T> obj, String file)
    private InputStream getFile(String file){
        return this.getClass().getClassLoader().getResourceAsStream(file);
    }

    private void waitForUserResponse() {
        System.out.println("Press [Enter] to continue.");
        scanner.nextLine();
    }
    private void printDeadMessage(String message){
        Printer.printFile("data/dead.txt");
        System.out.printf("\nREASON: %s", message);
        System.exit(0);
    }
}