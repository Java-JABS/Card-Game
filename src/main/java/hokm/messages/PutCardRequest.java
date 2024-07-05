package hokm.messages;

import hokm.Card;

public class PutCardRequest extends ClientRequest {
    private Card card;

    public PutCardRequest() {
        super(ClientRequestType.PUT_CARD);
    }

    public PutCardRequest(Card card) {
        this();
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}
