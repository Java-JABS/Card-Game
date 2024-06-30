package hokm.server;

import hokm.RoomUpdate;

import java.util.ArrayList;

public class Room {
    final int capacity;
    private final ArrayList<Player> players = new ArrayList<>();
    RoomUpdate roomUpdate = new RoomUpdate();
    ArrayList<String> names = new ArrayList<>();
    private Game game;

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
            names.add(newPlayer.name);
            roomUpdate.setPlayerNames(names);
        }
    }

    public boolean leave(Player player) {
        synchronized (this) {
            names.remove(player.name);
            roomUpdate.setPlayerNames(names);
            player.setRoom(null);
            return players.remove(player);
        }
    }

    public ArrayList<Player> getPlayers() {
        synchronized (this) {
            return new ArrayList<>(players);
        }
    }

    public void startGame() throws RequestException {
        synchronized (this) {
            if (isReady()) {
                game = new Game(players, this);
                for (Player player : players)
                    player.setGame(game);
                roomUpdate.setGameStarted(true);
                return;

            }
            throw new RequestException("Room is not reached its capacity! (" + capacity + ")");
        }
    }

    public boolean isReady() {
        synchronized (this) {
            return players.size() == capacity;
        }
    }

    public RoomUpdate getUpdate() {
        synchronized (this) {
            return roomUpdate.clone();
        }
    }

    public void endGame() {
        synchronized (this) {
            for (Player player : players)
                player.setGame(null);
            game = null;
            roomUpdate.setGameStarted(false);
        }

    }
}
