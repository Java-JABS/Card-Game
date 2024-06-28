package hokm.client.scratch;

import javax.swing.*;
import java.awt.*;

public class Scratch2 {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Scratch2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);

        // Set the layout manager
        frame.setLayout(new BorderLayout());

        // Create some buttons
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();

        // Set size to panels
        panel1.setPreferredSize(new Dimension(200, 300));
        panel2.setPreferredSize(new Dimension(200, 300));
        panel3.setPreferredSize(new Dimension(200, 200));
        panel4.setPreferredSize(new Dimension(200, 200));
        panel5.setPreferredSize(new Dimension(200, 200));

        panel1.setBackground(Color.MAGENTA);
        panel2.setBackground(Color.RED);
        panel3.setBackground(Color.GRAY);
        panel4.setBackground(Color.BLUE);
        panel5.setBackground(Color.ORANGE);

        // Create parent panels with FlowLayout to contain the sized panels
        JPanel northParent = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        northParent.add(panel1);

        JPanel southParent = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        southParent.add(panel2);

        // Add parent panels to the frame
        frame.add(northParent, BorderLayout.NORTH);
        frame.add(southParent, BorderLayout.SOUTH);
        frame.add(panel3, BorderLayout.EAST);
        frame.add(panel4, BorderLayout.WEST);
        frame.add(panel5, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }
}
