package hokm.messages;

public class LeaveRequest extends ClientRequest{
    private boolean forceLeaveGame;

    public LeaveRequest(boolean forceLeaveGame) {
        super(ClientRequestType.LEAVE);
        this.forceLeaveGame = forceLeaveGame;
    }

    public boolean isForceLeaveGame() {
        return forceLeaveGame;
    }
}
