package main.viewsAndModels.game;

import main.mapComponents.Coordinate;

/**
 * le database du jeu
 */
public class MapDatabase {
    //21x21
    //0 = wall, 1 = path, 2 = coin, 3 = powerup, 4 = ghostWall
    // le layout du jeu que j'ai décidé sur
    private static final int[][] PRESET_MAP_LAYOUT = {
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1},
            {1, 0, 3, 0, 0, 2, 0, 0, 0, 2, 0, 2, 0, 0, 0, 2, 0, 0, 3, 0, 1},
            {1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1},
            {1, 0, 2, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 2, 0, 2, 0, 0, 2, 0, 1},
            {1, 0, 2, 2, 2, 2, 0, 2, 2, 2, 0, 2, 2, 2, 0, 2, 2, 2, 2, 0, 1},
            {1, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 0, 2, 0, 1, 1, 1, 1, 1, 1, 1, 0, 2, 0, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 2, 0, 1, 0, 0, 4, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 2, 0, 1, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 0, 2, 0, 1, 1, 1, 1, 1, 1, 1, 0, 2, 0, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 2, 0, 1, 0, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 1},
            {1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1},
            {1, 0, 2, 0, 0, 2, 0, 0, 0, 2, 0, 2, 0, 0, 0, 2, 0, 0, 2, 0, 1},
            {1, 0, 3, 2, 0, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 0, 2, 3, 0, 1},
            {1, 0, 0, 2, 0, 2, 0, 2, 0, 0, 0, 0, 0, 2, 0, 2, 0, 2, 0, 0, 1},
            {1, 0, 2, 2, 2, 2, 0, 2, 2, 2, 0, 2, 2, 2, 0, 2, 2, 2, 2, 0, 1},
            {1, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 1},
            {1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    };
    public static final int INIT_MAP_BLOCK_SIZE = 25; // largeur d'un case initialement en pixel par case

    public final int[][] mapLayout; //le mapLyout du jeu actuel (je peut crée plus de mapLayout et choisir lequelle qui vas être jouer)
    public final double blockSize;

    private String playerName; // noms du joueur actuel

    public MapDatabase() {
        mapLayout = PRESET_MAP_LAYOUT;
        blockSize = INIT_MAP_BLOCK_SIZE;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    /**
     * retourne la valeur du case
     * @param blockCoord Coordoné du case en question
     * @return la valeur du case avec le blockCoord, si outOfBounds retourne -1
     */
    public int getBlockValue(Coordinate blockCoord) {
        try {
            return mapLayout[(int) Math.round(blockCoord.getY())][(int) Math.round(blockCoord.getX())];
        } catch (ArrayIndexOutOfBoundsException e) {
            return -1;
        }
    }
}
