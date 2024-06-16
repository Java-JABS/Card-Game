package hokm.server;

import java.util.ArrayList;

public class Room {
    private final ArrayList<Player> players = new ArrayList<>();
    private final int capacity;

    public Room(int capacity) {
        this.capacity = capacity;
    }

    public Room() {
        this(4);
    }

    public boolean join(Player newPlayer) {
        synchronized (this) {
            if (players.size() >= capacity) return false;
            players.add(newPlayer);
            return true;
        }
    }

    public boolean left(Player player){
        synchronized (this){
            return players.remove(player);
        }
    }
    public ArrayList<Player> getPlayers(){
        synchronized (this){
            return new ArrayList<>(players);
        }
    }
}
