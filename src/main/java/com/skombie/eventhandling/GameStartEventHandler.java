package com.skombie.eventhandling;

import com.skombie.utilities.AttackEngine;
import com.skombie.utilities.Music;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

public class GameStartEventHandler implements ActionListener {
    private final JLabel bGround;
    InputStream EMERGENCY = getFile();

    public GameStartEventHandler(JLabel bGround) {

        this.bGround = bGround;
    }

    private InputStream getFile(){
        return this.getClass().getClassLoader().getResourceAsStream("music/emergency.wav");
    }
    //For ticket tracking AB#907 on line 21
    @Override
    public void actionPerformed(ActionEvent e) {
        bGround.setVisible(false);
        Music.stop();
        Music.playSound(EMERGENCY);

        //For ticket tracking AB#908 starts on line 25
        JOptionPane.showMessageDialog(bGround, "<html><body BGCOLOR=RED TEXT=BLACK STYLE=TEXT-ALIGN:center>"
                + "<H1>  Reports have been received of skunk zombies attacking in the surrounding area.. </H1>"
                + "<H2>  These creatures are extremely dangerous and should not be approached under any circumstances. <br>"
                + "They are believed to be highly contagious and can infect others with a zombie virus through bites or scratches. <br>"
                + "If you see one of these skunk zombies, immediately seek shelter and contact authorities.<br>"
                + "Do not attempt to engage or confront the skunk zombie."
                + " This is not a drill, repeat, this is not a drill.<br><br>"
                + "** Authorities are urging that everyone stay indoors since the skunk spray will KILL you. **<br><br></H2> "
                + "<H1>IF YOU DO NEED TO GO OUTSIDE MAKE SURE TO WEAR A GAS MASK  </H1>"
        );
        bGround.requestFocus();

        new AttackEngine(); //calls the Skombie fight
    }

}
