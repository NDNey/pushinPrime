package com.servermonks.pushinprime;

import javax.sound.sampled.*;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoundPlayer extends Thread {

    private List<String> playList = Collections.synchronizedList(new ArrayList<>());
    private boolean playing = false;
    private boolean powerOn = true;
    private String repeatSound = "";
    private Clip clip;
    private float volume = 1.0f;

    /*

        Sound Player controls:
            volume up: up arrow
            volume down: down arrow
            mute: right arrow
            mute off: left arrow

     */

    public void SoundPlayer() {
    }

    public void run() {
        while(powerOn) {
            while(playList.isEmpty() || playing == true);
            synchronized (playList) {
                playSound(playList.get(0));
                removeSoundFile(playList.get(0));
            }
        }
    }

    public void playSound(String fileName) {
        try {
            playing = true;
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("resources/" + fileName).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            if (repeatSound == fileName) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    playing = false;
                }
            });
            clip.start();

        } catch (Exception ex) { ex.printStackTrace(); }
    }

    public synchronized void addSoundFile(String fileName) {
        addSoundFile(fileName, false);
    }

    public synchronized void addSoundFile(String fileName, boolean loop) {
        playList.add(fileName);
        if(loop) {
            repeatSound = fileName;
        }
    }

    public void removeSoundFile(String fileName) {
        playList.remove(fileName);
    }

    public void setVolume(float volumeAdjustment) {
        volumeAdjustment = volumeAdjustment * 2;
        float newVolume = volume + volumeAdjustment;
        newVolume = BigDecimal.valueOf(newVolume).setScale(1,RoundingMode.FLOOR)
                .floatValue();
        if(newVolume < 0.0f) {
            newVolume = 0.0f;
        } else if(newVolume > 1.0f) {
            newVolume = 1.0f;
        }
        volume = newVolume;
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        newVolume = 20f * (float) Math.log10(newVolume);
        gainControl.setValue(newVolume);
    }
    public void lowerVolume() {
        setVolume(-0.1f);
    }

    public void raiseVolume() {
        setVolume(0.1f);
    }

    public void mute() {
        setVolume(-1.0f);
    }

    public void unMute() {
        setVolume(1.0f);
    }
}