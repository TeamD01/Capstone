package com.skombie.utilities;


import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class Music {
    private static Clip clip;
    public static void playSound(InputStream soundFile){
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        }
        catch(IOException | UnsupportedAudioFileException | LineUnavailableException e){
            e.printStackTrace();
        }
    }

    public static void stopSound(){

        clip.close();
    }

}