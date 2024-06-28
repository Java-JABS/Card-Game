package hokm.client.GUI;

import hokm.Card;
import hokm.CardValues;
import hokm.CardsSuit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    private final JPanel rightPlayerPanel = new JPanel();
    private final JPanel leftPlayerPanel = new JPanel();
    private final JPanel upPlayerPanel = new JPanel();

    private final JPanel downPanel = new JPanel();
    private final JPanel centerPanel = new JPanel();

    private final JPanel myProfilePanel = new JPanel();
    private final JPanel cardDeckPanel = new JPanel();

    private final JLabel[] profilePictureLabels = {new JLabel(), new JLabel(), new JLabel(), new JLabel()};
    private final JLabel[] profileNameLabels = {new JLabel(), new JLabel(), new JLabel(), new JLabel()};
    private final JLabel[] playedCardLabels = {new JLabel(), new JLabel(), new JLabel(), new JLabel()};

    JButton[] cardDeckButtons = new JButton[13];

    public GamePanel() {
        setLayout(new BorderLayout());
        initComponents();
    }

    public ImageIcon getCardIcon(Card card) {
        return new ImageIcon(new ImageIcon("pictures/cards/" + card.suit() + "/" + card.value() + ".png").getImage().getScaledInstance(80, -1, Image.SCALE_SMOOTH));
    }

    public void setPlayedCardLabelsIcon(ArrayList<Card> list, int index) {
        for (int i = 0; i < list.size(); i++) {
            playedCardLabels[(i + (list.size() - index)) % 4].setIcon(getCardIcon(list.get(i)));
        }
    }

    public void setDeckCardButtons(ArrayList<Card> list, int index) {

    }

    public void setProfileNameLabelsText(ArrayList<String> list, int index) {
        for (int i = 0; i < list.size(); i++) {
            profileNameLabels[(i + (list.size() - index)) % 4].setText(list.get(i));
        }
    }

    public void setProfilePictureLabelsIcon() {
        for (JLabel label : profilePictureLabels) {
            label.setIcon(new ImageIcon(new ImageIcon("pictures/Person.png").getImage().getScaledInstance(80, -1, Image.SCALE_SMOOTH)));
        }
    }

    private void initComponents() {

        Dimension cardsDimension = new Dimension(80, 120);
        Dimension profilePicturesDimension = new Dimension(80, 80);

        GridBagConstraints gridBagConstraints;

        // setting right player panel :
        rightPlayerPanel.setLayout(new GridBagLayout());
        profileNameLabels[1].setText("Name");
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
        gridBagConstraints.insets = new Insets(0,0,10,0);
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
        gridBagConstraints.insets = new Insets(0,0,0,0);
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
        cardDeckPanel.setPreferredSize(new Dimension(800, 170));
        downPanel.add(cardDeckPanel, gridBagConstraints);

        add(downPanel, BorderLayout.PAGE_END);

        centerPanel.setLayout(new BorderLayout());
        add(centerPanel, BorderLayout.CENTER);
        setProfilePictureLabelsIcon();
        ArrayList<Card> a = new ArrayList<>();
        a.add(new Card(CardValues.FIVE, CardsSuit.CLUBS));
        a.add(new Card(CardValues.FIVE, CardsSuit.CLUBS));
        a.add(new Card(CardValues.FIVE, CardsSuit.CLUBS));
        a.add(new Card(CardValues.FIVE, CardsSuit.CLUBS));
        setPlayedCardLabelsIcon(a, 0);

        for (int i = 0; i < 13; i++) {
            cardDeckButtons[i] = new JButton();
            cardDeckButtons[i].setLayout(new GridBagLayout());
            JLabel iconLabel = new JLabel(new ImageIcon(new ImageIcon("pictures/cards/DIAMONDS/KING.png").getImage().getScaledInstance(80, -1, Image.SCALE_SMOOTH)));
            cardDeckButtons[i].add(iconLabel);
            cardDeckButtons[i].setVerticalTextPosition(SwingConstants.BOTTOM);
            cardDeckButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
            cardDeckButtons[i].setPreferredSize(cardsDimension);
            GridBagConstraints bGrid = new GridBagConstraints();
            bGrid.gridx = i + 1;
            bGrid.gridy = 5;
            bGrid.insets = new Insets(0, -10, 0, -10);
            cardDeckPanel.add(cardDeckButtons[i], bGrid);
            int finalI = i;
            cardDeckButtons[i].setOpaque(false);
            cardDeckButtons[i].setContentAreaFilled(false);
            cardDeckButtons[i].setBorderPainted(false);
            int finalI1 = i;
            cardDeckButtons[i].addMouseListener(new MouseListener() {
                private int y = 40;
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {}
                @Override
                public void mousePressed(MouseEvent mouseEvent) {}
                @Override
                public void mouseReleased(MouseEvent mouseEvent) {}
                @Override
                public void mouseEntered(MouseEvent mouseEvent) {
                    Dimension size = cardDeckButtons[finalI].getSize();
                    size.height += y;
                    cardDeckButtons[finalI].setSize(size);
                    Point point = cardDeckButtons[finalI].getLocation();
                    point.y -= y;
                    cardDeckButtons[finalI].setLocation(point);
                    cardDeckButtons[finalI1].setVerticalTextPosition(SwingConstants.BOTTOM);
                    cardDeckButtons[finalI1].setHorizontalTextPosition(SwingConstants.CENTER);
                }
                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    Dimension size = cardDeckButtons[finalI].getSize();
                    size.height -= y;
                    cardDeckButtons[finalI].setSize(size);
                    Point point = cardDeckButtons[finalI].getLocation();
                    point.y += y;
                    cardDeckButtons[finalI].setLocation(point);
                    cardDeckButtons[finalI1].setVerticalTextPosition(SwingConstants.BOTTOM);
                    cardDeckButtons[finalI1].setHorizontalTextPosition(SwingConstants.CENTER);
                }
            });
        }
    }
}