package com.skombie.eventhandling;

import com.skombie.utilities.AttackEngine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class BasementEventHandler implements ActionListener {
    private final JLabel background;
    private final JButton livingroom;
    private final JButton hallway;
    private final JButton office;
    private final JButton basement;
    private final JButton bedroom;
    private final JButton kitchen;
    private final JButton backyard;

    public BasementEventHandler(JLabel background, JButton livingroom, JButton hallway, JButton office, JButton basement,
                                JButton bedroom, JButton kitchen, JButton backyard) {
        this.background = background;
        this.livingroom = livingroom;
        this.hallway = hallway;
        this.office = office;
        this.basement = basement;
        this.bedroom = bedroom;
        this.kitchen = kitchen;
        this.backyard = backyard;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Basement can only see hallway
        livingroom.setVisible(false);
        office.setVisible(false);
        basement.setVisible(false);
        bedroom.setVisible(false);
        kitchen.setVisible(false);
        backyard.setVisible(false);
        hallway.setVisible(true);//<---
        hallway.requestFocus();

        BufferedImage bufImage;
        try (InputStream inStream = getClass().getClassLoader().getResourceAsStream("povImages/basement-AleksandarPasaric.jpg")) {
            assert inStream != null;
            bufImage = ImageIO.read(inStream);
            background.setIcon(new ImageIcon(bufImage));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        new AttackEngine();
    }
}

