package rule.messages;

public class LoginRequest extends ClientRequest {
    public LoginRequest() {
        super(ClientRequestType.LOGIN);
    }
}
