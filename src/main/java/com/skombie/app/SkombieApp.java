package com.skombie.app;

import com.skombie.eventhandling.*;
import com.skombie.model.House;
import com.skombie.model.Player;
import com.skombie.utilities.Console;
import com.skombie.utilities.Music;
import com.skombie.utilities.Printer;
import com.skombie.utilities.PromptHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.net.URL;
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
//        else if ("C".equalsIgnoreCase(input)) {
//            house.loadGame();
//            house.setProgressedPastHelp(false);
//        }
    }

    private final JMenuItem item1 = new JMenuItem("                         MUTE");
    private final JMenuItem item2 = new JMenuItem("                         STOP");
    private final JMenuItem item3 = new JMenuItem("                        START");

    int hp = 100;

    public void getGameTitle() {
        JPanel gameControls;
        JFrame gameFrame;
        JButton gameStart;
        JButton gameHelp;
        JButton gameQuit;
        JSlider slider;
        JButton gameAttack;
        JPanel skombieHealthBarPanel;
        JProgressBar skombieHealthBar;


        gameFrame = new JFrame();
        gameFrame.setTitle("NIGHT OF THE SKOMBIES");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        URL imgPath = ClassLoader.getSystemClassLoader().getResource("images/NightOfTheSkombies.jpeg");
        assert imgPath != null;
        ImageIcon img = new ImageIcon(imgPath);
        JLabel background;
        background = new JLabel(img);

        gameControls = new JPanel();


        gameStart = new JButton("START");
        gameStart.setBackground(Color.green);
        gameControls.add(gameStart);
        gameFrame.add(gameControls, BorderLayout.NORTH);
        gameStart.addActionListener(new GameStartEventHandler(background));

        gameHelp = new JButton("HELP");
        gameHelp.setBackground(Color.red);
        gameControls.add(gameHelp);
        gameFrame.add(gameControls, BorderLayout.NORTH);
        gameHelp.addActionListener(new GameHelpEventHandler(background, gameStart, gameHelp));
        gameFrame.add(gameControls, BorderLayout.NORTH);

        generateMusicDropDown(gameControls);

        gameAttack = new JButton("ATTACK");
        gameAttack.setBackground(Color.red);
        gameControls.add(gameAttack);
        gameFrame.add(gameControls, BorderLayout.NORTH);
        gameAttack.addActionListener(new DamageHandler());// attack button

        //Skombie Health
        skombieHealthBarPanel = new JPanel();
        skombieHealthBarPanel.setBackground(Color.green);
        skombieHealthBarPanel.setPreferredSize(new Dimension(300, 27));
        skombieHealthBarPanel.setFocusable(false);
        gameControls.add(skombieHealthBarPanel);
        gameFrame.add(gameControls, BorderLayout.NORTH);

        skombieHealthBar = new JProgressBar(0, 100);
        skombieHealthBar.setPreferredSize(new Dimension(300, 50));
        skombieHealthBar.setForeground(Color.green);

        skombieHealthBar.setValue(80);  // set the healthbar
        skombieHealthBarPanel.add(skombieHealthBar);

        generateQuitButton(gameControls, gameFrame);

        gameStart.requestFocus();
        gameFrame.pack();
        gameFrame.add(background);
        gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameFrame.setVisible(true);

        addMapToGameFrame(gameFrame);
        gameStart.requestFocus();
        gameFrame.pack();
        gameFrame.add(background);
        gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameFrame.setVisible(true);

        printFile(TITLE, 5);
    }

    private void generateMusicDropDown(JPanel gameControls) {
        JSlider slider;
        JMenuBar musicBar = new JMenuBar();
        JMenu menu1 = new JMenu("MUSIC");
        musicBar.setBackground(Color.red);
        musicBar.setPreferredSize(new Dimension(55, 27));

        menu1.add(item1);
        menu1.add(item2);
        menu1.add(item3);

        item1.setBackground(Color.red);
        item2.setBackground(Color.orange);
        item3.setBackground(Color.yellow);

        item1.addActionListener(new GameMusicMuteEventHandler());
        item2.addActionListener(new GameMusicStopEventHandler());
        item3.addActionListener(new GameMusicStartEventHandler());

        musicBar.add(menu1);
        gameControls.add(musicBar, BorderLayout.NORTH);

        slider = new JSlider(-40, 6);
        slider.setBackground(Color.green);
        slider.addChangeListener(e -> {
            Music.setCurrentVolume(slider.getValue());
            Music.fc.setValue(Music.getCurrentVolume());
        });
        menu1.add(slider);

    }


    private void generateQuitButton(JPanel gameControls, JFrame gameFrame) {
        JButton gameQuit;
        gameQuit = new JButton("QUIT");
        gameQuit.setBackground(Color.red);
        gameControls.add(gameQuit);
        gameFrame.add(gameControls, BorderLayout.NORTH);
        gameQuit.addActionListener(new GameQuitEventHandler());
    }


    private void generateHelpButton(JPanel gameControls, JFrame gameFrame, JButton gameStart, JLabel background) {
        JButton gameHelp;
        gameHelp = new JButton("HELP");
        gameHelp.setBackground(Color.red);
        gameControls.add(gameHelp);
        gameFrame.add(gameControls);
        gameHelp.addActionListener(new GameHelpEventHandler(background, gameStart, gameHelp));
    }

    private JButton generateStartButton(JPanel gameControls, JFrame gameFrame, JLabel background) {
        JButton gameStart;
        gameStart = new JButton("START");
        gameStart.setBackground(Color.green);
        gameControls.add(gameStart);
        gameFrame.add(gameControls);
        gameStart.addActionListener(new GameStartEventHandler(background));
        return gameStart;
    }


    private void addMapToGameFrame(JFrame gameFrame) {
        JPanel houseMapPanel;
        //adding a persistent map to the upper right hand corner.
        houseMapPanel = new JPanel();
        URL houseMapImgPath = ClassLoader.getSystemClassLoader().getResource("images/skombies-house-map.jpg");
        assert houseMapImgPath != null;
        ImageIcon houseMapImg = new ImageIcon(houseMapImgPath);
        JLabel mapDisplayLabel = new JLabel(houseMapImg);
        houseMapPanel.setBounds(550, 450, 50, 50);
        houseMapPanel.add(mapDisplayLabel);
        gameFrame.add(houseMapPanel);
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
// pop new screen...borderlayout??

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
    private InputStream getFile(String file) {
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

    public class DamageHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            hp = hp - 10;
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

}