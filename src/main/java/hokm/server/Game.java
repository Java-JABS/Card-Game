package hokm.server;

import hokm.Card;
import hokm.CardsSuit;
import hokm.Dast;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private CardsSuit rule;
    private Player ruler;
    private Player currentPlayer;
    private final ArrayList<Player> players;
    private Dast onTableCards;
    private Dast dast;
    private final Team[] teams = {new Team(), new Team()};

    public Game(ArrayList<Player> players) {
        if (players.size() != 4) throw new IllegalArgumentException();
        this.players= new ArrayList<>(players);
        // set next ruler
        Random random = new Random();
        newSet(players.get(random.nextInt(3)));
    }

    private void newSet(Player ruler) {
        if (!players.contains(ruler)) throw new RuntimeException();
        dast = new Dast(true);
        ruler.dast.addAll(dast.popFromStart(5));
        this.ruler = ruler;
    }

    public boolean newRound() {
        Card highestCard = null;
        boolean isHokmPlayed = false;
        for (Card card : onTableCards) {
            if (card.suit == rule && !isHokmPlayed) {
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

    public boolean putCard(Player player, Card card) throws Exception {
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

    public void hokm(Player player, CardsSuit hokm) throws Exception {
        if (!players.contains(player)) throw new Exception();
        if (player != ruler) throw new Exception();
        if (hokm != null) throw new Exception();
        this.rule = hokm;
        ruler.dast.addAll(dast.popFromStart(8));
        for (Player playerI : players)
            if (playerI != ruler) playerI.dast.addAll(dast.popFromStart(13));
    }


}
