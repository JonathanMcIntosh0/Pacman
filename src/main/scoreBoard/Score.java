package main.scoreBoard;

import java.io.Serializable;

public class Score implements Serializable{
    private final String playerName;
    private final int point;

    public Score(String playerName, int point) {
        this.playerName = playerName;
        this.point = point;
    }

    public int getPoint() {
        return point;
    }

    public String getPlayerName() {
        return playerName;
    }
}
