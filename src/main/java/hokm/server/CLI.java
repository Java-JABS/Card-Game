package hokm.server;

public class CLI {
    Server server;

    public CLI(int port) {
            server = Server.runInstance(port);
    }
}
