package hokm.client.GUI;

import hokm.CardsSuit;
import hokm.GameUpdate;
import hokm.client.ClientRequestSender;
import hokm.messages.GameUpdateRequest;
import hokm.messages.HokmRequest;
import hokm.server.GameState;
import hokm.server.RequestException;
import hokm.server.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class OuterGamePanel extends JPanel {

    JLabel team1Sets = new JLabel("0", SwingConstants.CENTER);
    JLabel team1Rounds = new JLabel("0", SwingConstants.CENTER);
    JLabel team2Sets = new JLabel("0", SwingConstants.CENTER);
    JLabel team2Rounds = new JLabel("0", SwingConstants.CENTER);
    GamePanel gamePanel = new GamePanel();
    GameUpdate gameUpdate = new GameUpdate();
    private final Logger logger = LoggerFactory.getLogger(OuterGamePanel.class);

    OuterGamePanel() {
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

        upperPanel.add(team, teamGrid);
        upperPanel.add(sets, setsGrid);
        upperPanel.add(round, roundGrid);
        upperPanel.add(team1, team1Grid);
        upperPanel.add(team2, team2Grid);
        upperPanel.add(this.team1Sets, team1SetsGrid);
        upperPanel.add(this.team1Rounds, team1RoundsGrid);
        upperPanel.add(this.team2Sets, team2SetsGrid);
        upperPanel.add(this.team2Rounds, team2RoundsGrid);
        this.add(upperPanel, BorderLayout.NORTH);
        new Thread(() -> {
            boolean isTeamNamesSet = false;
            while (true) {
                try {
                    sleep(1000);
                    MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
                    try {
                        logger.debug("Request for Game Update :-)");
                        String mess = topFrame.client.sendMessage(new GameUpdateRequest(true));
                        GameUpdate newGameUpdate = ClientRequestSender.gsonAgent.fromJson(mess, GameUpdate.class);
                        switch (newGameUpdate.getNumber() - gameUpdate.getNumber()) {
                            case 0:
                                break;
                            default:
                                logger.warn("Update is behind so get a major update!");
                                newGameUpdate = ClientRequestSender.gsonAgent.fromJson(topFrame.client.sendMessage(new GameUpdateRequest(true)), GameUpdate.class);
                            case 1:
                                newGameUpdate.updatesFrom(gameUpdate);
                                gameUpdate.update(newGameUpdate);
                                if (newGameUpdate.getDast() != null) {
                                    gamePanel.setDeckCardButtons(gameUpdate.getDast());
                                }
                                if (newGameUpdate.getOnTableCards() != null) {
                                    gamePanel.setPlayedCardLabelsIcon(gameUpdate.getOnTableCards(), gameUpdate.getYourIndex() - gameUpdate.getCurrentPlayer());
                                }
                                if (newGameUpdate.getPlayerNames() != null) {
                                    gamePanel.setProfileNameLabelsText(gameUpdate.getPlayerNames(), gameUpdate.getYourIndex());
                                }
                                if (gameUpdate.getGameState() == GameState.HOKM && gameUpdate.getYourIndex() == gameUpdate.getCurrentRuler()) {
                                    CardsSuit[] buttons = CardsSuit.values();
                                    int returnValue = JOptionPane.showOptionDialog(null, "Your are the Ruler", "Select The Rule:",
                                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, buttons, null);
                                    topFrame.client.sendMessage(new HokmRequest(buttons[returnValue]));
                                }
                                if (newGameUpdate.getTeams() != null) {
                                    Team[] teams = gameUpdate.getTeams();
                                    team1Rounds.setText(String.valueOf(teams[0].getRound()));
                                    team2Rounds.setText(String.valueOf(teams[1].getRound()));
                                    team1Sets.setText(String.valueOf(teams[0].getSet()));
                                    team2Sets.setText(String.valueOf(teams[1].getSet()));
                                }
                                if (newGameUpdate.getCurrentRuler() != null) {
                                    gamePanel.setProfilePictureLabelsIcon(gameUpdate.getCurrentRuler() - gameUpdate.getYourIndex());
                                }
                                if (!isTeamNamesSet){
                                    isTeamNamesSet=true;
                                    team1.setText((gameUpdate.getYourIndex()%2==0)?"Your team":"Opponent Team");
                                    team2.setText((gameUpdate.getYourIndex()%2!=0)?"Your team":"Opponent Team");
                                }
                                if(newGameUpdate.getRule()!=null){
                                    team.setText("Hokm : " + gameUpdate.getRule().toString());
                                }
                                if(newGameUpdate.getCurrentPlayer()!=null){
                                    gamePanel.setCurrentPlayer(gameUpdate.getCurrentPlayer()-gameUpdate.getYourIndex());
                                }
                        }
                    } catch (RequestException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
                        topFrame.remove(OuterGamePanel.this);
                        topFrame.add(new MainMenuPanel());
                        topFrame.repaint();
                        topFrame.revalidate();
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
