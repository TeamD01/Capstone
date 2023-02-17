package com.skombie.eventhandling;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class AtticEventHandler implements ActionListener {
    private final JLabel background;
    private final JButton livingroom;
    private final JButton hallway;
    private final JButton office;
    private final JButton basement;
    private final JButton bedroom;
    private final JButton kitchen;
    private final JButton backyard;
    private final JButton attic;

    public AtticEventHandler(JLabel background, JButton livingroom, JButton hallway, JButton office, JButton basement,
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
        // Attic can only see bedroom
        livingroom.setVisible(false);
        office.setVisible(false);
        basement.setVisible(false);
        bedroom.setVisible(true);//<--
        kitchen.setVisible(false);
        backyard.setVisible(false);
        hallway.setVisible(false);
        attic.setVisible(false);
        bedroom.requestFocus();

        BufferedImage bufImage;
        try (InputStream inStream = getClass().getClassLoader().getResourceAsStream("povImages/attic-Tyler Lastovich.jpg")) {
            assert inStream != null;
            bufImage = ImageIO.read(inStream);
            background.setIcon(new ImageIcon(bufImage));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
