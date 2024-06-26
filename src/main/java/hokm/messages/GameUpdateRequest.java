package hokm.messages;

public class GameUpdateRequest extends ClientRequest {
    private boolean isMajorUpdate;
    public GameUpdateRequest() {
        super(ClientRequestType.GAME_UPDATE);
    }

    public boolean isMajorUpdate() {
        return isMajorUpdate;
    }

    public void setMajorUpdate(boolean majorUpdate) {
        isMajorUpdate = majorUpdate;
    }
}
