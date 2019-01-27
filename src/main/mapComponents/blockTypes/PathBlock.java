package main.mapComponents.blockTypes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.mapComponents.Coordinate;

/**
 * chemin que touts MovingEntity peut traverser ou que tout ItemBlock peut être desus
 * voir MapBlock pour plus d'info sur les methodes
 */
public class PathBlock extends MapBlock {

    public PathBlock(Coordinate blockCoord, int blockSize) {
        super(blockCoord, blockSize);
    }

    @Override
    protected void setGraphic(int blockSize) {
        Rectangle rectangle = new Rectangle(
                blockSize,
                blockSize,
                Color.BLACK
        );
        this.shapes.add(rectangle);
    }
}

