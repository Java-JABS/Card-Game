package hokm.server;

import hokm.Card;
import hokm.CardsSuit;
import hokm.Dast;
import hokm.GameUpdate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Game {
    private final ArrayList<Player> players;
    private final Team[] teams = {new Team(), new Team()};
    private CardsSuit rule;
    private Player ruler;
    private Player currentPlayer;
    private Dast onTableCards;
    private Dast dast;
    private GameState gameState;
    private final GameUpdate update = new GameUpdate();
    public Game(ArrayList<Player> players) {
        this.players = players;
        if (players.size() != 4) throw new IllegalArgumentException();
        // set next ruler
        Random random = new Random();
        newSet(players.get(random.nextInt(3)));
    }

    private void newSet(Player ruler) {
        synchronized (this) {
            if (gameState != GameState.NEW_SET)
                throw new RuntimeException();
            if (!players.contains(ruler)) throw new RuntimeException();
            dast = new Dast(true);
            this.ruler = ruler;
            update.setCurrentRuler(players.indexOf(ruler));
            ruler.dast.addAll(dast.popFromStart(5));
            gameState = GameState.HOKM;
            update.setGameState(gameState);
            goal(teams[0], teams[1]);
            goal(teams[1], teams[0]);
            gameState = (teams[0].getSet() == 7 || teams[1].getSet() == 7) ? GameState.END : GameState.HOKM;
            update.setGameState(gameState);
        }
    }

    private void goal(Team team0, Team team1) {
        if (team0.getRound() == 7) {
            if (team1.getRound() == 0) {
                if (players.indexOf(ruler) % 2 == 0) {
                    team0.kot();
                } else {
                    team0.rulerKot();
                }
            } else team0.set();
        }
    }

    public void newRound() throws Exception {
        synchronized (this) {
            if (gameState != GameState.NEXT_ROUND)
                throw new Exception();
            // what if there is not 4 cards?!
            Card highestCard = getHighestCard(onTableCards);
            int indexWinnerPlayer = (players.indexOf(currentPlayer) + 1 + onTableCards.indexOf(highestCard)) % 4;
            teams[indexWinnerPlayer % 2].round();
            currentPlayer = players.get(indexWinnerPlayer);
            if (teams[0].getRound() == 7 || teams[1].getRound() == 7) {
                gameState = GameState.NEW_SET;
                update.setGameState(gameState);
            }
            onTableCards.clear();
            gameState = GameState.PUT_CARD;
            update.setGameState(gameState);
        }
    }

    private Card getHighestCard(Collection<Card> onTableCards) {
        Card highestCard = null;
        boolean isHokmPlayed = false;
        for (Card card : onTableCards) {
            if (card.suit() == rule && !isHokmPlayed) {
                highestCard = card;
                isHokmPlayed = true;
            } else if (highestCard == null) highestCard = card;
            else if (card.suit() == highestCard.suit() && card.value().number > highestCard.value().number)
                highestCard = card;
        }
        return highestCard;
    }

    /**
     * @return if 4 card has been put and newRound should be started
     */
    public boolean putCard(Player player, Card card) throws Exception {
        synchronized (this) {
            if (gameState != GameState.PUT_CARD)
                throw new Exception();
            //change exeptionTypes :)
            if (!players.contains(player)) throw new Exception();
            if (!player.dast.contains(card)) throw new Exception();
            if (onTableCards.size() == 4) throw new Exception();
            if (player != currentPlayer) throw new Exception();
            if (!onTableCards.isEmpty()) if (card.suit() != onTableCards.get(0).suit())
                if (player.dast.contains(onTableCards.get(0).suit())) throw new Exception();
            player.dast.remove(card);
            onTableCards.add(card);
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
            update.setCurrentPlayer(players.indexOf(currentPlayer));
            if (onTableCards.size() == 4) {
                gameState = GameState.NEXT_ROUND;
                update.setGameState(gameState);
                return true;
            }
            gameState = GameState.PUT_CARD;
            update.setGameState(gameState);
            return false;
        }
    }

    public void hokm(Player player, CardsSuit rule) throws Exception {
        synchronized (this) {
            if (gameState != GameState.HOKM)
                throw new Exception();
            if (!players.contains(player)) throw new Exception();
            if (player != ruler) throw new Exception();
            if (rule != null) throw new Exception();
            this.rule = rule;
            update.setRule(this.rule);
            ruler.dast.addAll(dast.popFromStart(8));
            for (Player playerI : players)
                if (playerI != ruler) playerI.dast.addAll(dast.popFromStart(13));
            gameState = GameState.PUT_CARD;
        }
    }

    public Dast getOnTableCards() {
        return onTableCards;
    }
}