package main.mapGenerator;

import main.mapComponents.Coordinate;

public class MapDatabase {
    //21x21
    //0 = wall, 1 = path, 2 = coin, 3 = powerup, 4 = ghostWall
    public static final int[][] PRESET_MAP_LAYOUT = {
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
            {1, 0, 3, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2, 3, 0, 1},
            {1, 0, 0, 2, 0, 2, 0, 2, 0, 0, 0, 0, 0, 2, 0, 2, 0, 2, 0, 0, 1},
            {1, 0, 2, 2, 2, 2, 0, 2, 2, 2, 0, 2, 2, 2, 0, 2, 2, 2, 2, 0, 1},
            {1, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 1},
            {1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    };
    public static final int INIT_MAP_BLOCK_SIZE = 25;
    public static final double COIN_BLOCK_RATIO = 0.25;

    public int[][] mapLayout;
    public double blockSize;

    public MapDatabase() {
        mapLayout = PRESET_MAP_LAYOUT;
        blockSize = INIT_MAP_BLOCK_SIZE;
    }

    public int getBlockValue(Coordinate blockCoord) {
        try {
            return mapLayout[(int) blockCoord.getY()][(int) blockCoord.getX()];
        } catch (ArrayIndexOutOfBoundsException e) {
            return -1;
        }
    }
}
