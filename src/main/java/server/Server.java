package server;

import java.util.Random;

public class Server {
    public static String tokenGenerator() {
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        char[] newToken = new char[6];
        for (int i = 0; i < 6; i++) {
            token.append("a").append(random.nextInt(26));
        }
        return token.toString();
    }
}
