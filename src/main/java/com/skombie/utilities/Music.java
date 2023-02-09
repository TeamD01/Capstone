package com.skombie.utilities;


import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Music {
    private static Clip clip;

    public static void playSound(InputStream soundFile) {
        try {
            InputStream bufferedIn = new BufferedInputStream(soundFile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {    // Print a stack trace if an exception occurs    e.printStackTrace();}
        }
    }

    public static void stopSound() {

        clip.close();
    }

}