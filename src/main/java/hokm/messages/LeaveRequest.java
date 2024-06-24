package hokm.messages;

public class LeaveRequest extends ClientRequest {
    private final boolean forceLeaveGame;

    public LeaveRequest(boolean forceLeaveGame) {
        super(ClientRequestType.LEAVE);
        this.forceLeaveGame = forceLeaveGame;
    }

    public boolean isForceLeaveGame() {
        return forceLeaveGame;
    }
}
