package com.skombie.model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameHelpEventHandler implements ActionListener {
    private JButton gHelp;
    private JLabel bGround;

    public GameHelpEventHandler(JButton gameHelp, JLabel background) {
        this.gHelp = gameHelp;
        this.bGround = background;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(bGround, "<html><body BGCOLOR=GREEN TEXT=BLACK STYLE=TEXT-ALIGN:left>"
                + "<H1>  The Help Menu: </H1>"
                + "<H2>  START BUTTON: starts the game  <br>"
                + "  QUIT BUTTON: closes the game  <br>"
                + "  HELP BUTTON: shows this message  <br>"
                + "  MORE BUTTONS: !!!!  <br></H2>"
        );
    }
}
