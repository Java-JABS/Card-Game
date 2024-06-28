package hokm.client.GUI;

import hokm.RoomUpdate;
import hokm.client.ClientRequestSender;
import hokm.messages.GameStartRequest;
import hokm.messages.RoomUpdateRequest;
import hokm.server.RequestException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.lang.Thread.sleep;

public class RoomPanel extends JPanel {

    JLabel roomLabel = new JLabel();
    JTextArea names= new JTextArea();
    JTextField showToken = new JTextField(SwingConstants.CENTER);
    JButton startButton = new JButton("Start");
    RoomUpdate roomUpdate = new RoomUpdate();
    public RoomPanel(){

        roomLabel.setLayout(new GridBagLayout());
        ImageIcon roomLabelPicture = new ImageIcon("pictures/MainMenuLabelPicture.jpeg");
        roomLabel.setIcon(roomLabelPicture);

        startButton.setPreferredSize(new Dimension(300,100));
        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        GridBagConstraints startButtonGrid = new GridBagConstraints();
        startButtonGrid.gridx = 0;
        startButtonGrid.gridy = 1;
        startButtonGrid.insets = new Insets(5,5,5,5);
        startButton.setFocusable(false);
        startButton.addActionListener(actionEvent -> {

        });
        startButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(RoomPanel.this);
                try{
                    topFrame.client.sendMessage(new GameStartRequest());
                    topFrame.remove(RoomPanel.this);
                    topFrame.add(new OuterGameLabel());
                    topFrame.repaint();
                    topFrame.revalidate();
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
                startButton.setBackground(Color.green);
            }
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                startButton.setBackground(new JButton().getBackground());
            }
        });

        names.setEditable(false);
        names.setPreferredSize(new Dimension(300,150));
        names.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        names.setLineWrap(true);
        names.setWrapStyleWord(true);
        names.setFont(new Font("Arial", Font.BOLD, 20));
        GridBagConstraints namesGrid = new GridBagConstraints();
        namesGrid.gridx = 0;
        namesGrid.gridy = 2;
        namesGrid.insets = new Insets(5,5,5,5);

        showToken.setPreferredSize(new Dimension(300,30));
        showToken.setEditable(false);
        showToken.setBackground(Color.WHITE);
        showToken.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        showToken.setFont(new Font("Arial", Font.BOLD, 15));
        GridBagConstraints showTokenGrid = new GridBagConstraints();
        showTokenGrid.gridx = 0;
        showTokenGrid.gridy = 0;
        showTokenGrid.insets = new Insets(5,5,5,5);

        setLayout(new GridLayout(1,1));
        this.roomLabel.add(startButton, startButtonGrid);
        this.roomLabel.add(names, namesGrid);
        this.roomLabel.add(showToken, showTokenGrid);

        this.add(roomLabel);
        new Thread(()->{
            showToken.setText(" Room token : ");
            while (true){
                MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(RoomPanel.this);
                try {
                    RoomUpdate newRoomUpdate = ClientRequestSender.gsonAgent.fromJson(topFrame.client.sendMessage(new RoomUpdateRequest()), RoomUpdate.class);
                    if (newRoomUpdate.getNumber() - roomUpdate.getNumber() != 0) {
                        if(!newRoomUpdate.getPlayerNames().equals(roomUpdate.getPlayerNames())){
                            String roomMembers = " Players in room :\n";
                            for (int i = 0; i < newRoomUpdate.getPlayerNames().size(); i++) {
                                roomMembers = roomMembers +" Player <" + (i+1) + ">: " + newRoomUpdate.getPlayerNames().get(i) + "\n";
                            }
                            names.setText(roomMembers);
                        }
                    }
                    if(roomUpdate.getGameStarted()){
                        topFrame.remove(RoomPanel.this);
                        topFrame.add(new OuterGameLabel());
                        topFrame.repaint();
                        topFrame.revalidate();
                    }
                    roomUpdate=newRoomUpdate;
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }catch (RequestException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
                    topFrame.remove(RoomPanel.this);
                    topFrame.add(new MainMenuPanel());
                    topFrame.repaint();
                    topFrame.revalidate();
                }
            }
        }).start();

    }
}
