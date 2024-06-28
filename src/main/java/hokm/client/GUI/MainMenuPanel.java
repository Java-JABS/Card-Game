package hokm.client.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenuPanel extends JPanel {
    String SERVER_IP;
    int SERVER_PORT;

    MainMenuPanel(){
        SERVER_IP = JOptionPane.showInputDialog("Please Enter Server IP.");
        SERVER_PORT = Integer.parseInt(JOptionPane.showInputDialog("Please Enter Server PORT."));
        //main frame :
        //this.setSize(getMaximumSize());
        //this.setResizable(false);
        this.setLayout(new GridLayout(1,1));

        //main menu panel :

        setLayout(new GridLayout(1,1));

        JLabel mainMenuLabel = new JLabel();
        mainMenuLabel.setLayout(new GridBagLayout());
        ImageIcon mainMenuLabelPicture = new ImageIcon("pictures/MainMenuLabelPicture.jpeg");
        mainMenuLabel.setIcon(mainMenuLabelPicture);

        JButton createNewGameButton = new JButton("Create New Game");
        createNewGameButton.setFocusable(false);
        createNewGameButton.setPreferredSize(new Dimension(300,100));
        createNewGameButton.setFont(new Font("Arial", Font. PLAIN, 30));
        GridBagConstraints createNewGameButtonGrid = new GridBagConstraints();
        createNewGameButtonGrid.gridx = 0;
        createNewGameButtonGrid.gridy = 0;
        createNewGameButtonGrid.insets = new Insets(5,5,5,5);
        createNewGameButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(MainMenuPanel.this);
                topFrame.remove(MainMenuPanel.this);
                topFrame.add(new RoomPanel());
                topFrame.revalidate();
                topFrame.repaint();
            }
            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {}
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                createNewGameButton.setBackground(Color.green);
            }
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                createNewGameButton.setBackground(new JButton().getBackground());
            }
        });

        JButton joinExistingGameButton = new JButton("Join Existing Game");
        joinExistingGameButton.setFocusable(false);
        joinExistingGameButton.setPreferredSize(new Dimension(300,100));
        joinExistingGameButton.setFont(new Font("Arial", Font. PLAIN, 30));
        GridBagConstraints joinExistingGameButtonGrid = new GridBagConstraints();
        joinExistingGameButtonGrid.gridx = 0;
        joinExistingGameButtonGrid.gridy = 1;
        joinExistingGameButtonGrid.insets = new Insets(5,5,5,5);
        joinExistingGameButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {}
            @Override
            public void mousePressed(MouseEvent mouseEvent) {}
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {}
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                joinExistingGameButton.setBackground(Color.green);
            }
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                joinExistingGameButton.setBackground(new JButton().getBackground());
            }
        });

        mainMenuLabel.add(joinExistingGameButton, joinExistingGameButtonGrid);
        mainMenuLabel.add(createNewGameButton, createNewGameButtonGrid);

        add(mainMenuLabel);
    }
}
