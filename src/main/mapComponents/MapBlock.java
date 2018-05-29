package main.mapComponents;

import javafx.scene.Group;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

abstract class MapBlock extends Group{
    public final Coordinate blockCoord;
    List<Shape> shapes = new ArrayList<>();

    public MapBlock(Coordinate blockCoord, double blockSize) {
        this.blockCoord = blockCoord;
        resizeBlock(blockSize);
    }

    public void resizeBlock(double blockSize) {
        this.shapes.clear();
        setCoords(blockSize);
        setGraphic(blockSize);
        this.getChildren().setAll(shapes);
    }

    private void setCoords(double blockSize) {
        this.setLayoutX(blockCoord.x * blockSize);
        this.setLayoutY(blockCoord.y * blockSize);
    }

    protected abstract void setGraphic(double blockSize);
}
