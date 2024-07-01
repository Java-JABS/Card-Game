package hokm.client.GUI;

import hokm.messages.JoinRequest;
import hokm.messages.RoomCreateRequest;
import hokm.server.RequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

public class MainMenuPanel extends JPanel {
    private final Logger logger = LoggerFactory.getLogger(MainFrame.class);
    Color blackGUI = new Color(0xDC000000, false);
    Color whiteGUI = new Color(0x9FEBEBF1, true);

    MainMenuPanel() {

        this.setLayout(new GridLayout(1, 1));

        setLayout(new GridLayout(1, 1));

        JLabel mainMenuLabel = new JLabel();
        mainMenuLabel.setLayout(new GridBagLayout());
        ImageIcon mainMenuLabelPicture = new ImageIcon(Assets.getImageIcon("intro.jpg").getImage().getScaledInstance(1000, -1, Image.SCALE_SMOOTH));
        mainMenuLabel.setIcon(mainMenuLabelPicture);
        JButton createNewGameButton = new JButton("Create New Game");
        createNewGameButton.setBackground(blackGUI);
        createNewGameButton.setForeground(Color.WHITE);
        createNewGameButton.setFocusable(false);
        createNewGameButton.setPreferredSize(new Dimension(300, 100));
        createNewGameButton.setFont(new Font("Arial", Font.PLAIN, 30));
        GridBagConstraints createNewGameButtonGrid = new GridBagConstraints();
        createNewGameButtonGrid.gridx = 0;
        createNewGameButtonGrid.gridy = 0;
        createNewGameButtonGrid.insets = new Insets(5, 5, 5, 5);
        createNewGameButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(MainMenuPanel.this);
                try {
                    logger.info("Try to create a room");
                    String roomToken = topFrame.client.sendMessage(new RoomCreateRequest());
                    topFrame.remove(MainMenuPanel.this);
                    topFrame.add(new RoomPanel(roomToken));
                    topFrame.revalidate();
                    topFrame.repaint();
                } catch (RequestException e) {
                    logger.warn("Unable to create room, Reason: {}", e.getMessage());
                    if(e.getMessage().equals("Player is already in a room!"))
                    {
                        logger.info("Try to join previous room.");
                        topFrame.remove(MainMenuPanel.this);
                        topFrame.add(new RoomPanel(""));
                        topFrame.revalidate();
                        topFrame.repaint();
                    }
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {


                createNewGameButton.setBackground(whiteGUI);
                createNewGameButton.setForeground(Color.BLACK);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                createNewGameButton.setBackground(blackGUI);
                createNewGameButton.setForeground(Color.WHITE);
                repaint();
            }
        });

        JButton joinExistingGameButton = new JButton("Join Existing Game");
        joinExistingGameButton.setBackground(blackGUI);
        joinExistingGameButton.setForeground(Color.WHITE);
        joinExistingGameButton.setFocusable(false);
        joinExistingGameButton.setPreferredSize(new Dimension(300, 100));
        joinExistingGameButton.setFont(new Font("Arial", Font.PLAIN, 30));
        GridBagConstraints joinExistingGameButtonGrid = new GridBagConstraints();
        joinExistingGameButtonGrid.gridx = 0;
        joinExistingGameButtonGrid.gridy = 1;
        joinExistingGameButtonGrid.insets = new Insets(5, 5, 5, 5);
        joinExistingGameButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(MainMenuPanel.this);
                try {
                    String token = JOptionPane.showInputDialog("Enter room token:");
                    logger.info("Try to join a room.");
                    topFrame.client.sendMessage(new JoinRequest(token));
                    topFrame.remove(MainMenuPanel.this);
                    topFrame.add(new RoomPanel(token));
                    topFrame.revalidate();
                    topFrame.repaint();
                } catch (RequestException e) {
                    if(e.getMessage().equals("Player is already in a room!"))
                    {
                        logger.info("Try to join previous room.");
                        topFrame.remove(MainMenuPanel.this);
                        topFrame.add(new RoomPanel(""));
                        topFrame.revalidate();
                        topFrame.repaint();
                    }
                    logger.warn("Unable to Join , Reason : {}", e.getMessage());
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {


                joinExistingGameButton.setBackground(whiteGUI);
                joinExistingGameButton.setForeground(Color.BLACK);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                joinExistingGameButton.setBackground(blackGUI);
                joinExistingGameButton.setForeground(Color.WHITE);
                repaint();
            }
        });

        mainMenuLabel.add(joinExistingGameButton, joinExistingGameButtonGrid);
        mainMenuLabel.add(createNewGameButton, createNewGameButtonGrid);

        add(mainMenuLabel);
    }
}
