package main.mapComponents;

import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import main.movingParts.PacMan;


public class MapForeground extends Group {

    public PacMan pacMan;

    public MapForeground() {
        createForeground();
    }

//    public void resizeForeground(double initBlockSize) {
//        pacMan.resize(initBlockSize);
//    }

    private void createForeground() {
        this.pacMan = new PacMan(new Coordinate(10, 15));

        this.getChildren().add(pacMan);
    }
}
