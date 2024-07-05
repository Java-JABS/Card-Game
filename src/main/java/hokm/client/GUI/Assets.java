package hokm.client.GUI;

import hokm.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;

public class Assets {
    static Logger logger = LoggerFactory.getLogger(Assets.class);
    static Clip backgroundClip;
    static AudioInputStream backgroundAudioInput;

    public static ImageIcon getImageIcon(String filePath) {
        return new ImageIcon(getResource("pictures/" + filePath));
    }

    public static ImageIcon getCardImageIcon(Card card) {
        return getImageIcon("cards/" + card.suit() + '/' + card.value() + ".png");
    }

    public static URL getResource(String filename) {
        return Assets.class.getClassLoader().getResource(filename);
    }

    public static void playBackgroundMusic(String filename) {
        if (backgroundClip == null) {
            try {
                backgroundClip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                logger.warn("Audio is not available!");
                return;
            }
        }
        if (backgroundAudioInput != null) {
            backgroundClip.stop();
        }
        try {
            backgroundAudioInput = AudioSystem.getAudioInputStream(getResource("musics/" + filename));
            backgroundClip.open(backgroundAudioInput);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            logger.warn("Unsupported audio file!");
        } catch (IOException e) {
            logger.warn("Error accessing audio file!");
        } catch (LineUnavailableException e) {
            logger.warn("Couldn't get a line to play Audio!");
        }
    }
}
