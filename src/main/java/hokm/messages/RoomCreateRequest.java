package hokm.messages;

public class RoomCreateRequest extends ClientRequest{

    public RoomCreateRequest() {
        super(ClientRequestType.ROOM_CREATE);
    }
}
