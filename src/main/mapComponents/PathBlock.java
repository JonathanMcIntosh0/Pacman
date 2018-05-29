package main.mapComponents;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PathBlock extends MapBlock {
    //    private boolean isActive;
//    private boolean hasCoin = true;

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
