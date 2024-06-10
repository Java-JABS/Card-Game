package hokm;

import java.util.ArrayList;
import java.util.Collections;

public class Dast extends ArrayList<Card> {
    public boolean contains(CardsSuit suit) {
        for (Card card : this) {
            if (card.suit == suit) return true;
        }
        return false;
    }

    public Card pop(int index) {
        Card card = get(index);
        remove(index);
        return card;
    }

    public Card pop(Card card) {
        int index = indexOf(card);
        return (index == -1) ? null : pop(index);
    }

    public Dast popFromStart(int size) {
        Dast result = new Dast();
        for (int i = 0; i < size; i++) {
            result.add(this.get(i));
        }
        this.removeRange(0, size);
        return result;
    }

    public Dast() {

    }

    public Dast(boolean shuffled) {
        for (CardsSuit suit : CardsSuit.values()) {
            for (CardValues cardValues : CardValues.values()) {
                add(new Card(cardValues, suit));
            }
        }
        if (shuffled) Collections.shuffle(this);
    }
}
