package com.skombie.eventhandling;

import com.skombie.utilities.Music;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMusicMuteEventHandler implements ActionListener {

    public GameMusicMuteEventHandler() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Music.volumeMute();
    }

}
