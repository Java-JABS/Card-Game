package hokm.server;

import hokm.Card;
import hokm.CardsSuit;
import hokm.Dast;
import hokm.GameUpdate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class Game {
    private final ArrayList<Player> players;
    private final Team[] teams = {new Team(), new Team()};
    private CardsSuit rule;
    private Player ruler;
    private Player currentPlayer;
    private final Dast onTableCards = new Dast();
    private Dast dast;
    private GameState gameState;
    private final GameUpdate majorUpdate = new GameUpdate();
    private GameUpdate minorUpdate;
    private final int[] lastUpdate = new int[4];
    private Room room;
    public Game(ArrayList<Player> players,Room room) {
        this.players = players;
        this.room = room;
        if (players.size() != 4) throw new IllegalArgumentException();
        // set next ruler
        Random random = new Random();
        this.gameState = GameState.NEW_SET;
        newSet(players.get(random.nextInt(3)));
    }

    private void newSet(Player ruler) {
        synchronized (this) {
            if (gameState != GameState.NEW_SET)
                throw new RuntimeException();
            if (!players.contains(ruler)) throw new RuntimeException();
            dast = new Dast(true);
            this.ruler = ruler;
            currentPlayer = ruler;
            minorUpdate = new GameUpdate(majorUpdate);
            minorUpdate.setCurrentPlayer(players.indexOf(currentPlayer));
            minorUpdate.setCurrentRuler(players.indexOf(ruler));
            ruler.dast.addAll(dast.popFromStart(5));
            gameState = GameState.HOKM;
            minorUpdate.setGameState(gameState);
            goal(teams[0], teams[1]);
            goal(teams[1], teams[0]);
            gameState = (teams[0].getSet() == 7 || teams[1].getSet() == 7) ? GameState.END : GameState.HOKM;
            minorUpdate.setTeams(teams);
            minorUpdate.setGameState(gameState);
            majorUpdate.update(minorUpdate);
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

    public void newRound() {
        synchronized (this) {
            if (gameState != GameState.NEXT_ROUND)
                throw new RuntimeException();
            while (Arrays.stream(lastUpdate).min().getAsInt() != majorUpdate.getNumber()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            minorUpdate = new GameUpdate(majorUpdate);
            // what if there is not 4 cards?!
            Card highestCard = getHighestCard(onTableCards);
            int indexWinnerPlayer = (players.indexOf(currentPlayer) + onTableCards.indexOf(highestCard)) % 4;
            teams[indexWinnerPlayer % 2].round();
            currentPlayer = players.get(indexWinnerPlayer);
            minorUpdate.setCurrentPlayer(indexWinnerPlayer);
            if (teams[0].getRound() == 7 || teams[1].getRound() == 7) {
                gameState = GameState.NEW_SET;
                minorUpdate.setGameState(gameState);
            }
            onTableCards.clear();
            minorUpdate.setOnTableCards(onTableCards);
            gameState = GameState.PUT_CARD;
            minorUpdate.setGameState(gameState);
            majorUpdate.update(minorUpdate);
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
    public boolean putCard(Player player, Card card) throws RequestException {
        synchronized (this) {
            if (gameState != GameState.PUT_CARD)
                throw new RequestException("Can not put card right now!");
            //change exeptionTypes :)
            if (!players.contains(player)) throw new RuntimeException();
            if (!player.dast.contains(card)) throw new RequestException("You don't Have this Card!");
            if (onTableCards.size() == 4) throw new RequestException("You can not put card right now!");
            if (player != currentPlayer) throw new RequestException("It's not your turn!");
            if (!onTableCards.isEmpty()) if (card.suit() != onTableCards.get(0).suit())
                if (player.dast.contains(onTableCards.get(0).suit()))
                    throw new RequestException("You should select a " + onTableCards.get(0).suit().toString().toLowerCase() + " card!");
            minorUpdate = new GameUpdate(majorUpdate);
            player.dast.remove(card);
            onTableCards.add(card);
            minorUpdate.setOnTableCards(onTableCards);
            currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % 4);
            minorUpdate.setCurrentPlayer(players.indexOf(currentPlayer));
            if (onTableCards.size() == 4) {
                gameState = GameState.NEXT_ROUND;
                minorUpdate.setGameState(gameState);
                majorUpdate.update(minorUpdate);
                new Thread(this::newRound).start();
                return true;
            }
            gameState = GameState.PUT_CARD;
            minorUpdate.setGameState(gameState);
            majorUpdate.update(minorUpdate);
            return false;
        }
    }

    public void hokm(Player player, CardsSuit rule) throws RequestException {
        synchronized (this) {
            if (gameState != GameState.HOKM)
                throw new RequestException("Can not hokm right now!");
            if (!players.contains(player)) throw new RuntimeException();
            if (player != ruler) throw new RequestException("You are not the ruler!");
            if (rule == null) throw new RuntimeException();
            this.rule = rule;
            minorUpdate = new GameUpdate(majorUpdate);
            minorUpdate.setRule(this.rule);
            ruler.dast.addAll(dast.popFromStart(8));
            for (Player playerI : players)
                if (playerI != ruler) playerI.dast.addAll(dast.popFromStart(13));
            minorUpdate.setOnTableCards(onTableCards);
            gameState = GameState.PUT_CARD;
            minorUpdate.setGameState(gameState);
            majorUpdate.update(minorUpdate);
        }
    }

    public GameUpdate getUpdate(Player player, boolean isMajorUpdate) {
        synchronized (this) {
            if (!players.contains(player)) throw new RuntimeException();
            lastUpdate[players.indexOf(player)] = majorUpdate.getNumber();
            GameUpdate gameUpdate;
            if (isMajorUpdate) {
                gameUpdate = majorUpdate.clone();
                gameUpdate.setYourIndex(players.indexOf(player));
                gameUpdate.setDast(player.dast);
            } else gameUpdate = minorUpdate.clone();
            notify();
            return gameUpdate;
        }
    }
}