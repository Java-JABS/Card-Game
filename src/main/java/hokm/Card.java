package hokm;



import java.util.ArrayList;

public class Card  {
    public final CardsSuit cardSuit;
    public final CardValues card;

    public Card(CardValues card, CardsSuit cardSuit) {
        this.card = card;
        this.cardSuit = cardSuit;
    }
    static ArrayList<Card> getCards(){
        ArrayList<Card> result = new ArrayList<>();
        for (CardsSuit suit : CardsSuit.values()){
            for (CardValues cardValues : CardValues.values()){
                result.add(new Card(cardValues, suit));
            }
        }
        return result;
    }
}

