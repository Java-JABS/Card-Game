package rule.messages;

public class RoomUpdateRequest extends ClientRequest {
    public RoomUpdateRequest() {
        super(ClientRequestType.ROOM_UPDATE);
    }
}
