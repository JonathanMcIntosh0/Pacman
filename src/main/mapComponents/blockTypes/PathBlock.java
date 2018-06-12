package main.mapComponents.blockTypes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.mapComponents.Coordinate;

/**
 * chemin que touts MovingEntity peut traverser ou que tout ItemBlock peut être desus
 * voir MapBlock pour plus d'info sur les methodes
 */
public class PathBlock extends MapBlock {

    public PathBlock(Coordinate blockCoord, double blockSize) {
        super(blockCoord, blockSize);
    }

    @Override
    protected void setGraphic(double blockSize) {
        Rectangle rectangle = new Rectangle(
                Math.ceil(blockSize),
                Math.ceil(blockSize),
                Color.BLACK
        );
        this.shapes.add(rectangle);
    }
}

