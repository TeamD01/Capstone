package com.skombie.app;

import com.skombie.model.*;
import com.skombie.utilities.Console;
import com.skombie.utilities.Music;
import com.skombie.utilities.Printer;
import com.skombie.utilities.PromptHelper;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    public final InputStream EMERGENCY = getFile("music/emergency.wav");
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
        //alertMessage();
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
        JPanel gameControls;
        JFrame gameFrame;
        JButton gameStart;
        JButton gameHelp;
        JButton gameQuit;

        gameFrame = new JFrame();
        gameFrame.setTitle("NIGHT OF THE SKOMBIES");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        URL imgPath = ClassLoader.getSystemClassLoader().getResource("images/NightOfTheSkombies.jpeg");
        assert imgPath != null;
        ImageIcon img = new ImageIcon(imgPath);
        JLabel background;
        background = new JLabel(img);

        gameControls = new JPanel();
        GridLayout blockLayout;
        blockLayout = new GridLayout(3, 3);
        background.setLayout(blockLayout);

        gameStart = new JButton("START");
        gameStart.setBackground(Color.green);
        gameControls.add(gameStart);
        gameFrame.add(gameControls);
        gameStart.addActionListener(new GameStartEventHandler(background));

        gameHelp = new JButton("HELP");
        gameHelp.setBackground(Color.red);
        gameControls.add(gameHelp);
        gameFrame.add(gameControls);
        gameHelp.addActionListener(new GameHelpEventHandler(background));

        gameQuit = new JButton("QUIT");
        gameQuit.setBackground(Color.red);
        gameControls.add(gameQuit);
        gameFrame.add(gameControls);
        gameQuit.addActionListener(new GameQuitEventHandler());

        gameStart.requestFocus();
        gameFrame.setSize(600, 500);
        gameFrame.pack();
        gameFrame.add(background);
        gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameFrame.setVisible(true);

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
            previousMessage = new ArrayList<>(){
                {
                    add("Grandpa Nick:");
                    add("You know I had some old military gear in an old trunk that might come in handy.");
                    add("I took it from the Army because I knew this would go down eventually.");
                    add("You'll have to find your wife to get the combination");
                }
            };
            house.gatherLocationData(previousMessage);
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
        Music.playSound(getFile("music/eatingMeat.wav"));
        System.out.printf("\nREASON: %s", message);
        Music.playSound(getFile("music/gamefinish.wav"));
        Console.pause(15000);
        System.exit(0);
    }
}