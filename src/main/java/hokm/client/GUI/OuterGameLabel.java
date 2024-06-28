package hokm.client.GUI;

import javax.swing.*;
import java.awt.*;

public class OuterGameLabel extends JLabel {
    OuterGameLabel(){
        ImageIcon gameBackGround = new ImageIcon("pictures/GameBackGround.jpg");
        setIcon(gameBackGround);
        setLayout(new GridLayout(1,1));
        add(new OuterGamePanel());
    }
}
