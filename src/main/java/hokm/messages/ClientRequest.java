package hokm.messages;

public class ClientRequest {
    protected String token;
    protected ClientRequestType type;
    protected ClientState state;

    public ClientRequest(ClientRequestType type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ClientRequestType getType() {
        return type;
    }

    public ClientState getState() {
        return state;
    }

    public void setState(ClientState state) {
        this.state = state;
    }
}
