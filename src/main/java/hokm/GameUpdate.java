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

    public GameUpdate() {
    }

    public GameUpdate(GameUpdate update) {
        this.number=update.number++;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public CardsSuit getRule() {
        return rule;
    }

    public void setRule(CardsSuit rule) {
        this.rule = rule;
    }

    public int getCurrentRuler() {
        return currentRuler;
    }

    public void setCurrentRuler(int currentRuler) {
        this.currentRuler = currentRuler;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getYourIndex() {
        return yourIndex;
    }

    public void setYourIndex(int yourIndex) {
        this.yourIndex = yourIndex;
    }

    public Dast getDast() {
        return dast;
    }

    public void setDast(Dast dast) {
        this.dast = dast;
    }

    public Dast getOnTableCards() {
        return onTableCards;
    }

    public void setOnTableCards(Dast onTableCards) {
        this.onTableCards = onTableCards;
    }

    public Team[] getTeams() {
        return teams;
    }

    public void setTeams(Team[] teams) {
        this.teams = teams;
    }

    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(ArrayList<String> playerNames) {
        this.playerNames = playerNames;
    }

    public void update(GameUpdate update) {
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
