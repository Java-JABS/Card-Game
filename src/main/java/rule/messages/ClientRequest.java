package rule.messages;

public class ClientRequest {
    protected String token;
    protected ClientRequestType type;

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
}
