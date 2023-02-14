package com.skombie.UI;

import com.skombie.eventhandling.GameHelpEventHandler;
import com.skombie.eventhandling.GameInventoryEventHandler;
import com.skombie.eventhandling.GameQuitEventHandler;
import com.skombie.eventhandling.GameStartEventHandler;
import com.skombie.model.House;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
class gameFrame extends JFrame {

    /*JPanel gameControls;
    JLabel playerInventory;
    JButton gameStart;
    JButton gameHelp;
    JButton gameQuit;

    public gameFrame() {
        JFrame mainFrame = new JFrame();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(520, 520);
        gameFrame.setLayout(new BorderLayout(10, 10));
        gameFrame.setTitle("NIGHT OF THE SKOMBIES");
        gameFrame.setVisible(true);

        JPanel nPanel = new JPanel();
        JPanel sPanel = new JPanel();
        JPanel ePanel = new JPanel();
        JPanel wPanel = new JPanel();
        JPanel cPanel = new JPanel();

        nPanel.setBackground(Color.red);
        sPanel.setBackground(Color.yellow);
        ePanel.setBackground(Color.blue);
        wPanel.setBackground(Color.green);
        cPanel.setBackground(Color.orange);

        nPanel.setPreferredSize(new Dimension(40, 40));
        sPanel.setPreferredSize(new Dimension(40, 40));
        ePanel.setPreferredSize(new Dimension(40, 40));
        wPanel.setPreferredSize(new Dimension(40, 40));
        cPanel.setPreferredSize(new Dimension(40, 40));

        gameFrame.add(nPanel,BorderLayout.NORTH);
        gameFrame.add(sPanel,BorderLayout.SOUTH);
        gameFrame.add(ePanel,BorderLayout.EAST);
        gameFrame.add(wPanel,BorderLayout.WEST);
        gameFrame.add(cPanel,BorderLayout.CENTER);

        JPanel playerInventoryPanel = new JPanel();
       URL playerInventoryImgPath = ClassLoader.getSystemClassLoader().getResource("images/Inventory.jpg");
       assert playerInventoryImgPath != null;
       ImageIcon playerInventoryImg = new ImageIcon(playerInventoryImgPath);
       JLabel mapDisplayLabel = new JLabel(playerInventoryImg);
       playerInventoryPanel.setBounds(700, 50, 550, 425);
       playerInventoryPanel.setLocation(900, 200);
       playerInventoryPanel.createImage(10, 10);
       playerInventoryPanel.add(mapDisplayLabel);
       gameFrame.add(playerInventoryPanel);


        URL imgPath = ClassLoader.getSystemClassLoader().getResource("images/NightOfTheSkombies.jpeg");
        assert imgPath != null;
        ImageIcon img = new ImageIcon(imgPath);
        JLabel background;
        background = new JLabel(img);
        playerInventory = new JLabel("House.printInventory()");

        this.gameControls = new JPanel();
        GridLayout blockLayout;
        blockLayout = new GridLayout(4, 4);
        background.setLayout(blockLayout);

        gameStart = new JButton("START");
        gameStart.setBackground(Color.green);
        nPanel.add(gameStart);
        gameStart.setLayout(null);
        this.add(gameControls);

        gameStart.addActionListener(new GameStartEventHandler(background));

        gameHelp = new JButton("HELP");
        gameHelp.setBackground(Color.green);
        nPanel.add(gameHelp);
        gameHelp.setLayout(null);
        this.add(gameControls);
        gameHelp.setBounds(0, 0, 10, 10);
        gameHelp.setLayout(null);
        gameHelp.addActionListener(new GameHelpEventHandler(background, gameStart, gameHelp));

        gameQuit = new JButton("QUIT");
        gameQuit.setBackground(Color.green);
        gameQuit.setLayout(null);
        nPanel.add(gameQuit);
        this.add(gameControls);
        gameQuit.addActionListener(new GameQuitEventHandler());

        JButton playerInventory = new JButton("Inventory");
        playerInventory.setText("Inventory");
        playerInventory.setBackground(Color.green);
        playerInventory.setLayout(null);
        cPanel.add(playerInventory);
        this.setSize(600, 500);
        this.add(gameControls);

        //playerInventory.addActionListener(new GameInventoryEventHandler());

        gameStart.requestFocus();
        this.setSize(600, 500);
        this.pack();
        this.add(background);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);*/

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

