package hokm.client.GUI;

import javax.swing.*;
import java.io.IOException;

public class Launcher{
    public static void main(String[] args) throws IOException {
        JFrame jFrame = new JFrame("Java-JABS Hokm");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.add(new MainMenuPanel());
        jFrame.pack();
        jFrame.setVisible(true);
    }

}
