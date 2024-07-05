package rule.messages;

public class GameStartRequest extends ClientRequest {
    public GameStartRequest() {
        super(ClientRequestType.GAME_START);
    }
}
