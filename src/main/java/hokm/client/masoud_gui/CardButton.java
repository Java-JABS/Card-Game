package hokm.client.masoud_gui;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CardButton extends JButton {
    private int width;
    private int height;
    private String path;

    public void setPicToCard(){
        setIcon(new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(this.width,this.height, Image.SCALE_SMOOTH)));
    }
    public static void playSound(String soundFile) {
        try {
            // Load the sound file
            File soundPath = new File(soundFile);
            if (soundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("Sound file not found: " + soundFile);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Wrong step. :-|");
        }
    }
}
