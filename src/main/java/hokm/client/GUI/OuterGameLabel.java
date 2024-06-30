package hokm.client.GUI;

import javax.swing.*;
import java.awt.*;

public class OuterGameLabel extends JLabel {
    OuterGameLabel() {
        setIcon(new ImageIcon(Assets.getImageIcon("GameBackGround.jpg").getImage().getScaledInstance(1920, -1, Image.SCALE_SMOOTH)));
        setLayout(new GridLayout(1, 1));
        add(new OuterGamePanel());
    }
}
