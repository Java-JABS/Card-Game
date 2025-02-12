package rule;

import java.util.ArrayList;

public class RoomUpdate implements Cloneable {
    ArrayList<String> playerNames;
    String token;

    Boolean isGameStarted = false;

    Integer number = 0;
    Integer yourIndex;

    public Boolean getGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(Boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(ArrayList<String> playerNames) {
        this.playerNames = playerNames;
        number++;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getYourIndex() {
        return yourIndex;
    }

    public void setYourIndex(Integer yourIndex) {
        this.yourIndex = yourIndex;
    }

    public RoomUpdate clone() {
        try {
            return (RoomUpdate) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
