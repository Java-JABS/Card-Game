package hokm;

public class Card {
    public final CardsSuit cardSuit;
    public final CardValues card;

    public Card(CardValues card, CardsSuit cardSuit) {
        this.card = card;
        this.cardSuit = cardSuit;
    }
}

