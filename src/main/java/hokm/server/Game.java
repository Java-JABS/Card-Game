package hokm.server;

import hokm.Card;
import hokm.CardsSuit;
import hokm.Dast;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    // Todo make all these variables private
    CardsSuit hokm;
    Player hakem;
    Player currentPlayer;
    ArrayList<Player> players = new ArrayList<>();
    Dast onTableCards;
    Random random = new Random();
    Dast dast;
    Team[] teams = {new Team(), new Team()};

    public Game(ArrayList<Player> players) {
        if (players.size() != 4) throw new IllegalArgumentException();
        // set next hakem
        newSet(players.get(random.nextInt(3)));
    }

    void newSet(Player hakem) {
        if (!players.contains(hakem)) throw new RuntimeException();
        dast = new Dast(true);
        hakem.dast.addAll(dast.popFromStart(5));
        this.hakem = hakem;
    }

    boolean newRound() {
        Card highestCard = null;
        boolean isHokmPlayed = false;
        for (Card card : onTableCards) {
            if (card.suit == hokm && !isHokmPlayed) {
                highestCard = card;
                isHokmPlayed = true;
            }
            else if (highestCard == null) highestCard = card;
            else if (card.suit == highestCard.suit && card.value.number > highestCard.value.number) highestCard = card;
        }
        int indexWinnerPlayer = (players.indexOf(currentPlayer) + 1 + onTableCards.indexOf(highestCard)) % 4;
        teams[indexWinnerPlayer % 2].round();
        currentPlayer = players.get(indexWinnerPlayer);
        if (teams[0].getRound() == 7 ||teams[1].getRound() == 7) {
            //Todo:endgame
            return true;
        }
        onTableCards.clear();
        return false;
    }

    boolean putCard(Player player, Card card) throws Exception {
        if (!players.contains(player)) throw new Exception();
        if (!player.dast.contains(card)) throw new Exception();
        if (onTableCards.size() == 4) throw new Exception();
        if (player != currentPlayer) throw new Exception();
        if (!onTableCards.isEmpty()) if (card.suit != onTableCards.get(0).suit)
            if (player.dast.contains(onTableCards.get(0).suit)) throw new Exception();
        onTableCards.add(player.dast.pop(card));
        currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
        return onTableCards.size() == 4;
    }

    void hokm(Player player, CardsSuit hokm) throws Exception {
        if (!players.contains(player)) throw new Exception();
        if (player != hakem) throw new Exception();
        if (hokm != null) throw new Exception();
        this.hokm = hokm;
        hakem.dast.addAll(dast.popFromStart(8));
        for (Player playerI : players)
            if (playerI != hakem) playerI.dast.addAll(dast.popFromStart(13));
    }


}
