package hokm.server;

import hokm.Card;
import hokm.CardsSuit;
import hokm.Dast;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private int hakemIndex;
    CardsSuit hokm;
    Player currentPlayer;
    ArrayList<Player> players = new ArrayList<>();
    Dast onTableCards;
    Random random = new Random();
    Dast dast;

    public Game(ArrayList<Player> players) {
        if (players.size() != 4) throw new IllegalArgumentException();
    }

    void newBigRound(Player hakem) {
        dast = new Dast(true);
        players.get(hakemIndex).dast.addAll(dast.popFromStart(5));
        hokm = null;
    }
    void newSmallRound(){
        //todo set goal
        //todo set current player
        //todo if up to 7 rounds finish game
        onTableCards.clear();

    }
    //0,2 team and 1,3 team
    void startGame() {
        hakemIndex = random.nextInt(3);


    }

    boolean putCard(Player player, Card card) throws Exception {
        if (!players.contains(player)) throw new Exception();
        if (!player.dast.contains(card)) throw new Exception();
        if (onTableCards.size() == 4) throw new Exception();
        if (player != currentPlayer) throw new Exception();
        if (!onTableCards.isEmpty()) if (card.cardSuit != onTableCards.get(0).cardSuit)
            if (player.dast.contains(onTableCards.get(0).cardSuit)) throw new Exception();
        onTableCards.add(player.dast.pop(card));
        return onTableCards.size() == 4;
    }

    void hokm(Player player, CardsSuit hokm) throws Exception {
        if (!players.contains(player)) throw new Exception();
        if (player != players.get(hakemIndex)) throw new Exception();
        if (hokm != null) throw new Exception();
        //todo throw card
        this.hokm = hokm;
        players.get(hakemIndex).dast.addAll(dast.popFromStart(8));
        for (int i = 0; i < 3; i++)
            if (i != hakemIndex) players.get(i).dast.addAll(dast.popFromStart(13));

    }


}
