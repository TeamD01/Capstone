package com.skombie.utilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class AttackEngine {

    private final JProgressBar skombiehealthBar;
    private int hitPoints;
    private final int damageDone;
    private final JFrame window;

    DamageHandler damageHandler = new DamageHandler();

    public AttackEngine() {
        //Feature to allow different weapons
        String weapon = "Baseball Bat";

        if(weapon.equalsIgnoreCase("Baseball Bat")){
            damageDone = 10;
        }else if(weapon.equalsIgnoreCase("Flame Thrower")){
            damageDone = 50;
        }else if (weapon.equalsIgnoreCase("Vinegar Spray Bottle")){
            damageDone = 1;
        }else{
            damageDone = 5;
        }


        window = new JFrame("ATTACK");
        window.setSize(800, 500);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(null);
        Container con = window.getContentPane();

        JPanel picturePanel = new JPanel();
        picturePanel.setBounds(100, 50, 600, 200);
        picturePanel.setBackground(Color.black);
        con.add(picturePanel);

        JLabel pictureLabel = new JLabel();

        BufferedImage image = null;

        try (InputStream inStream = getClass().getClassLoader().getResourceAsStream("images/SkombieEyes.jpg")){
            assert inStream != null;
            image = ImageIO.read(inStream);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        assert image != null;
        pictureLabel.setIcon(new ImageIcon(image));
        picturePanel.add(pictureLabel);
        con.add(picturePanel);

        JPanel playerHealthBarPanel = new JPanel();
        playerHealthBarPanel.setBounds(250,400,300,30);
        playerHealthBarPanel.setBackground(Color.green);
        con.add(playerHealthBarPanel);

        JPanel skombieHealthBarPanel = new JPanel();
        skombieHealthBarPanel.setBounds(250, 250, 300, 30);
        skombieHealthBarPanel.setBackground(Color.black);
        con.add(skombieHealthBarPanel);

        skombiehealthBar = new JProgressBar(0, 100);
        skombiehealthBar.setPreferredSize(new Dimension(300, 30));
        skombiehealthBar.setForeground(Color.black);
        skombiehealthBar.setBackground(Color.red);
        skombiehealthBar.setValue(100);
        skombieHealthBarPanel.add(skombiehealthBar);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(250, 300, 300, 40);
        buttonPanel.setBackground(Color.black);
        con.add(buttonPanel);

        JPanel playerButtonPanel = new JPanel();
        playerButtonPanel.setBounds(250, 400, 300, 40);
        playerButtonPanel.setBackground(Color.black);
        con.add(playerButtonPanel);

        JButton button = new JButton("ATTACK");
        button.setBackground(Color.black);
        button.setForeground(Color.RED);
        button.setFocusPainted(false);
        button.addActionListener(damageHandler);
        buttonPanel.add(button);
        hitPoints = 100;


        window.setAlwaysOnTop(true);
        window.setVisible(true);
    }

    public void damageReceived(){
        hitPoints -= damageDone;

        if(hitPoints == 0){
            window.dispose();
        }
        skombiehealthBar.setValue(hitPoints);
    }
    public class DamageHandler implements ActionListener{
        public void actionPerformed(ActionEvent event){

            damageReceived();
        }
    }
}