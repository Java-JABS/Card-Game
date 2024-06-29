package hokm.server;
public class CLI {
    static Server server;
    public static void main(String[] args) {
        if(args.length < 2)
            throw new RuntimeException("please enter port");
        if(args[0].equals("--port"))
            server = Server.runInstance(Integer.parseInt(args[1]));
    }
}
