package hokm.client.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.lang.Thread.sleep;

public class RoomPanel extends JPanel {
    JTextArea names= new JTextArea();
    JButton startButton = new JButton("Start");

    public RoomPanel(){
        startButton.setPreferredSize(new Dimension(300,100));
        startButton.setFont(new Font("Arial", Font.BOLD, 25));
        GridBagConstraints startButtonGrid = new GridBagConstraints();
        startButtonGrid.gridx = 0;
        startButtonGrid.gridy = 0;
        startButtonGrid.insets = new Insets(5,5,5,5);
        startButton.setFocusable(false);
        startButton.addActionListener(actionEvent -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.remove(this);
            topFrame.add(new OuterGamePanel());
            topFrame.repaint();
            topFrame.revalidate();
        });
        startButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {}
            @Override
            public void mousePressed(MouseEvent mouseEvent) {}
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {}
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                startButton.setBackground(Color.green);
            }
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                startButton.setBackground(new JButton().getBackground());
            }
        });

        names.setPreferredSize(new Dimension(300,100));
        names.setFont(new Font("Arial", Font.BOLD, 25));
        GridBagConstraints namesGrid = new GridBagConstraints();
        namesGrid.gridx = 0;
        namesGrid.gridy = 1;
        namesGrid.insets = new Insets(5,5,5,5);

        setLayout(new GridBagLayout());
        add(startButton, startButtonGrid);
        add(names, namesGrid);
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
