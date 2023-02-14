package com.skombie.eventhandling;

import com.skombie.utilities.Music;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMusicStartEventHandler implements ActionListener {
    public GameMusicStartEventHandler() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Music.start();
    }
}
