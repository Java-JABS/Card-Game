package rule.messages;

import rule.Card;

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
