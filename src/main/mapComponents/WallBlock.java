package main.mapComponents;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class WallBlock extends MapBlock {

    public WallBlock(Coordinate blockCoord, double blockSize) {
        super(blockCoord, blockSize);
    }

    @Override
    protected void setGraphic(double blockSize) {
        Rectangle rectangle = new Rectangle(
                Math.ceil(blockSize),
                Math.ceil(blockSize),
                Color.DARKSLATEBLUE
        );
        this.shapes.add(rectangle);
    }
}
