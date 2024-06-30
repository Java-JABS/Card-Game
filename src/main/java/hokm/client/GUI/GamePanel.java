package hokm.client.GUI;

import hokm.Card;
import hokm.messages.PutCardRequest;
import hokm.server.RequestException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    private final JPanel rightPlayerPanel = new JPanel();
    private final JPanel leftPlayerPanel = new JPanel();
    private final JPanel upPlayerPanel = new JPanel();

    private final JPanel downPanel = new JPanel();
    private final JPanel centerPanel = new JPanel();

    private final JPanel myProfilePanel = new JPanel();
    private final JPanel cardDeckPanel = new JPanel(new GridBagLayout());

    private final JLabel[] profilePictureLabels = {new JLabel(), new JLabel(), new JLabel(), new JLabel()};
    private final JLabel[] profileNameLabels = {new JLabel(), new JLabel(), new JLabel(), new JLabel()};
    private final JLabel[] playedCardLabels = {new JLabel(), new JLabel(), new JLabel(), new JLabel()};
    private final ArrayList<CardButton> cardButtons = new ArrayList<>(13);

    GamePanel() {
        initComponents();
    }

    private void initComponents() {

        this.setLayout(new BorderLayout());
        this.rightPlayerPanel.setOpaque(false);
        this.leftPlayerPanel.setOpaque(false);
        this.myProfilePanel.setOpaque(false);
        this.upPlayerPanel.setOpaque(false);
        this.downPanel.setOpaque(false);
        this.centerPanel.setOpaque(false);
        this.cardDeckPanel.setOpaque(false);
        this.setOpaque(false);
        for (int i = 0; i < this.profileNameLabels.length; i++) {
            this.profileNameLabels[i].setBackground(Color.RED);
            this.profileNameLabels[i].setForeground(Color.WHITE);
        }


        Dimension cardsDimension = new Dimension(80, 120);
        Dimension profilePicturesDimension = new Dimension(80, 80);

        GridBagConstraints gridBagConstraints;

        // setting right player panel :
        rightPlayerPanel.setLayout(new GridBagLayout());
        profileNameLabels[1].setText("Name");
        profileNameLabels[1].setOpaque(true);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        rightPlayerPanel.add(profileNameLabels[1], gridBagConstraints);
        profilePictureLabels[1].setPreferredSize(profilePicturesDimension);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        rightPlayerPanel.add(profilePictureLabels[1], gridBagConstraints);
        playedCardLabels[1].setPreferredSize(cardsDimension);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 0, 0, 10);
        rightPlayerPanel.add(playedCardLabels[1], gridBagConstraints);
        add(rightPlayerPanel, BorderLayout.EAST);

        // setting left player panel :
        leftPlayerPanel.setLayout(new GridBagLayout());
        profileNameLabels[3].setText("Name");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        leftPlayerPanel.add(profileNameLabels[3], gridBagConstraints);
        playedCardLabels[3].setPreferredSize(cardsDimension);
        playedCardLabels[3].setAlignmentY(1.5F);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 10, 0, 0);
        leftPlayerPanel.add(playedCardLabels[3], gridBagConstraints);
        profilePictureLabels[3].setPreferredSize(profilePicturesDimension);
        profilePictureLabels[3].setAlignmentY(1.5F);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        leftPlayerPanel.add(profilePictureLabels[3], gridBagConstraints);
        add(leftPlayerPanel, BorderLayout.WEST);

        // setting up player panel :
        upPlayerPanel.setLayout(new GridBagLayout());
        profilePictureLabels[2].setPreferredSize(profilePicturesDimension);
        upPlayerPanel.add(profilePictureLabels[2], new GridBagConstraints());
        profileNameLabels[2].setText("Name");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        upPlayerPanel.add(profileNameLabels[2], gridBagConstraints);
        playedCardLabels[2].setPreferredSize(cardsDimension);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new Insets(0, 0, 10, 0);
        upPlayerPanel.add(playedCardLabels[2], gridBagConstraints);
        add(upPlayerPanel, BorderLayout.PAGE_START);

        // setting down panel :
        downPanel.setLayout(new GridBagLayout());
        myProfilePanel.setLayout(new GridBagLayout());
        playedCardLabels[0].setPreferredSize(cardsDimension);
        myProfilePanel.add(playedCardLabels[0], new GridBagConstraints());
        profileNameLabels[0].setText("Name");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        myProfilePanel.add(profileNameLabels[0], gridBagConstraints);
        profilePictureLabels[0].setPreferredSize(profilePicturesDimension);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        myProfilePanel.add(profilePictureLabels[0], gridBagConstraints);
        downPanel.add(myProfilePanel, new GridBagConstraints());
        cardDeckPanel.setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        cardDeckPanel.setPreferredSize(new Dimension(800, 200));
        downPanel.add(cardDeckPanel, gridBagConstraints);
        add(downPanel, BorderLayout.PAGE_END);

        // setting center panel :
        centerPanel.setLayout(new BorderLayout());
        add(centerPanel, BorderLayout.CENTER);

        for (int i = 0; i < this.profileNameLabels.length; i++) {
            this.profileNameLabels[i].setOpaque(false);
        }

        for (int i = 0; i < 13; i++) {
            int finalI = i;
            cardButtons.add(new CardButton(() -> {
                MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
                try {
                    topFrame.client.sendMessage(new PutCardRequest(cardButtons.get(finalI).getCard()));
                } catch (RequestException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }, cardsDimension));
        }
    }

    public ImageIcon getCardIcon(Card card) {
        return new ImageIcon(Assets.getCardImageIcon(card).getImage().getScaledInstance(80, -1, Image.SCALE_SMOOTH));
    }

    public void setPlayedCardLabelsIcon(ArrayList<Card> list, int index) {
        for (JLabel label : playedCardLabels) {
            label.setIcon(null);
        }
        for (int i = 0; i < list.size(); i++) {
            playedCardLabels[(i - list.size() - index + 8) % 4].setIcon(getCardIcon(list.get(i)));
        }
    }

    public void setDeckCardButtons(ArrayList<Card> list) {
        cardDeckPanel.removeAll();
        for (CardButton cardButton: cardButtons)
            cardButton.setMouseEntered(false);
        for (int i = 0; i < list.size(); i++) {
            CardButton cardButton = cardButtons.get(i);
            cardButton.setCard(list.get(i));
            add(cardButton);
            GridBagConstraints bGrid = new GridBagConstraints();
            bGrid.gridx = i;
            bGrid.insets = new Insets(0, -15, 0, -15);
            cardDeckPanel.add(cardButton, bGrid);
        }
        repaint();
        revalidate();
    }

    public void setProfileNameLabelsText(ArrayList<String> list, int index) {
        for (int i = 0; i < list.size(); i++) {
            profileNameLabels[(i - index + 4) % 4].setText(list.get(i));
        }
    }

    public void setProfilePictureLabelsIcon(int rulerIndex) {
        rulerIndex = (rulerIndex + 4) % 4;
        for (int i = 0; i < this.profilePictureLabels.length; i++) {
            this.profilePictureLabels[i].setIcon(new ImageIcon(Assets.getImageIcon((i == rulerIndex) ? "rulerIcon.png" : "personIcon.png").getImage().getScaledInstance(80, -1, Image.SCALE_SMOOTH)));
        }
    }

    public void setCurrentPlayer(int index){
        for (int i = 0; i < profileNameLabels.length; i++)
            profileNameLabels[i].setOpaque(i==index);
        repaint();
        revalidate();
    }
}