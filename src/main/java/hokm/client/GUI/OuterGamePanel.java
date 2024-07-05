package hokm.client.GUI;

import hokm.CardsSuit;
import hokm.GameUpdate;
import hokm.client.ClientRequestSender;
import hokm.messages.*;
import hokm.server.GameState;
import hokm.server.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

import static java.lang.Thread.sleep;

public class OuterGamePanel extends JPanel {

    private final Logger logger = LoggerFactory.getLogger(OuterGamePanel.class);
    JLabel team1Sets = new JLabel("0", SwingConstants.CENTER);
    JLabel team1Rounds = new JLabel("0", SwingConstants.CENTER);
    JLabel team2Sets = new JLabel("0", SwingConstants.CENTER);
    JLabel team2Rounds = new JLabel("0", SwingConstants.CENTER);
    GamePanel gamePanel = new GamePanel();
    GameUpdate gameUpdate = new GameUpdate();

    OuterGamePanel(OuterGameLabel topPanel) {
        team1Sets.setOpaque(true);
        team1Sets.setBackground(Color.GRAY);
        team1Sets.setForeground(Color.WHITE);
        team2Sets.setOpaque(true);
        team2Sets.setBackground(Color.GRAY);
        team2Sets.setForeground(Color.WHITE);
        team1Rounds.setOpaque(true);
        team1Rounds.setBackground(Color.GRAY);
        team1Rounds.setForeground(Color.WHITE);
        team2Rounds.setOpaque(true);
        team2Rounds.setBackground(Color.GRAY);
        team2Rounds.setForeground(Color.WHITE);

        this.setOpaque(false);

        setLayout(new BorderLayout());
        add(gamePanel, BorderLayout.CENTER);

        JPanel upperPanel = new JPanel();
        upperPanel.setOpaque(false);
        upperPanel.setLayout(new GridBagLayout());

        JLabel team = new JLabel("Team", SwingConstants.CENTER);
        team.setBackground(Color.GRAY);
        team.setForeground(Color.WHITE);
        team.setOpaque(true);
        team.setPreferredSize(new Dimension(175, 25));
        team.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints teamGrid = new GridBagConstraints();
        teamGrid.gridx = 0;
        teamGrid.gridy = 0;
        teamGrid.insets = new Insets(1, 1, 1, 1);

        JLabel sets = new JLabel("Set", SwingConstants.CENTER);
        sets.setBackground(Color.GRAY);
        sets.setForeground(Color.WHITE);
        sets.setOpaque(true);
        sets.setPreferredSize(new Dimension(50, 25));
        sets.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints setsGrid = new GridBagConstraints();
        setsGrid.gridx = 1;
        setsGrid.gridy = 0;
        setsGrid.insets = new Insets(1, 1, 1, 1);

        JLabel round = new JLabel("Round", SwingConstants.CENTER);
        round.setBackground(Color.GRAY);
        round.setForeground(Color.WHITE);
        round.setOpaque(true);
        round.setPreferredSize(new Dimension(50, 25));
        round.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints roundGrid = new GridBagConstraints();
        roundGrid.gridx = 2;
        roundGrid.gridy = 0;
        roundGrid.insets = new Insets(1, 1, 1, 1);


        JLabel team1 = new JLabel("Team1", SwingConstants.CENTER);
        team1.setBackground(Color.GRAY);
        team1.setForeground(Color.WHITE);
        team1.setOpaque(true);
        team1.setPreferredSize(new Dimension(175, 25));
        team1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints team1Grid = new GridBagConstraints();
        team1Grid.gridx = 0;
        team1Grid.gridy = 1;
        team1Grid.insets = new Insets(1, 1, 1, 1);


        JLabel team2 = new JLabel("Team2", SwingConstants.CENTER);
        team2.setOpaque(true);
        team2.setBackground(Color.GRAY);
        team2.setForeground(Color.WHITE);
        team2.setPreferredSize(new Dimension(175, 25));
        team2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints team2Grid = new GridBagConstraints();
        team2Grid.gridx = 0;
        team2Grid.gridy = 2;
        team2Grid.insets = new Insets(1, 1, 1, 1);

        this.team1Sets.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.team1Sets.setPreferredSize(new Dimension(50, 25));
        GridBagConstraints team1SetsGrid = new GridBagConstraints();
        team1SetsGrid.gridx = 1;
        team1SetsGrid.gridy = 1;
        team1SetsGrid.insets = new Insets(1, 1, 1, 1);

        this.team1Rounds.setPreferredSize(new Dimension(50, 25));
        this.team1Rounds.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints team1RoundsGrid = new GridBagConstraints();
        team1RoundsGrid.gridx = 2;
        team1RoundsGrid.gridy = 1;
        team1RoundsGrid.insets = new Insets(1, 1, 1, 1);

        this.team2Sets.setPreferredSize(new Dimension(50, 25));
        this.team2Sets.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints team2SetsGrid = new GridBagConstraints();
        team2SetsGrid.gridx = 1;
        team2SetsGrid.gridy = 2;
        team2SetsGrid.insets = new Insets(1, 1, 1, 1);

        this.team2Rounds.setPreferredSize(new Dimension(50, 25));
        this.team2Rounds.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints team2RoundsGrid = new GridBagConstraints();
        team2RoundsGrid.gridx = 2;
        team2RoundsGrid.gridy = 2;
        team2RoundsGrid.insets = new Insets(1, 1, 1, 1);

        JPanel scoreTablePanel = new JPanel();
        scoreTablePanel.setLayout(new GridBagLayout());
        GridBagConstraints scoreTablePanelGrid = new GridBagConstraints();
        scoreTablePanelGrid.gridx = 1;
        scoreTablePanelGrid.gridy = 0;
        scoreTablePanelGrid.insets = new Insets(0, 0, 0, 0);
        scoreTablePanel.add(team, teamGrid);
        scoreTablePanel.add(sets, setsGrid);
        scoreTablePanel.add(round, roundGrid);
        scoreTablePanel.add(team1, team1Grid);
        scoreTablePanel.add(team2, team2Grid);
        scoreTablePanel.add(this.team1Sets, team1SetsGrid);
        scoreTablePanel.add(this.team1Rounds, team1RoundsGrid);
        scoreTablePanel.add(this.team2Sets, team2SetsGrid);
        scoreTablePanel.add(this.team2Rounds, team2RoundsGrid);
        scoreTablePanel.setOpaque(false);
        upperPanel.add(scoreTablePanel, scoreTablePanelGrid);

        JLabel hokmIconLabel = new JLabel();
        hokmIconLabel.setForeground(Color.BLACK);
        hokmIconLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        hokmIconLabel.setPreferredSize(new Dimension(78, 78));
        GridBagConstraints hokmIconLabelGrid = new GridBagConstraints();
        hokmIconLabelGrid.gridx = 2;
        hokmIconLabelGrid.gridy = 0;
        hokmIconLabelGrid.insets = new Insets(0, 50, 0, 0);
        upperPanel.add(hokmIconLabel, hokmIconLabelGrid);

        JButton leaveGameButton = new JButton();
        leaveGameButton.setOpaque(true);
        leaveGameButton.setBackground(new Color(0x0FFFFFF, true));
        leaveGameButton.setPreferredSize(new Dimension(50, 50));
        leaveGameButton.setIcon(new ImageIcon(Assets.getImageIcon("LeaveGameIcon.png").getImage().getScaledInstance(50, -1, Image.SCALE_SMOOTH)));
        leaveGameButton.setFont(new Font("Arial", Font.BOLD, 15));
        leaveGameButton.setBorder(null);
        GridBagConstraints leaveGameButtonGrid = new GridBagConstraints();
        leaveGameButtonGrid.gridx = 0;
        leaveGameButtonGrid.gridy = 0;
        leaveGameButtonGrid.insets = new Insets(0, 0, 0, 50);
        leaveGameButton.setFocusable(false);
        leaveGameButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(OuterGamePanel.this);
                try {
                    logger.info("Request for leaving game.");
                    topFrame.client.sendMessage(new LeaveRequest(true));
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
                leaveGameButton.setBorder(BorderFactory.createLineBorder(new Color(0xDA1629), 5));
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                leaveGameButton.setBorder(null);
                repaint();
            }
        });
        upperPanel.add(leaveGameButton, leaveGameButtonGrid);


        this.add(upperPanel, BorderLayout.NORTH);
        new Thread(() -> {
            MainFrame topFrame = null;
            while (topFrame == null) {
                topFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
            }
            topFrame.setExtendedState(topFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            topFrame.setMinimumSize(new Dimension(1140, 1000));
            boolean isTeamNamesSet = false;
            while (true) {
                try {
                    sleep(1000);
                    try {
                        GameUpdate lastGameUpdate = gameUpdate;
                        logger.trace("Request for Game Update :-)");
                        GameUpdate newGameUpdate = ClientRequestSender.gsonAgent.fromJson(topFrame.client.sendMessage(new GameUpdateRequest(gameUpdate.getNumber() == 0)), GameUpdate.class);
                        switch (newGameUpdate.getNumber() - gameUpdate.getNumber()) {
                            case 0:
                                break;
                            default:
                                logger.warn("Update is behind so get a major update!");
                                newGameUpdate = ClientRequestSender.gsonAgent.fromJson(topFrame.client.sendMessage(new GameUpdateRequest(true)), GameUpdate.class);
                            case 1:
                                newGameUpdate.updatesFrom(gameUpdate);
                                gameUpdate.update(newGameUpdate);
                                if (newGameUpdate.getGameState() == GameState.NEW_SET || (lastGameUpdate.getGameState() == GameState.HOKM && lastGameUpdate.getCurrentRuler() == lastGameUpdate.getYourIndex())) {
                                    logger.debug("Clearing Table");
                                    gamePanel.clearDeckCardButtons();
                                }
                                if (newGameUpdate.getDast() != null) {
                                    logger.debug("Updating Dast");
                                    gamePanel.clearDeckCardButtons();
                                    gamePanel.setDeckCardButtons(gameUpdate.getDast());
                                }
                                if (newGameUpdate.getOnTableCards() != null) {
                                    logger.debug("Updating table cards");
                                    gamePanel.setPlayedCardLabelsIcon(gameUpdate.getOnTableCards(), gameUpdate.getYourIndex() - gameUpdate.getCurrentPlayer());
                                }
                                if (newGameUpdate.getPlayerNames() != null) {
                                    logger.debug("Updating player names");
                                    gamePanel.setProfileNameLabelsText(gameUpdate.getPlayerNames(), gameUpdate.getYourIndex());
                                }
                                if (gameUpdate.getGameState() == GameState.HOKM && gameUpdate.getYourIndex() == gameUpdate.getCurrentRuler()) {
                                    CardsSuit[] buttons = CardsSuit.values();
                                    int returnValue = JOptionPane.showOptionDialog(null, "Your are the Ruler", "Select The Rule:",
                                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, buttons, null);
                                    topFrame.client.sendMessage(new HokmRequest(buttons[returnValue]));
                                }
                                if (newGameUpdate.getTeams() != null) {
                                    logger.debug("Updating team goals");
                                    Team[] teams = gameUpdate.getTeams();
                                    team1Rounds.setText(String.valueOf(teams[0].getRound()));
                                    team2Rounds.setText(String.valueOf(teams[1].getRound()));
                                    team1Sets.setText(String.valueOf(teams[0].getSet()));
                                    team2Sets.setText(String.valueOf(teams[1].getSet()));
                                }
                                if (newGameUpdate.getCurrentRuler() != null) {
                                    logger.debug("Updating profile pictures");
                                    gamePanel.setProfilePictureLabelsIcon(gameUpdate.getCurrentRuler() - gameUpdate.getYourIndex());
                                }
                                if (!isTeamNamesSet) {
                                    isTeamNamesSet = true;
                                    team1.setText((gameUpdate.getYourIndex() % 2 == 0) ? "Your team" : "Opponent Team");
                                    team2.setText((gameUpdate.getYourIndex() % 2 != 0) ? "Your team" : "Opponent Team");
                                }
                                if (newGameUpdate.getRule() != null) {
                                    logger.debug("Updating rule");
                                    hokmIconLabel.setIcon(new ImageIcon(Assets.getImageIcon(gameUpdate.getRule().toString()).getImage().getScaledInstance(hokmIconLabel.getWidth(), -1, Image.SCALE_SMOOTH)));
                                    break;
                                }
                                if (newGameUpdate.getCurrentPlayer() != null) {
                                    logger.debug("Updating current player");
                                    gamePanel.setCurrentPlayer((gameUpdate.getCurrentPlayer() - gameUpdate.getYourIndex() + 4) % 4);
                                }
                                if (gameUpdate.getGameState() == GameState.NEXT_ROUND) {
                                    sleep(1000);
                                }
                        }
                    } catch (RequestException e) {
                        if (e.getErrorMessage() == RequestErrorMessage.NOT_IN_GAME) {
                            JOptionPane.showMessageDialog(null, "The game has ended!", "Warning", JOptionPane.WARNING_MESSAGE);
                            topFrame.setMinimumSize(new Dimension(0, 0));
                            topFrame.remove(topPanel);
                            topFrame.add(new RoomPanel());
                            topFrame.repaint();
                            topFrame.revalidate();
                            topFrame.pack();
                            break;
                        } else {
                            JOptionPane.showMessageDialog(null, "Unknown error happend!\nexisting!", "Warning", JOptionPane.WARNING_MESSAGE);
                            topFrame.dispatchEvent(new WindowEvent(topFrame, WindowEvent.WINDOW_CLOSING));
                        }
                    } catch (NullPointerException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
