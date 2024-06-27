package hokm.client.masoud_gui;

import hokm.Card;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CardButton extends JButton {
    private int width;
    private int height;
    private Card card;
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

    public Card getCard(){ return this.card; }
    public void setCard(Card _card){
        this.card = _card;
    }

    public CardButton(){
        // Mouse-listener
        // ToDo
    }
}
