package hokm.server;

import hokm.Card;
import hokm.CardsSuit;
import hokm.Dast;
import hokm.GameUpdate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static java.lang.Math.abs;

public class Game {
    private final ArrayList<Player> players;
    private final Team[] teams = {new Team(), new Team()};
    private final Dast onTableCards = new Dast();
    private final GameUpdate majorUpdate = new GameUpdate();
    private final int[] lastUpdate = new int[4];
    private final Room room;
    private final Runnable waitForEveryoneToGetUpdate = () -> {
        synchronized (this) {
            while (Arrays.stream(lastUpdate).min().getAsInt() != majorUpdate.getNumber()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private CardsSuit rule;
    private Player ruler;
    private Player currentPlayer;
    private Dast dast;
    private GameState gameState;
    private GameUpdate minorUpdate;

    public Game(ArrayList<Player> players, Room room) {
        this.players = players;
        this.room = room;
        if (players.size() != 4) throw new IllegalArgumentException();
        Random random = new Random();
        this.gameState = GameState.NEW_SET;
        ArrayList<String> names = new ArrayList<>();
        for (Player player : players)
            names.add(player.name);
        minorUpdate = new GameUpdate(majorUpdate);
        minorUpdate.setPlayerNames(names);
        newSet(players.get(random.nextInt(3)));
    }

    private void newSet(Player ruler) {
        waitForEveryoneToGetUpdate.run();
        synchronized (this) {
            if (gameState != GameState.NEW_SET) {
                throw new RuntimeException();
            }
            if (!players.contains(ruler)) throw new RuntimeException();
            goal(teams[0], teams[1]);
            goal(teams[1], teams[0]);
            for (Team team : teams)
                team.clearRound();
            minorUpdate.setTeams(teams);
            gameState = (teams[0].getSet() >= 2 || teams[1].getSet() >= 2) ? GameState.END : GameState.HOKM;
            minorUpdate.setGameState(gameState);
            if (gameState == GameState.END) {
                waitForEveryoneToGetUpdate.run();
                room.endGame();
                majorUpdate.update(minorUpdate);
                return;
            }
            dast = new Dast(true);
            for (Player player : players)
                player.dast.clear();
            this.ruler = ruler;
            currentPlayer = ruler;
            minorUpdate.setCurrentPlayer(players.indexOf(currentPlayer));
            minorUpdate.setCurrentRuler(players.indexOf(ruler));
            ruler.dast.addAll(dast.popFromStart(5));
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
        waitForEveryoneToGetUpdate.run();
        synchronized (this) {
            if (gameState != GameState.NEXT_ROUND) {
                System.out.println("this shouldn't happen!");
            }
            minorUpdate = new GameUpdate(majorUpdate);
            Card highestCard = getHighestCard(onTableCards);
            int indexWinnerPlayer = (players.indexOf(currentPlayer) + onTableCards.indexOf(highestCard)) % 4;
            teams[indexWinnerPlayer % 2].round();
            currentPlayer = players.get(indexWinnerPlayer);
            minorUpdate.setCurrentPlayer(indexWinnerPlayer);
            onTableCards.clear();
            minorUpdate.setOnTableCards(onTableCards);
            if (teams[0].getRound() == 7 || teams[1].getRound() == 7) {
                gameState = GameState.NEW_SET;
                int rulerIndex = players.indexOf(ruler);
                int winnerTeam = (teams[0].getRound() == 7) ? 0 : 1;
                newSet(players.get((rulerIndex + abs(rulerIndex % 2 - winnerTeam)) % 4));
            } else {
                minorUpdate.setGameState(gameState);
                gameState = GameState.PUT_CARD;
                majorUpdate.update(minorUpdate);
            }
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
     *
     */
    public void putCard(Player player, Card card) throws RequestException {
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
                return;
            }
            gameState = GameState.PUT_CARD;
            minorUpdate.setGameState(gameState);
            majorUpdate.update(minorUpdate);
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