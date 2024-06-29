package hokm.client.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OuterGameLabel extends JLabel {
    OuterGameLabel(){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("pictures/GameBackGround.jpg"));
        } catch (IOException e) {
            System.out.println("An error acquired.");
        }
        Image dimg = img.getScaledInstance(1900, -1, Image.SCALE_SMOOTH);
        ImageIcon gameBackGround = new ImageIcon(dimg);
        setIcon(gameBackGround);
        setLayout(new GridLayout(1,1));
        add(new OuterGamePanel());
    }
}
