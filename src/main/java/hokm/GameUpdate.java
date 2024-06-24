package hokm;

import hokm.server.GameState;
import hokm.server.Player;
import hokm.server.Team;

import java.util.ArrayList;

public class GameUpdate {
    Dast dast;
    Dast onTableCards;
    GameState gameState;
    CardsSuit rule;
    Team[] teams = new Team[2];
    ArrayList<String> playerNames;
    int currentRuler;
    int currentPlayer;
    int yourIndex;
}
