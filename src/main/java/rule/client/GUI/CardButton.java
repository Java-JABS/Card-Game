package rule.client.GUI;

import rule.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CardButton extends JButton {
    final Runnable mouseClick;
    private final JLabel iconLabel = new JLabel();
    private final int deltaY = 40;
    private Card card;
    private boolean isMouseEntered = false;

    public CardButton(Runnable mouseClick, Dimension cardPreferredSize) {
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
                isMouseEntered = true;
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                if (!isMouseEntered) return;
                Dimension size = getSize();
                size.height -= deltaY;
                setSize(size);
                Point point = getLocation();
                point.y += deltaY;
                setLocation(point);
                setVerticalTextPosition(SwingConstants.BOTTOM);
                setHorizontalTextPosition(SwingConstants.CENTER);
                isMouseEntered = false;
            }
        });
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        iconLabel.setIcon(new ImageIcon(Assets.getCardImageIcon(card).getImage().getScaledInstance(80, -1, Image.SCALE_SMOOTH)));
        this.card = card;
    }

    public void setMouseEntered(boolean mouseEntered) {
        isMouseEntered = mouseEntered;
    }
}
