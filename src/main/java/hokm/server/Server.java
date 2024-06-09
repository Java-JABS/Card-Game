package hokm.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Server {
    ArrayList<Client> clients = new ArrayList<>();
    HashMap<String, Game> games = new HashMap<>();

    void createGame(Client client) {
        Game newGame = new Game(tokenGenerator());
        games.put(newGame.token, newGame);
        newGame.joinClient(client);
    }

    void joinGame(Client client, String token) {
        Game game;
        if ((game = games.get(token)) != null) {
            game.joinClient(client);
        }
    }

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
