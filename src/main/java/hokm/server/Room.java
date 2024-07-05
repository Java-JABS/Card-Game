package hokm.server;

import hokm.RoomUpdate;
import hokm.messages.RequestErrorMessage;
import hokm.messages.RequestException;

import java.util.ArrayList;
import java.util.Collections;

public class Room {
    final int capacity;
    private final ArrayList<Player> players = new ArrayList<>();
    RoomUpdate roomUpdate = new RoomUpdate();
    ArrayList<String> names = new ArrayList<>();
    private Game game;
    final String token;

    public Room(int capacity, String token) {
        this.capacity = capacity;
        this.token = token;
        roomUpdate.setToken(token);
    }

    public Room(String token) {
        this(4, token);
    }

    public void join(Player newPlayer) throws RequestException {
        synchronized (this) {
            if (newPlayer.getRoom() != null) throw new RequestException(RequestErrorMessage.IN_ROOM);
            if (players.size() >= capacity) throw new RequestException(RequestErrorMessage.ROOM_FULL);
            players.add(newPlayer);
            newPlayer.setRoom(this);
            names.add(newPlayer.name);
            roomUpdate.setPlayerNames(names);
        }
    }

    public void leave(Player player) throws RequestException{
        synchronized (this) {
            if (roomUpdate.getGameStarted()) throw new RequestException(RequestErrorMessage.IN_GAME);
            names.remove(player.name);
            roomUpdate.setPlayerNames(names);
            player.setRoom(null);
            players.remove(player);
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
                ArrayList<Player> shuffledPlayers= (ArrayList<Player>) players.clone();
                Collections.shuffle(shuffledPlayers);
                game = new Game(shuffledPlayers, this);
                for (Player player : players)
                    player.setGame(game);
                roomUpdate.setGameStarted(true);
                return;

            }
            throw new RequestException(RequestErrorMessage.ROOM_NOT_FULL);
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
