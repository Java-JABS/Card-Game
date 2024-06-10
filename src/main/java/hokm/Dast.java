package hokm;

import java.util.ArrayList;
import java.util.Collections;

public class Dast extends ArrayList<Card> {
    boolean contains(CardsSuit suit) {
        for (Card card : this) {
            if (card.cardSuit == suit) return true;
        }
        return false;
    }

    Card pop(int index) {
        Card card = get(index);
        remove(index);
        return card;
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
