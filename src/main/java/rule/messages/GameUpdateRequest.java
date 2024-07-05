package rule.messages;

public class GameUpdateRequest extends ClientRequest {
    private boolean isMajorUpdate = false;

    public GameUpdateRequest() {
        super(ClientRequestType.GAME_UPDATE);
    }

    public GameUpdateRequest(boolean isMajorUpdate) {
        this();
        this.isMajorUpdate = isMajorUpdate;
    }

    public boolean isMajorUpdate() {
        return isMajorUpdate;
    }
}
