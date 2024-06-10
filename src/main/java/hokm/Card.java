package hokm;

public class Card {
    public final CardsSuit suit;
    public final CardValues value;

    public Card(CardValues value, CardsSuit suit) {
        this.value = value;
        this.suit = suit;
    }
}

