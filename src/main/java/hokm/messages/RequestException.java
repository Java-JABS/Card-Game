package hokm.messages;

public class RequestException extends Exception {
    RequestErrorMessage errorMessage;

    public RequestException(RequestErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public RequestErrorMessage getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public String getMessage() {
        return this.errorMessage.message;
    }
}
