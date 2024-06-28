package hokm;

import java.util.ArrayList;

public class RoomUpdate implements Cloneable{
    ArrayList<String> playerNames;

    Boolean isGameStarted =false;

    Integer number = 0;
    Integer yourIndex;
    public void setGameStarted(Boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    public Boolean getGameStarted() {
        return isGameStarted;
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

    public RoomUpdate clone(){
        try {
            return  (RoomUpdate) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }

    }
}
