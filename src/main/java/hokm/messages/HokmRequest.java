package hokm.messages;

import hokm.CardsSuit;

public class HokmRequest extends ClientRequest {
    private CardsSuit hokm;

    public HokmRequest() {
        super(ClientRequestType.HOKM);
    }

    public HokmRequest(CardsSuit cardsSuit) {
        this();
        this.hokm = cardsSuit;
    }

    public CardsSuit getHokm() {
        return hokm;
    }

    public void setHokm(CardsSuit hokm) {
        this.hokm = hokm;
    }
}
