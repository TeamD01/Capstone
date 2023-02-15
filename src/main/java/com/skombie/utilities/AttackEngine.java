package com.skombie.utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AttackEngine {

    private final JProgressBar healthBar;
    private final JButton button;
    private int hitPoints;
    private int damageDone;
    private final JFrame window;
    private Container con;
    private final JPanel healBarPanel;
    private final JPanel buttonPanel;
    private JPanel picturePanel;
    private JLabel pictureLabel;
    private ImageIcon image;

    DamageHandler damageHandler = new DamageHandler();

    public AttackEngine() {
        //String weapon = Weapon.class.getName();
        String weapon = "Baseball Bat";

        if(weapon == "Baseball Bat"){
            damageDone = 10;
        }else if(weapon == "Flame Thrower"){
            damageDone = 50;
        }else if (weapon == "Vinegar Spray Bottle"){
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
        con = window.getContentPane();

        picturePanel = new JPanel();
        picturePanel.setBounds(100, 50, 600, 200);
        picturePanel.setBackground(Color.black);
        con.add(picturePanel);

        pictureLabel = new JLabel();
        image = new ImageIcon("src/main/resources/images/SkombieEyes.jpg");
        pictureLabel.setIcon(image);
        picturePanel.add(pictureLabel);
        con.add(picturePanel);

        healBarPanel = new JPanel();
        healBarPanel.setBounds(250, 250, 300, 30);
        healBarPanel.setBackground(Color.black);
        con.add(healBarPanel);

        healthBar = new JProgressBar(0, 100);
        healthBar.setPreferredSize(new Dimension(300, 30));
        healthBar.setForeground(Color.black);
        healthBar.setBackground(Color.red);
        healthBar.setValue(100);
        healBarPanel.add(healthBar);

        buttonPanel = new JPanel();
        buttonPanel.setBounds(250, 300, 300, 40);
        buttonPanel.setBackground(Color.black);
        con.add(buttonPanel);

        button = new JButton("ATTACK");
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
        healthBar.setValue(hitPoints);
    }
    public class DamageHandler implements ActionListener{
        public void actionPerformed(ActionEvent event){

            damageReceived();
        }
    }
}