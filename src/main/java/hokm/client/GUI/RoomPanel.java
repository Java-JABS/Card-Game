package hokm.client.GUI;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class RoomPanel extends JPanel {
    JTextArea names= new JTextArea();
    JButton startButton = new JButton("Start");

    public RoomPanel(){
        setLayout(new FlowLayout());
        add(startButton);
        add(names);
        new Thread(()->{
            Integer a=0;
            while (true){
                try {
                    sleep(1000);
                    names.setText(String.valueOf(a++));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
