package com.skombie.app;
import com.skombie.UI.*;
import com.skombie.model.House;
import com.skombie.model.Player;
import com.skombie.utilities.Console;
import com.skombie.utilities.Music;
import com.skombie.utilities.Printer;
import com.skombie.utilities.PromptHelper;

import javax.swing.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.skombie.utilities.Printer.printFile;

public class SkombieApp implements Runnable {
    private final Scanner scanner = new Scanner(System.in);
    private final PromptHelper prompter = new PromptHelper(scanner);
    private static final String TITLE = "images/title.txt";
    private static final String ALERT = "data/alertMsg.txt";
    private static final String INTRO = "data/intro";
    private final House house;
    private final InputStream MAIN_SONG = getFile("music/MonkeySpin.wav");
    public final InputStream EMERGENCY = getFile("music/emergency.wav");

    List<String> previousMessage = new ArrayList<>();
    private JComponent gameMap;

    public SkombieApp(House house) {
        this.house = house;
    }

    public void run() {
        Music.playSound(MAIN_SONG);
        getGameTitle();
        promptUserNew();
        Music.stop();
        Music.playSound(EMERGENCY);
        alertMessage();
        waitForUserResponse();
        Music.stop();
        generateInstructions();
        house.setProgressedPastHelp(true);
        startGame();
    }

    //removed option to continue game saved game data and continuation is not stable or properly functioning
    //[N]ew Game \t[C]ontinue & "[nNcC]" can be added to the String input when that functionality is fixed.
    public void promptUserNew() {
        String input = prompter.prompt("\nWould you like to start a new game or continue?\n[N]ew Game", "[nN]", "\nInvalid Entry\n");
        if ("N".equalsIgnoreCase(input)) {
            Console.clear();
        }

    }

    private final JMenuItem item1 = new JMenuItem("                         MUTE");
    private final JMenuItem item2 = new JMenuItem("                         STOP");
    private final JMenuItem item3 = new JMenuItem("                        START");

       public void getGameTitle() {


        mainFrame mainFrame = new mainFrame();
//        JPanel redPanel = new JPanel();
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
        gameMap.setVisible(true);

        Player player = house.getPlayer();
        if (previousMessage.size() == 0) {
            previousMessage = new ArrayList<>() {
                {
                    add("Grandpa Nick:");
                    add("You know I had some old military gear in an old trunk that might come in handy.");
                    add("I took it from the Army because I knew this would go down eventually.");
                    add("You'll have to find your wife to get the combination");
                }
            };
            house.gatherLocationData(previousMessage);
        } else {
            house.gatherLocationData(previousMessage);
        }
        while (!player.isDead()) {
            String userInput = prompter.prompt("Please enter a command to proceed.");
            house.manageCommand(userInput);
            previousMessage.addAll(house.updateCharacterStatusList());
            if (randGen()) {
                previousMessage = house.changeCharacterRoom();
            }
        }
        if (player.isDead()) {
            printDeadMessage(player.getReasonForDeath());
        }
    }

    private boolean randGen() {
        return Math.random() < 0.3;
    }

    //If moved to util class we will pass generic so any class can use it
    //private <T> InputStream getFile(Class<T> obj, String file)
    public InputStream getFile(String file) {
        return this.getClass().getClassLoader().getResourceAsStream(file);
    }

    private void waitForUserResponse() {
        System.out.println("Press [Enter] to continue.");
        scanner.nextLine();
    }

    private void printDeadMessage(String message) {
        Printer.printFile("data/dead.txt");
        Music.playSound(getFile("music/eatingMeat.wav"));
        System.out.printf("\nREASON: %s", message);
        Music.playSound(getFile("music/gamefinish.wav"));
        Console.pause(15000);
        System.exit(0);
    }


}