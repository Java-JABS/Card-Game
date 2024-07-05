package hokm.messages;

public class RegisterRequest extends ClientRequest {
    String username;

    public RegisterRequest() {
        super(ClientRequestType.REGISTER);
    }

    public RegisterRequest(String username) {
        this();
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
