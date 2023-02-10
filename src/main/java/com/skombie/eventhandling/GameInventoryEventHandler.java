package com.skombie.eventhandling;

import com.skombie.model.House;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameInventoryEventHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        House.printInventory();
    }
}