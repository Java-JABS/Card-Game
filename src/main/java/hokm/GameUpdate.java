package hokm;

import hokm.server.GameState;
import hokm.server.Player;
import hokm.server.Team;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GameUpdate {
    int number = 0;
    Dast dast;
    Dast onTableCards;
    GameState gameState;
    CardsSuit rule;
    Team[] teams;
    ArrayList<String> playerNames;
    int currentRuler;
    int currentPlayer;
    int yourIndex;

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        number++;
        this.gameState = gameState;
    }

    public CardsSuit getRule() {
        return rule;
    }

    public void setRule(CardsSuit rule) {
        number++;
        this.rule = rule;
    }

    public int getCurrentRuler() {
        return currentRuler;
    }

    public void setCurrentRuler(int currentRuler) {
        number++;
        this.currentRuler = currentRuler;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        number++;
        this.currentPlayer = currentPlayer;
    }

    public int getYourIndex() {
        return yourIndex;
    }

    public void setYourIndex(int yourIndex) {
        number++;
        this.yourIndex = yourIndex;
    }

    void update(GameUpdate update) {
        for (Field field : GameUpdate.class.getDeclaredFields()) {
            try {
                Object object = field.get(update);
                if (object != null) {
                    field.set(this, object);
                }
            } catch (IllegalAccessException e) {
                System.out.println("Couldn't Update GameUpdate");
            }
        }
    }
}
