package hokm.client.masoud_gui;

import javax.swing.*;
import java.awt.*;

public class Up_player extends JPanel {

    private int width;
    private int height;

    public Up_player(int _width, int _height){
        JLabel pic = new JLabel();
        super.setLayout(new BorderLayout());
        pic.setSize(_width/2, _height/2);
        add(pic, BorderLayout.CENTER);
        pic.setIcon(new ImageIcon(new ImageIcon("pictures/Person/person.png").getImage().getScaledInstance(_width/2,-1, Image.SCALE_SMOOTH)));
        JLabel text = new JLabel("Player");
        text.setSize(50, 30);
        add(text, BorderLayout.SOUTH);
        pic.setVisible(true);
        text.setVisible(true);

    }

}
