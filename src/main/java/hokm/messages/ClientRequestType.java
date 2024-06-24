package hokm.messages;

public enum ClientRequestType {
    REGISTER(RegisterRequest.class),
    LOGIN(LoginRequest.class),
    JOIN(JoinRequest.class),
    ROOM_CREATE(RoomCreateRequest.class),
    LEAVE(LeaveRequest.class),
    GAME_START(GameStartRequest.class),
    GAME_UPDATE(GameUpdateRequest.class),
    ROOM_UPDATE(RoomUpdateRequest.class),
    ;

    final public Class<? extends ClientRequest> className;

    <T extends ClientRequest> ClientRequestType(Class<T> className) {
        this.className = className;
    }
}
