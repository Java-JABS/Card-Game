package hokm.messages;

public class ClientRequest {
    protected String token;
    protected ClientRequestType type;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ClientRequestType getType() {
        return type;
    }
}
