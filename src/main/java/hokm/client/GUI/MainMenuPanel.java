package hokm.client.GUI;

import hokm.messages.JoinRequest;
import hokm.messages.RoomCreateRequest;
import hokm.server.RequestException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenuPanel extends JPanel {
    MainMenuPanel(){
        //main frame :
        //this.setSize(getMaximumSize());
        //this.setResizable(false);
        this.setLayout(new GridLayout(1,1));

        //main menu panel :

        setLayout(new GridLayout(1,1));

        JLabel mainMenuLabel = new JLabel();
        mainMenuLabel.setLayout(new GridBagLayout());
        ImageIcon mainMenuLabelPicture = new ImageIcon(this.getClass().getClassLoader().getResource("pictures/MainMenuLabelPicture.jpeg"));
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
                MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(MainMenuPanel.this);
                try {
                    String roomToken = topFrame.client.sendMessage(new RoomCreateRequest());
                    topFrame.remove(MainMenuPanel.this);
                    topFrame.add(new RoomPanel(roomToken));
                    topFrame.revalidate();
                    topFrame.repaint();
                } catch (RequestException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
                }
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
            public void mouseClicked(MouseEvent mouseEvent) {
                MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(MainMenuPanel.this);
                try {
                    String token = JOptionPane.showInputDialog("Enter room token:");
                    topFrame.client.sendMessage(new JoinRequest(token));
                    topFrame.remove(MainMenuPanel.this);
                    topFrame.add(new RoomPanel(token));
                    topFrame.revalidate();
                    topFrame.repaint();
                } catch (RequestException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
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
