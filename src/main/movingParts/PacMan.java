package main.movingParts;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import main.mapComponents.Coordinate;
import main.mapGenerator.MapDatabase;

public class PacMan extends Canvas {
    Coordinate blockCoord;
    Coordinate coordinate;
    double diameter;

    int direction = 0; // 0 = right, 1 = up, 2 = left, 3 = down
    int state = 0;

    GraphicsContext context = super.getGraphicsContext2D();

    public PacMan(double initBlockSize, Coordinate initBlockCoord) {
        super(initBlockSize, initBlockSize);

        this.diameter = initBlockSize;
        this.blockCoord = initBlockCoord;
        this.coordinate = new Coordinate(
                blockCoord.getX() * initBlockSize,
                blockCoord.getY() * initBlockSize
                );

        setGraphic(initBlockSize);
    }

    private void setGraphic(double blockSize) {
        context.clearRect(0, 0, blockSize, blockSize);
        context.setFill(Color.YELLOW);
        context.fillArc(0, 0, blockSize, blockSize, direction * 90, 360 - (state * 10), ArcType.ROUND);
    }

    private void setPos
}
