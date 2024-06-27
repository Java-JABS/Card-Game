package hokm.server;

import hokm.Card;
import hokm.CardsSuit;
import hokm.Dast;
import hokm.GameUpdate;
import hokm.messages.ClientState;

import java.util.Objects;

public class Player {
    final String token;
    Dast dast = new Dast();
    private Game game;
    private Room room;
    private ClientState state;

    public Player(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return Objects.equals(token, player.token);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(token);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GameUpdate getGameUpdate(boolean isMajorUpdate) {
        return game.getUpdate(this, isMajorUpdate);
    }

    public boolean putCard(Card card) throws RequestException {
        return game.putCard(this, card);
    }

    public void hokm(CardsSuit suit) throws RequestException {
        game.hokm(this, suit);
    }

    public boolean isInARoom() {
        synchronized (this) {
            return room != null;
        }
    }

    public boolean isInAGame() {
        synchronized (this) {
            return game != null;
        }
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        synchronized (this) {
            this.room = room;
        }
    }
}
