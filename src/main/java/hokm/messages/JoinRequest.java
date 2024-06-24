package hokm.messages;

public class JoinRequest extends ClientRequest {
    private String gameToken;

    public JoinRequest() {
        super(ClientRequestType.JOIN);
    }

    public String getGameToken() {
        return gameToken;
    }
}
