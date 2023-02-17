package com.skombie.UI;

import org.junit.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.Assert.*;

public class mainFrameTest {
//Positive tests
    @Test
    public void shouldReturnButtonStartText() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals("START", gameScreen.gameStart.getText());
    }

    @Test
    public void shouldReturnButtonHelpText() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals("HELP", gameScreen.gameHelp.getText());
    }

    @Test
    public void shouldReturnButtonQuitText() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals("QUIT", gameScreen.gameQuit.getText());
    }

    @Test
    public void shouldReturnButtonHallwayText() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals("HALLWAY", gameScreen.hallway.getText());
    }

    @Test
    public void shouldReturnButtonLivingroomText() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals("LIVINGROOM", gameScreen.livingroom.getText());
    }

    @Test
    public void shouldReturnButtonOfficeText() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals("OFFICE", gameScreen.office.getText());
    }

    @Test
    public void shouldReturnButtonAtticText() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals("ATTIC", gameScreen.attic.getText());
    }

    @Test
    public void shouldReturnButtonBasementText() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals("BASEMENT", gameScreen.basement.getText());
    }

    @Test
    public void shouldReturnButtonKitchenText() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals("KITCHEN", gameScreen.kitchen.getText());
    }

    @Test
    public void shouldReturnButtonBackyardText() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals("BACKYARD", gameScreen.backyard.getText());
    }

    @Test
    public void shouldReturnButtonBedroomText() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals("BEDROOM", gameScreen.bedroom.getText());
    }


    @Test
    public void shouldReturnBedroomButtonColorGreen() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals(Color.green, gameScreen.bedroom.getBackground());
    }

    @Test
    public void shouldReturnHallwayButtonColorGreen() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals(Color.green, gameScreen.hallway.getBackground());
    }

    @Test
    public void shouldReturnLivingroomButtonColorGreen() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals(Color.green, gameScreen.livingroom.getBackground());
    }

    @Test
    public void shouldReturnOfficeButtonColorGreen() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals(Color.green, gameScreen.office.getBackground());
    }

    @Test
    public void shouldReturnBasementButtonColorGreen() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals(Color.green, gameScreen.basement.getBackground());
    }

    @Test
    public void shouldReturnKitchenButtonColorGreen() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals(Color.green, gameScreen.kitchen.getBackground());
    }

    @Test
    public void shouldReturnBackyardButtonColorGreen() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals(Color.green, gameScreen.backyard.getBackground());
    }

    @Test
    public void shouldReturnAtticButtonColorGreen() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertEquals(Color.green, gameScreen.attic.getBackground());
    }

    @Test
    public void shouldReturnAtticButtonEnabled() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertTrue(gameScreen.attic.isEnabled());
    }

    @Test
    public void shouldReturnLivingroomButtonEnabled() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertTrue(gameScreen.livingroom.isEnabled());
    }

    @Test
    public void shouldReturnHallwayButtonEnabled() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertTrue(gameScreen.hallway.isEnabled());
    }

    @Test
    public void shouldReturnOfficeButtonEnabled() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertTrue(gameScreen.office.isEnabled());
    }

    @Test
    public void shouldReturnBasementButtonEnabled() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertTrue(gameScreen.basement.isEnabled());
    }

    @Test
    public void shouldReturnBedroomButtonEnabled() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertTrue(gameScreen.bedroom.isEnabled());
    }

    @Test
    public void shouldReturnKitchenButtonEnabled() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertTrue(gameScreen.kitchen.isEnabled());
    }

    @Test
    public void shouldReturnBackyardButtonEnabled() {
        new JButton();
        mainFrame gameScreen = new mainFrame();
        assertTrue(gameScreen.backyard.isEnabled());
    }
}