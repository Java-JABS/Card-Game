package hokm.messages;

public class GameCreateRequest extends ClientRequest{

    public GameCreateRequest() {
        super(ClientRequestType.GAME_CREATE);
    }
}
