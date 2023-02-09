package com.skombie.eventhandling;

import com.skombie.utilities.Console;
import com.skombie.utilities.Music;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameQuitEventHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Music.stopSound();
        Music.playSound(this.getClass().getClassLoader().getResourceAsStream("music/gamefinish.wav"));
        Console.pause(11000);
        System.exit(0);
    }
}
