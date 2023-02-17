package com.skombie.utilities;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Music {
    private static Clip clip;
    public static float previousVolume = 0;
    public static float currentVolume = -10;
    public static FloatControl fc;
    private static boolean mute = false;

    public static void playSound(InputStream soundFile) {
        try {
            InputStream bufferedIn = new BufferedInputStream(soundFile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {    // Print a stack trace if an exception occurs    e.printStackTrace();}
        }
    }

    public static void stop() {

        clip.stop();
    }

    public static void start(){

        clip.start();
    }

    public static void volumeMute() {
        if (!mute) {
            setPreviousVolume(getCurrentVolume());
            setCurrentVolume(-60.0f);
            fc.setValue(currentVolume);
            setMute(true);
        } else {
            setCurrentVolume(getPreviousVolume());
            fc.setValue(currentVolume + 10);
            setMute(false);
        }
    }

    public static float getPreviousVolume() {
        return previousVolume;
    }

    public static void setPreviousVolume(float previousVolume) {
        Music.previousVolume = previousVolume;
    }

    public static float getCurrentVolume() {
        return currentVolume;
    }

    public static void setCurrentVolume(float currentVolume) {
        Music.currentVolume = currentVolume;
    }

    public static void setMute(boolean mute) {
        Music.mute = mute;
    }
}