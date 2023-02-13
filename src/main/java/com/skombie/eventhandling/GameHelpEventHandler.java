package com.skombie.eventhandling;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameHelpEventHandler implements ActionListener {
    private final JLabel bGround;
    private final JButton gStart;
    private final JButton gHelp;

    public GameHelpEventHandler(JLabel background, JButton gameStart, JButton gameHelp) {
        this.bGround = background;
        this.gStart = gameStart;
        this.gHelp = gameHelp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == gHelp){
            gStart.setBackground(Color.red);
            gHelp.setBackground(Color.green);
        }
        JOptionPane.showMessageDialog(bGround, "<html><body BGCOLOR=GREEN TEXT=BLACK STYLE=TEXT-ALIGN:left>"
                + "<H1>Basic Commands </H1><br>" +
                "<H2>** Navigation **<br>" +
                "Go <Direction> - Go Living Room<br><br>" +
                "** ITEMS, WEAPONS **<br>" +
                "Get <Item>  - Get Hammer<br>" +
                "Drop <Item> - Drop Hammer<br>" +
                "Look <Item> - Look Hammer<br>" +
                "Open<Furniture> - Open Refrigerator<br><br>" +
                "** TALK **<br>" +
                "Talk <Character> - Talk Chris<br><br>" +
                "** OTHER **<br>" +
                "Attack Skombie - attack skombie with current weapon<br>" +
                "Secure Room - In order to secure the room you are in<br>" +
                "Inventory - returns your inventory details<br>" +
                "Help - returns this page if you get stuck<br>" +
                "Quit - quit the current game </H2></body>"

        );

    }
}
