package hokm;

import hokm.client.GUI.MainFrame;
import hokm.server.CLI;

public class Hokm {
    public static void main(String[] args) {
        if (args.length == 2 && args[0].equals("--port"))
            new CLI(Integer.parseInt(args[1]));
        else new MainFrame();
    }
}
