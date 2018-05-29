package main.movingParts;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import main.mapComponents.Coordinate;

public class PacMan extends Canvas {
    Coordinate blockCoord;
    Coordinate coordinate;
    double diameter;

    int currentDirection = 0; // 0 = right, 1 = up, 2 = left, 3 = down
    int state = 0;
    boolean isOpening = true;

    GraphicsContext context = super.getGraphicsContext2D();

    public PacMan(double initBlockSize, Coordinate initBlockCoord) {
        super(initBlockSize, initBlockSize);

        this.diameter = initBlockSize;
        this.blockCoord = initBlockCoord;
        this.coordinate = new Coordinate(
                (int) (blockCoord.getX() * initBlockSize),
                (int) (blockCoord.getY() * initBlockSize)
        );

        updateGraphic();
        updatePos();
    }

    public void setSize(double diameter) {
        this.diameter = diameter;
    }

    public int getState() {
        return state;
    }

    public boolean isOpening() {
        return isOpening;
    }

    public void setDirection(int direction) {
        this.currentDirection = direction;
        updateGraphic();
    }

    public void changeState(int newState) {
        state = newState;
        updateGraphic();
        if (newState > 10) isOpening = false;
        if (newState < 1) isOpening = true;
    }

    private void updateGraphic() {
        context.clearRect(0, 0, diameter, diameter);
        context.setFill(Color.YELLOW);
        context.fillArc(0, 0, diameter, diameter, currentDirection * 90, 360 - (state * 10), ArcType.ROUND);
    }

    private void updatePos() {
        super.setLayoutX(coordinate.getX());
        super.setLayoutY(coordinate.getY());
    }
}
