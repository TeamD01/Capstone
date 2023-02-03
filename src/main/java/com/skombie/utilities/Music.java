package com.skombie.utilities;


import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class Music {
    public static void playSound(InputStream soundFile){
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        }
        catch(IOException | UnsupportedAudioFileException | LineUnavailableException e){
            e.printStackTrace();
        }
    }
}