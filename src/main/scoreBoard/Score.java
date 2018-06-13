package main.scoreBoard;

import java.io.Serializable;

/**
 * Score qui inclu le noms du joueur et la pointage
 */
public class Score implements Serializable{
    private final String playerName; //noms
    private final int point; //pointage

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
