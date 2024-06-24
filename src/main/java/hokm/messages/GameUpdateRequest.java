package hokm.messages;

public class GameUpdateRequest extends ClientRequest {
    public GameUpdateRequest() {
        super(ClientRequestType.GAME_UPDATE);
    }
}
