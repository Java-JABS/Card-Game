package hokm.server;

import hokm.Card;
import hokm.CardsSuit;
import hokm.Dast;
import java.util.Objects;

public class Player {
    String token;
    private Game game;
    Dast dast = new Dast();

    Player(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(token, player.token);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(token);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean putCard(Card card) throws Exception {
        return game.putCard(this, card);
    }

    public void hokm(CardsSuit suit) throws Exception {
        game.hokm(this, suit);
    }
}
