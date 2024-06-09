package hokm.messages;

public enum ClientRequestType {
    REGISTER(RegisterRequest.class),
    LOGIN(LoginRequest.class),
    JOIN(JoinRequest.class),
    GAME_CREATE(GameCreateRequest.class),
    ;

    <T extends ClientRequest> ClientRequestType(Class<T> className) {
        this.className = className;
    }

    final public Class<? extends ClientRequest> className;
}
