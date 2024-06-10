package hokm;

import java.util.Collections;

public class Card {
    public final CardsSuit cardSuit;
    public final CardValues card;

    public Card(CardValues card, CardsSuit cardSuit) {
        this.card = card;
        this.cardSuit = cardSuit;
    }

    public static Dast getCards() {
        Dast result = new Dast();
        for (CardsSuit suit : CardsSuit.values()) {
            for (CardValues cardValues : CardValues.values()) {
                result.add(new Card(cardValues, suit));
            }
        }
        Collections.shuffle(result);
        return result;
    }
}

