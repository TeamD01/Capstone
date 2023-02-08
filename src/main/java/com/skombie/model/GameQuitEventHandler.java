package com.skombie.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameQuitEventHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
