package hokm.messages;

public class JoinRequest extends ClientRequest {
    private final String gameToken;

    public JoinRequest(String gameToken) {
        super(ClientRequestType.JOIN);
        this.gameToken = gameToken;
    }

    public String getGameToken() {
        return gameToken;
    }
}
