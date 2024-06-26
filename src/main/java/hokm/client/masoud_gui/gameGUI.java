package hokm.client.masoud_gui;

import javax.swing.*;
import java.awt.*;

public class gameGUI extends JFrame {

    public gameGUI(int width, int height){
        super.setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Up_player north = new Up_player(100, 100);
        add(north);
        setSize(width, height);
        // to add component
        setVisible(true);

    }

    public static void main(String[] args) {
        new gameGUI(1500, 800);
    }

}
