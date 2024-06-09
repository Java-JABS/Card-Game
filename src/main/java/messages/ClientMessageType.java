package messages;

public enum ClientMessageType {
    ;
    <T extends ClientMessage> ClientMessageType(Class<T> className) {
        this.className = (Class<ClientMessage>) className;
    }

    final public Class<ClientMessage> className;
}
