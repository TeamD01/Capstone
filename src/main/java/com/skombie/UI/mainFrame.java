package com.skombie.UI;

import com.skombie.eventhandling.*;
import com.skombie.utilities.AttackEngine;
import com.skombie.utilities.Music;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
public class mainFrame extends JFrame {
    private final JMenuItem MUTE = new JMenuItem("                         MUTE");
    private final JMenuItem STOP = new JMenuItem("                         STOP");
    private final JMenuItem START = new JMenuItem("                        START");

    JPanel gameControls;
    JLabel playerInventoryLabel;
    JButton gameStart;
    JButton gameHelp;
    JButton gameQuit;

    public mainFrame() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(520, 520);
        this.setTitle("NIGHT OF THE SKOMBIES");
        this.setLayout(new BorderLayout(1, 1));
        this.setVisible(true);

        JPanel nPanel = new JPanel();
        JPanel sPanel = new JPanel();
        JPanel ePanel = new JPanel();
        JPanel wPanel = new JPanel();
        JPanel cPanel = new JPanel();

        nPanel.setBackground(Color.decode("#0E1A0D"));
        sPanel.setBackground(Color.decode("#0E1A0D"));
        ePanel.setBackground(Color.decode("#21300F"));
        wPanel.setBackground(Color.decode("#010A03"));
        cPanel.setBackground(Color.decode("#010A03"));

        nPanel.setPreferredSize(new Dimension(40, 40));
        sPanel.setPreferredSize(new Dimension(40, 150));
        ePanel.setPreferredSize(new Dimension(100, 40));
        wPanel.setPreferredSize(new Dimension(40, 40));
        cPanel.setPreferredSize(new Dimension(20, 30));

        this.add(nPanel,BorderLayout.NORTH);
        this.add(sPanel,BorderLayout.SOUTH);
        this.add(ePanel,BorderLayout.EAST);
        this.add(wPanel,BorderLayout.WEST);
        this.add(cPanel,BorderLayout.CENTER);

        playerInventoryLabel = new JLabel("Inventory");
        playerInventoryLabel.setForeground(Color.white);
        ePanel.add(playerInventoryLabel);


        URL imgPath = ClassLoader.getSystemClassLoader().getResource("images/NightOfTheSkombies.jpeg");
        assert imgPath != null;
        ImageIcon img = new ImageIcon(imgPath);
        JLabel background;
        background = new JLabel(img);

        this.gameControls = new JPanel();
        GridLayout blockLayout;
        blockLayout = new GridLayout(4, 4);
        background.setLayout(blockLayout);

        generateStartButton(nPanel);
        gameStart.addActionListener(new GameStartEventHandler(background));
        generateHelpButton(nPanel, background);
        generateQuitButton(nPanel);
        gameQuit.addActionListener(new GameQuitEventHandler());
        generateMusicDropDown(nPanel);
        generateInventoryButton(ePanel);
        addMapToGameFrame(ePanel);

        new AttackEngine();

        gameStart.requestFocus();
        this.setSize(600, 500);
        this.pack();
        this.add(background);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);

    }

    private void generateInventoryButton(JPanel cPanel) {
        JButton playerInventory = new JButton("Inventory");
        //playerInventory.setText("Inventory");
        playerInventory.setBackground(Color.green);
        playerInventory.setLayout(null);
        cPanel.add(playerInventory);
        this.add(gameControls);
        playerInventory.addActionListener(new GameInventoryEventHandler());
    }

    private void generateStartButton(JPanel nPanel) {
        gameStart = new JButton("START");
        gameStart.setBackground(Color.green);
        nPanel.add(gameStart);
        gameStart.setLayout(null);
        this.add(gameControls);
    }

    private void generateHelpButton(JPanel nPanel, JLabel background) {
        gameHelp = new JButton("HELP");
        gameHelp.setBackground(Color.green);
        nPanel.add(gameHelp);
        gameHelp.setLayout(null);
        this.add(gameControls);
        gameHelp.setBounds(0, 0, 10, 10);
        gameHelp.setLayout(null);
        gameHelp.addActionListener(new GameHelpEventHandler(background, gameStart, gameHelp));
    }

    private void generateQuitButton(JPanel nPanel) {
        gameQuit = new JButton("QUIT");
        gameQuit.setBackground(Color.green);
        gameQuit.setLayout(null);
        nPanel.add(gameQuit);
        this.add(gameControls);
    }

    private void generateMusicDropDown(JPanel gameControls) {
        JSlider slider;
        JMenuBar musicBar = new JMenuBar();
        JMenu menu1 = new JMenu("MUSIC");
        musicBar.setBackground(Color.green);
        musicBar.setPreferredSize(new Dimension(55,27));

        menu1.add(MUTE);
        menu1.add(STOP);
        menu1.add(START);

        MUTE.setBackground(Color.red);
        STOP.setBackground(Color.orange);
        START.setBackground(Color.yellow);

        MUTE.addActionListener(new GameMusicMuteEventHandler());
        STOP.addActionListener(new GameMusicStopEventHandler());
        START.addActionListener(new GameMusicStartEventHandler());

        musicBar.add(menu1);
        gameControls.add(musicBar);

        slider = new JSlider(-40, 6);
        slider.setBackground(Color.green);
        slider.addChangeListener(e -> {
            Music.setCurrentVolume(slider.getValue());
            Music.fc.setValue(Music.getCurrentVolume());
        });
        menu1.add(slider);
    }

    private void addMapToGameFrame(JPanel gameFrame) {
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
}

/*
public class mainFrame extends JFrame implements ActionListener {

    */
/*JMenuBar menuBar;
    JMenu startMenu;
    JMenu quitMenu;
    JMenu helpMenu;
    JMenu inventoryMenu;
    JMenuItem m
*//*

    JFrame mainFrame = new JFrame();
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
*/

