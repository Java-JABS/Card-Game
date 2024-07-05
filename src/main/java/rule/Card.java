package rule;

public record Card(CardValues value, CardsSuit suit) implements Comparable<Card> {
    @Override
    public int compareTo(Card card) {
        return this.value.number - card.value.number;
    }
}

