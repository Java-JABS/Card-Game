package hokm.messages;

public class RegisterRequest extends ClientRequest{
    String username;

    public RegisterRequest() {
        super(ClientRequestType.REGISTER);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {

        return username;
    }

}
