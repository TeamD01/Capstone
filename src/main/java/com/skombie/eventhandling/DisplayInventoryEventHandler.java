package com.skombie.eventhandling;

import com.skombie.model.House;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayInventoryEventHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

       House classObj = new House();
       classObj.printInventory();
    }
}