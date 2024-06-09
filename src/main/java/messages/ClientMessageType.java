package messages;

public enum ClientMessageType {
    ;

    <T extends ClientMessage> ClientMessageType(Class<T> className) {
        this.className = className;
    }

    final public Class<? extends ClientMessage> className;
}
