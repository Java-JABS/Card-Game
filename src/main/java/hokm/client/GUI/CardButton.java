package hokm.client.GUI;

import hokm.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CardButton extends JButton {
    private Card card;
    private JLabel iconLabel = new JLabel();
    private final int deltaY = 40;
    final Runnable mouseClick;

    public CardButton(Runnable mouseClick,Dimension cardPreferredSize) {
        this.mouseClick = mouseClick;
        setLayout(new GridBagLayout());
        add(iconLabel);
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setPreferredSize(cardPreferredSize);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                mouseClick.run();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                Dimension size = getSize();
                size.height += deltaY;
                setSize(size);
                Point point = getLocation();
                point.y -= deltaY;
                setLocation(point);
                setVerticalTextPosition(SwingConstants.BOTTOM);
                setHorizontalTextPosition(SwingConstants.CENTER);
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                Dimension size = getSize();
                size.height -= deltaY;
                setSize(size);
                Point point = getLocation();
                point.y += deltaY;
                setLocation(point);
                setVerticalTextPosition(SwingConstants.BOTTOM);
                setHorizontalTextPosition(SwingConstants.CENTER);
            }
        });
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        iconLabel.setIcon(new ImageIcon(new ImageIcon(this.getClass().getClassLoader().getResource("pictures/cards/" + card.suit() + "/" + card.value() + ".png")).getImage().getScaledInstance(80, -1, Image.SCALE_SMOOTH)));
        this.card = card;
    }
}
