package com.skombie.model;

import com.skombie.utilities.Music;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

public class GameStartEventHandler implements ActionListener {
    private JButton gStart;
    private JLabel bGround;

    public GameStartEventHandler(JButton gStart, JLabel bGround) {
        this.gStart = gStart;
        this.bGround = bGround;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        bGround.setVisible(false);
        Music.stopSound();

        JFrame alertFrame = new JFrame();
        alertFrame.setSize(1500,500);
        alertFrame.setLayout(null);
        JTextArea area = new JTextArea("This is an emergency announcement. Reports have been received of skunk zombies attacking in the surrounding area.\n" +
                "These creatures are extremely dangerous and should not be approached under any circumstances.\n" +
                "They are believed to be highly contagious and can infect others with a zombie virus through bites or scratches.\n" +
                "If you see one of these skunk zombies, immediately seek shelter and contact authorities.\n" +
                "Do not attempt to engage or confront the skunk zombie. This is not a drill, repeat, this is not a drill.\n" +
                "\n" +
                "** Authorities are urging that everyone stay indoors since the skunk spray will KILL you. **\n" +
                "                IF YOU DO NEED TO GO OUTSIDE MAKE SURE TO WEAR A GAS MASK");
        area.setBounds(10,10, 2500, 300);
        alertFrame.add(area);


        alertFrame.setVisible(true);


    }
}
