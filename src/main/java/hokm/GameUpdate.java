package hokm;

import hokm.server.GameState;
import hokm.server.Team;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GameUpdate implements Cloneable {
    Integer number = 0;
    Dast dast;
    Dast onTableCards;
    GameState gameState;
    CardsSuit rule;
    Team[] teams;
    ArrayList<String> playerNames;
    Integer currentRuler;
    Integer currentPlayer;
    Integer yourIndex;

    public GameUpdate() {
    }

    public GameUpdate(GameUpdate update) {
        this.number = update.number + 1;
    }

    public GameUpdate clone() {
        try {
            return (GameUpdate) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updatesFrom(GameUpdate pastUpdate) throws IllegalAccessException {
        if (pastUpdate == null)
            return;
        for (Field field : GameUpdate.class.getDeclaredFields()) {
            Object pastObject = field.get(pastUpdate);
            if (pastObject != null) {
                Object object = field.get(this);
                if (object != null && object.equals(pastObject))
                    field.set(this, null);
            }
        }
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

    public Integer getCurrentRuler() {
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

    public int getNumber() {
        return number;
    }

}
