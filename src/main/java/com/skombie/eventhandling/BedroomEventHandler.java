package com.skombie.eventhandling;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class BedroomEventHandler implements ActionListener {
    private final JLabel background;
    private final JButton livingroom;
    private final JButton hallway;
    private final JButton office;
    private final JButton basement;
    private final JButton bedroom;
    private final JButton kitchen;
    private final JButton backyard;
    private final JButton attic;

    public BedroomEventHandler(JLabel background, JButton livingroom, JButton hallway, JButton office, JButton basement,
                               JButton bedroom, JButton kitchen, JButton backyard, JButton attic) {

        this.background = background;
        this.livingroom = livingroom;
        this.hallway = hallway;
        this.office = office;
        this.basement = basement;
        this.bedroom = bedroom;
        this.kitchen = kitchen;
        this.backyard = backyard;
        this.attic = attic;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Bedroom can see hallway and attic
        livingroom.setVisible(false);
        office.setVisible(false);
        basement.setVisible(false);
        bedroom.setVisible(false);
        kitchen.setVisible(false);
        backyard.setVisible(false);
        hallway.setVisible(true);//<--
        attic.setVisible(true);//<--

        BufferedImage bufImage;
        try (InputStream inStream = getClass().getClassLoader().getResourceAsStream("povImages/bedroom-ReneAsmussen.jpg")) {
            assert inStream != null;
            bufImage = ImageIO.read(inStream);
            background.setIcon(new ImageIcon(bufImage));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
