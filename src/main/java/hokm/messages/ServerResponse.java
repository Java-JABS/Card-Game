package hokm.messages;

public class ServerResponse {
    private final boolean success;
    private final String additionalInfo;

    public ServerResponse(boolean success, String info) {
        this.success = success;
        this.additionalInfo = info;
    }

    public boolean wasNotSuccessful() {
        return !this.success;
    }

    public String getAdditionalInfo() {
        return this.additionalInfo;
    }
}
