package com.skombie.UI;

import com.skombie.eventhandling.GameHelpEventHandler;
import com.skombie.eventhandling.GameInventoryEventHandler;
import com.skombie.eventhandling.GameQuitEventHandler;
import com.skombie.eventhandling.GameStartEventHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
public class mainFrame extends JFrame {

    JPanel gameControls;
    JButton gameStart;
    JButton gameHelp;
    JButton gameQuit;

    public mainFrame() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(520, 520);
        this.setTitle("NIGHT OF THE SKOMBIES");
        this.setVisible(true);


        URL imgPath = ClassLoader.getSystemClassLoader().getResource("images/NightOfTheSkombies.jpeg");
        assert imgPath != null;
        ImageIcon img = new ImageIcon(imgPath);
        JLabel background;
        background = new JLabel(img);

        JPanel redPanel = new JPanel();
        redPanel.setBackground(Color.red);
        redPanel.setBounds(20, 20, 200, 200);

        gameControls = new JPanel();
        GridLayout blockLayout;
        blockLayout = new GridLayout(4, 4);
        background.setLayout(blockLayout);


        gameStart = new JButton("START");
        gameStart.setBackground(Color.green);
        gameControls.add(gameStart);
        this.add(gameControls);
        gameStart.addActionListener(new GameStartEventHandler(background));

        gameHelp = new JButton("HELP");
        gameHelp.setBackground(Color.red);
        gameControls.add(gameHelp);
        this.add(gameControls);
        gameHelp.addActionListener(new GameHelpEventHandler(background));

        gameQuit = new JButton("QUIT");
        gameQuit.setBackground(Color.red);
        gameControls.add(gameQuit);
        this.add(gameControls);
        gameQuit.addActionListener(new GameQuitEventHandler());

        JButton playerInventory = new JButton();
        playerInventory.setText("Inventory");
        playerInventory.setBackground(Color.YELLOW);
        gameControls.add(playerInventory);
        this.add(gameControls);
        playerInventory.addActionListener(new GameInventoryEventHandler());

        gameStart.requestFocus();
        this.setSize(600, 500);
        this.pack();
        this.add(background);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }




}