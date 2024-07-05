package rule.server;

import rule.*;
import rule.messages.RequestException;

import java.util.Objects;

public class Player {
    final String name;
    final String token;
    Dast dast = new Dast();
    private Game game;
    private Room room;

    public Player(String name, String token) {
        this.name = name;
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

    public void putCard(Card card) throws RequestException {
        game.putCard(this, card);
    }

    public void rule(CardsSuit suit) throws RequestException {
        game.rule(this, suit);
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

    public RoomUpdate getRoomUpdate() {
        synchronized (this) {
            return room.getUpdate();
        }
    }

    public void leaveRoom() throws RequestException {
        synchronized (this) {
            room.leave(this);
        }
    }

    public void leaveGame() {
        synchronized (this) {
            game.endGame();
        }
    }
}
