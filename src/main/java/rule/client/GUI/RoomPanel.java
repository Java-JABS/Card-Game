package rule.client.GUI;

import rule.RoomUpdate;
import rule.client.ClientRequestSender;
import rule.messages.GameStartRequest;
import rule.messages.LeaveRequest;
import rule.messages.RequestException;
import rule.messages.RoomUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.lang.Thread.sleep;

public class RoomPanel extends JPanel {

    private final Logger logger = LoggerFactory.getLogger(RoomPanel.class);
    JLabel roomLabel = new JLabel();
    JTextArea names = new JTextArea();
    JTextField showToken = new JTextField(SwingConstants.CENTER);
    JButton startButton = new JButton("Start Game");
    JButton leaveRoomButton = new JButton("Leave Room");
    RoomUpdate roomUpdate = new RoomUpdate();
    Color blackGUI = new Color(0xDC000000, false);
    Color whiteGUI = new Color(0x9FEBEBF1, true);

    public RoomPanel() {

        roomLabel.setLayout(new GridBagLayout());
        ImageIcon roomLabelPicture = new ImageIcon(Assets.getImageIcon("background.jpg").getImage().getScaledInstance(1920, -1, Image.SCALE_SMOOTH));
        roomLabel.setIcon(roomLabelPicture);

        leaveRoomButton.setBackground(blackGUI);
        leaveRoomButton.setForeground(Color.WHITE);
        leaveRoomButton.setOpaque(true);
        leaveRoomButton.setPreferredSize(new Dimension(300, 70));
        leaveRoomButton.setFont(new Font("Arial", Font.BOLD, 30));
        GridBagConstraints leaveRoomButtonGrid = new GridBagConstraints();
        leaveRoomButtonGrid.gridx = 0;
        leaveRoomButtonGrid.gridy = 2;
        leaveRoomButtonGrid.insets = new Insets(5, 5, 5, 5);
        leaveRoomButton.setFocusable(false);
        leaveRoomButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(RoomPanel.this);
                try {
                    logger.info("Request for leave room.");
                    topFrame.client.sendMessage(new LeaveRequest(false));
                    topFrame.remove(RoomPanel.this);
                    topFrame.repaint();
                    topFrame.revalidate();
                    topFrame.add(new MainMenuPanel());
                    topFrame.repaint();
                    topFrame.revalidate();
                } catch (RequestException e) {
                    logger.warn("Failed to leave, Reason: {}", e.getMessage());
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

                leaveRoomButton.setBackground(whiteGUI);
                leaveRoomButton.setForeground(Color.BLACK);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                leaveRoomButton.setBackground(blackGUI);
                leaveRoomButton.setForeground(Color.WHITE);
                repaint();
            }
        });


        startButton.setBackground(blackGUI);
        startButton.setForeground(Color.WHITE);
        startButton.setOpaque(true);
        startButton.setPreferredSize(new Dimension(300, 70));
        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        GridBagConstraints startButtonGrid = new GridBagConstraints();
        startButtonGrid.gridx = 0;
        startButtonGrid.gridy = 1;
        startButtonGrid.insets = new Insets(5, 5, 5, 5);
        startButton.setFocusable(false);
        startButton.addActionListener(actionEvent -> {

        });
        startButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(RoomPanel.this);
                try {
                    logger.info("Request for game start.");
                    topFrame.client.sendMessage(new GameStartRequest());
                    topFrame.remove(RoomPanel.this);
                    topFrame.repaint();
                    topFrame.revalidate();
                    topFrame.add(new OuterGameLabel());
                    topFrame.repaint();
                    topFrame.revalidate();
                } catch (RequestException e) {
                    logger.warn("Failed to request, Reason: {}", e.getMessage());
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

                startButton.setBackground(whiteGUI);
                startButton.setForeground(Color.BLACK);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                startButton.setBackground(blackGUI);
                startButton.setForeground(Color.WHITE);
                repaint();
            }
        });

        names.setBackground(Color.BLACK);
        names.setForeground(Color.WHITE);
        names.setEditable(false);
        names.setPreferredSize(new Dimension(300, 150));
        names.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        names.setLineWrap(true);
        names.setWrapStyleWord(true);
        names.setFont(new Font("Arial", Font.BOLD, 20));
        GridBagConstraints namesGrid = new GridBagConstraints();
        namesGrid.gridx = 0;
        namesGrid.gridy = 3;
        namesGrid.insets = new Insets(5, 5, 5, 5);

        showToken.setBackground(Color.BLACK);
        showToken.setForeground(Color.WHITE);
        showToken.setPreferredSize(new Dimension(300, 30));
        showToken.setEditable(false);
        showToken.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        showToken.setFont(new Font("Arial", Font.BOLD, 15));
        GridBagConstraints showTokenGrid = new GridBagConstraints();
        showTokenGrid.gridx = 0;
        showTokenGrid.gridy = 0;
        showTokenGrid.insets = new Insets(5, 5, 5, 5);

        setLayout(new GridLayout(1, 1));
        this.roomLabel.add(startButton, startButtonGrid);
        this.roomLabel.add(leaveRoomButton, leaveRoomButtonGrid);
        this.roomLabel.add(names, namesGrid);
        this.roomLabel.add(showToken, showTokenGrid);

        this.add(roomLabel);
        new Thread(() -> {
            while (true) {
                MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(RoomPanel.this);
                try {
                    logger.debug("Request for room update:-)");
                    RoomUpdate newRoomUpdate = ClientRequestSender.gsonAgent.fromJson(topFrame.client.sendMessage(new RoomUpdateRequest()), RoomUpdate.class);
                    if (newRoomUpdate.getNumber() - roomUpdate.getNumber() != 0) {
                        if (!newRoomUpdate.getPlayerNames().equals(roomUpdate.getPlayerNames())) {
                            StringBuilder roomMembers = new StringBuilder(" Players in room :\n");
                            for (int i = 0; i < newRoomUpdate.getPlayerNames().size(); i++) {
                                roomMembers.append(" Player <").append(i + 1).append("> : ").append(newRoomUpdate.getPlayerNames().get(i)).append("\n");
                            }
                            names.setText(roomMembers.toString());
                        }
                    }
                    showToken.setText(" Room token: " + newRoomUpdate.getToken());
                    if (roomUpdate.getGameStarted()) {
                        topFrame.remove(RoomPanel.this);
                        topFrame.repaint();
                        topFrame.revalidate();
                        topFrame.add(new OuterGameLabel());
                        topFrame.repaint();
                        topFrame.revalidate();
                        break;
                    }
                    roomUpdate = newRoomUpdate;
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (RequestException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
                    topFrame.remove(RoomPanel.this);
                    topFrame.repaint();
                    topFrame.revalidate();
                    topFrame.add(new MainMenuPanel());
                    topFrame.repaint();
                    topFrame.revalidate();
                    break;
                } catch (Exception e) {
                    break;
                }
            }
        }).start();
    }
}
