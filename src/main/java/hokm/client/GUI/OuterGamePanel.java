package hokm.client.GUI;

import hokm.CardsSuit;
import hokm.GameUpdate;
import hokm.client.ClientRequestSender;
import hokm.messages.GameUpdateRequest;
import hokm.messages.HokmRequest;
import hokm.server.GameState;
import hokm.server.RequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class OuterGamePanel extends JPanel {

    JLabel team1Sets = new JLabel("0",SwingConstants.CENTER);
    JLabel team1Rounds = new JLabel("0",SwingConstants.CENTER);
    JLabel team2Sets = new JLabel("0",SwingConstants.CENTER);
    JLabel team2Rounds = new JLabel("0",SwingConstants.CENTER);
    GamePanel gamePanel = new GamePanel();
    GameUpdate gameUpdate = new GameUpdate();
    private Logger logger = LoggerFactory.getLogger(OuterGamePanel.class);

    OuterGamePanel(){
        setLayout(new BorderLayout());
        add(gamePanel,BorderLayout.CENTER);

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new GridBagLayout());

        JLabel team = new JLabel("Team",SwingConstants.CENTER);
        team.setPreferredSize(new Dimension(50,25));
        team.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints teamGrid = new GridBagConstraints();
        teamGrid.gridx = 0;
        teamGrid.gridy = 0;
        teamGrid.insets = new Insets(1,1,1,1);

        JLabel sets = new JLabel("Set",SwingConstants.CENTER);
        sets.setPreferredSize(new Dimension(50,25));
        sets.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints setsGrid = new GridBagConstraints();
        setsGrid.gridx = 1;
        setsGrid.gridy = 0;
        setsGrid.insets = new Insets(1,1,1,1);

        JLabel round = new JLabel("Round",SwingConstants.CENTER);
        round.setPreferredSize(new Dimension(50,25));
        round.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints roundGrid = new GridBagConstraints();
        roundGrid.gridx = 2;
        roundGrid.gridy = 0;
        roundGrid.insets = new Insets(1,1,1,1);


        JLabel team1 = new JLabel("Team1",SwingConstants.CENTER);
        team1.setPreferredSize(new Dimension(50,25));
        team1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints team1Grid = new GridBagConstraints();
        team1Grid.gridx = 0;
        team1Grid.gridy = 1;
        team1Grid.insets = new Insets(1,1,1,1);

        JLabel team2 = new JLabel("Team2",SwingConstants.CENTER);
        team2.setPreferredSize(new Dimension(50,25));
        team2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints team2Grid = new GridBagConstraints();
        team2Grid.gridx = 0;
        team2Grid.gridy = 2;
        team2Grid.insets = new Insets(1,1,1,1);

        this.team1Sets.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.team1Sets.setPreferredSize(new Dimension(50,25));
        GridBagConstraints team1SetsGrid = new GridBagConstraints();
        team1SetsGrid.gridx = 1;
        team1SetsGrid.gridy = 1;
        team1SetsGrid.insets = new Insets(1,1,1,1);

        this.team1Rounds.setPreferredSize(new Dimension(50,25));
        this.team1Rounds.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints team1RoundsGrid = new GridBagConstraints();
        team1RoundsGrid.gridx = 2;
        team1RoundsGrid.gridy = 1;
        team1RoundsGrid.insets = new Insets(1,1,1,1);

        this.team2Sets.setPreferredSize(new Dimension(50,25));
        this.team2Sets.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints team2SetsGrid = new GridBagConstraints();
        team2SetsGrid.gridx = 1;
        team2SetsGrid.gridy = 2;
        team2SetsGrid.insets = new Insets(1,1,1,1);

        this.team2Rounds.setPreferredSize(new Dimension(50,25));
        this.team2Rounds.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints team2RoundsGrid = new GridBagConstraints();
        team2RoundsGrid.gridx = 2;
        team2RoundsGrid.gridy = 2;
        team2RoundsGrid.insets = new Insets(1,1,1,1);

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

        new Thread(()->{
            while (true){
                MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
                try {
                    logger.info("Request for Game Update :-)");
                    String mess = topFrame.client.sendMessage(new GameUpdateRequest(true));
                    GameUpdate newGameUpdate = ClientRequestSender.gsonAgent.fromJson(mess, GameUpdate.class);
                    switch (newGameUpdate.getNumber() - gameUpdate.getNumber()){
                        case 0:
                            break;
                        default:
                            newGameUpdate = ClientRequestSender.gsonAgent.fromJson(topFrame.client.sendMessage(new GameUpdateRequest(true)), GameUpdate.class);
                        case 1:
                            if(newGameUpdate.getDast()!=null){
                                gamePanel.setDeckCardButtons(newGameUpdate.getDast());
                            }
                            if(newGameUpdate.getOnTableCards()!=null){
                                System.out.println(newGameUpdate.getOnTableCards().toString()   );
                                gamePanel.setPlayedCardLabelsIcon(newGameUpdate.getOnTableCards(),newGameUpdate.getYourIndex()-newGameUpdate.getCurrentPlayer());
                            }
                            if(newGameUpdate.getPlayerNames()!=null){
                                gamePanel.setProfileNameLabelsText(newGameUpdate.getPlayerNames(),newGameUpdate.getYourIndex());
                            }
                            if(newGameUpdate.getGameState()== GameState.HOKM&&newGameUpdate.getYourIndex()== newGameUpdate.getCurrentRuler()){
                                CardsSuit[] buttons = CardsSuit.values();
                                int returnValue = JOptionPane.showOptionDialog(null, "Narrative", "Narrative",
                                        JOptionPane.WARNING_MESSAGE, 0, null, buttons, null);
                                topFrame.client.sendMessage(new HokmRequest(buttons[returnValue]));
                            }
                    }

                    gameUpdate=newGameUpdate;
                    sleep(1000);
                } catch (InterruptedException e) {
                    logger.warn("Failed to request, Reason: {}", e.getMessage());
                    throw new RuntimeException(e);
                }catch (RequestException e) {
                    logger.warn("Failed to request, Reason: {}", e.getMessage());
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
                    topFrame.remove(OuterGamePanel.this);
                    topFrame.add(new MainMenuPanel());
                    topFrame.repaint();
                    topFrame.revalidate();
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
