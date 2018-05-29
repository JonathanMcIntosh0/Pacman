package main.mapComponents;

import javafx.scene.Group;
import main.movingParts.PacMan;


public class MapForeground extends Group {
    private double blockSize;

    PacMan pacMan;

    public MapForeground(double initBlockSize) {
        this.blockSize = initBlockSize;
        this.pacMan = new PacMan(initBlockSize, new Coordinate(11, 16));
    }
}
