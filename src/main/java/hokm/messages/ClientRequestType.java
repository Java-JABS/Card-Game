package hokm.messages;

public enum ClientRequestType {
    ;

    <T extends ClientRequest> ClientRequestType(Class<T> className) {
        this.className = className;
    }

    final public Class<? extends ClientRequest> className;
}
