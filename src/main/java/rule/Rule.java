package rule;

import rule.client.GUI.MainFrame;
import rule.server.CLI;

public class Rule {
    public static void main(String[] args) {
        if (args.length == 2 && args[0].equals("--port"))
            new CLI(Integer.parseInt(args[1]));
        else new MainFrame();
    }
}
