package hokm.server;

import java.util.ArrayList;

public class Room {
    final int capacity;
    private final ArrayList<Player> players = new ArrayList<>();

    public Room(int capacity) {
        this.capacity = capacity;
    }

    public Room() {
        this(4);
    }

    public void join(Player newPlayer) throws RequestException {
        synchronized (this) {
            if (newPlayer.getRoom() != null) throw new RequestException("You are already in a room!");
            if (players.size() >= capacity) throw new RequestException("Room is full!");
            players.add(newPlayer);
            newPlayer.setRoom(this);
        }
    }

    public boolean leave(Player player) {
        synchronized (this) {
            return players.remove(player);
        }
    }

    public ArrayList<Player> getPlayers() {
        synchronized (this) {
            return new ArrayList<>(players);
        }
    }

    public Game startGame() throws RequestException {
        synchronized (this) {
            if (players.size() == capacity) {
                Game game = new Game(players);
                for (Player player : players)
                    player.setGame(game);
                return game;

            }
            throw new RequestException("Room is not reached its capacity! (" + capacity + ")");
        }
    }

    public boolean isReady() {
        synchronized (this) {
            return players.size() == capacity;
        }
    }
}
