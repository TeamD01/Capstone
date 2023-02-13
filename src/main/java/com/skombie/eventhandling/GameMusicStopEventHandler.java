package com.skombie.eventhandling;

import com.skombie.utilities.Music;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMusicStopEventHandler implements ActionListener {
    public GameMusicStopEventHandler() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Music.stop();
    }
}
