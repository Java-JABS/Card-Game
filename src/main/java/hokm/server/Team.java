package hokm.server;

public class Team {
    private int set = 0;
    private int round = 0;


    public void set() {
        this.set++;
    }

    public void round() {
        this.round++;
    }

    public int getSet() {
        return set;
    }

    public int getRound() {
        return round;
    }

    public void kot() {
        this.round += 2;
    }

    public void rulerKot() {
        this.round += 3;
    }

    public void clearRound() {
        this.round = 0;
    }

}
