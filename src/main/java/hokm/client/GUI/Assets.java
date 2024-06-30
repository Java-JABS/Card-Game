package hokm.client.GUI;

import hokm.Card;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Assets {
    public static ImageIcon getImageIcon(String filePath) {
        return new ImageIcon(Assets.class.getClassLoader().getResource("pictures/" + filePath));
    }

    public static ImageIcon getCardImageIcon(Card card) {
        return getImageIcon("cards/" + card.suit() + '/' + card.value() + ".png");
    }

    public static void playSound(String path) {
        try {
            // Load the sound file
            File soundPath = new File(path);
            if (soundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("Sound file not found: " + path);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Wrong step. :-|");
        }
    }
}
