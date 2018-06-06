package main.mapComponents;

import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import main.movingParts.PacMan;


public class MapForeground extends Group {
    private double blockSize;

    public PacMan pacMan;

    public MapForeground(double initBlockSize,double width, double height) {
        this.blockSize = initBlockSize;
        createForeground(width, height);
    }

    public void resizeForeground(double blockSize, double width, double height) {
        pacMan.resize(blockSize, width, height);
    }

    private void createForeground(double width, double height) {
        this.pacMan = new PacMan(blockSize, width, height, new Coordinate(10, 15));

        this.getChildren().add(pacMan);
    }
}
