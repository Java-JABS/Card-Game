package hokm.messages;

public class JoinRequest extends ClientRequest {
    private String gameToken;

    public JoinRequest(String gameToken) {
        super(ClientRequestType.JOIN);
        this.gameToken=gameToken;
    }

    public String getGameToken() {
        return gameToken;
    }
}
