package hokm.client.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OuterGameLabel extends JLabel {
    OuterGameLabel(){
        setIcon(new ImageIcon(Assets.getImageIcon("GameBackGround.jpg").getImage().getScaledInstance(1900, -1, Image.SCALE_SMOOTH)));
        setLayout(new GridLayout(1,1));
        add(new OuterGamePanel());
    }
}
