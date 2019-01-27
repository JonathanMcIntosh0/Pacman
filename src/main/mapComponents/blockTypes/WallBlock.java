package main.mapComponents.blockTypes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.mapComponents.Coordinate;

/**
 * murs que rien peut passer à travers
 * voir MapBlock pour plus d'info sur les methodes
 */
public class WallBlock extends MapBlock {

    public WallBlock(Coordinate blockCoord, int blockSize) {
        super(blockCoord, blockSize);
    }

    @Override
    protected void setGraphic(int blockSize) {
        Rectangle rectangle = new Rectangle(
                blockSize,
                blockSize,
                Color.DARKSLATEBLUE
        );
        this.shapes.add(rectangle);
    }
}
