package main.mapComponents;

import javafx.scene.Group;
import main.movingParts.PacMan;


public class MapForeground extends Group {
    private double blockSize;

    PacMan pacMan;

    public MapForeground(double initBlockSize) {
        this.blockSize = initBlockSize;
        createForeground(blockSize);
    }

    public void resizeForeground(double blockSize) {
        pacMan.setSize(blockSize);
    }

    private void createForeground(double initBlockSize) {
        this.pacMan = new PacMan(initBlockSize, new Coordinate(10, 15));

        this.getChildren().add(pacMan);
    }
}
