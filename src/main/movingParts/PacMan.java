package main.movingParts;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import main.mapComponents.Coordinate;
import main.mapComponents.MapBackground;
import main.mapGenerator.MapDatabase;

import java.util.Timer;

public class PacMan extends Pane {
    private static final double SPEED = 5 / 100;

    Arc pacManShape = new Arc();

    Coordinate blockCoord;
    Coordinate coordinate;

    double diameter;
    int currentDirection = -1; // 0 = right, 1 = up, 2 = left, 3 = down
    int nextDirection = -1;
    double state = 0;
    boolean isOpening = true;

    public boolean canMove = false;

    public PacMan(double initBlockSize, Coordinate initBlockCoord) {
        pacManShape.setFill(Color.YELLOW);
        pacManShape.setType(ArcType.ROUND);

        this.diameter = initBlockSize;
        pacManShape.setRadiusX(diameter);
        pacManShape.setRadiusY(diameter);

        this.blockCoord = initBlockCoord;
        this.coordinate = new Coordinate(
                blockCoord.getX() * initBlockSize,
                blockCoord.getY() * initBlockSize
        );
        this.updatePos();
        this.getChildren().add(pacManShape);
    }

    public void resize(double newDiameter) {
        Coordinate newCoord = new Coordinate(
                coordinate.getX() / this.diameter * newDiameter,
                coordinate.getY() / this.diameter * newDiameter
        );

        this.diameter = newDiameter;

        pacManShape.setRadiusX(diameter);
        pacManShape.setRadiusY(diameter);
        this.resize(diameter, diameter);

        setPos(newCoord);
        updatePos();
    }

    //MOVING

   /* public void move(Coordinate nextBlockCoord) {
        double total;
        double fullMoveAmount = diameter / 2;
        double moveAmount = fullMoveAmount * SPEED;
        double dy = 0;
        double dx = 0;

        blockCoord = nextBlockCoord;

        for (int i = 0; i < 2; i++) {
            total = 0;

            do {
                switch (currentDirection) {
                    case Direction.RIGHT:
                        dx = moveAmount;
                        break;
                    case Direction.LEFT:
                        dx = -moveAmount;
                        break;
                    case Direction.DOWN:
                        dy = moveAmount;
                        break;
                    case Direction.UP:
                        dy = -moveAmount;
                }
                total += (dx + dy);

                changeState(isOpening ? state + 1 : state - 1);
                updateGraphic(new Coordinate(coordinate.getX() + dx, coordinate.getY() + dy));

            } while (total < fullMoveAmount);

            if (i == 0) {
                switch (currentDirection) {
                    case Direction.RIGHT:
                        dx = -fullMoveAmount;
                        break;
                    case Direction.LEFT:
                        dx = fullMoveAmount;
                        break;
                    case Direction.DOWN:
                        dy = -fullMoveAmount;
                        break;
                    case Direction.UP:
                        dy = +fullMoveAmount;
                }

                updateGraphic(new Coordinate(blockCoord.getX() * diameter + dx, blockCoord.getY() * diameter + dy));
            }
        }

    }
*/
    public Coordinate getBlockCoord() {
        return blockCoord;
    }

    public int getDirection() {
        return currentDirection;
    }

    public void setDirection(int direction) {
        this.currentDirection = direction;
    }

    //Animation

    private void changeState(double newState) {
        state = newState;
        if (newState > 90) isOpening = false;
        if (newState < 1) isOpening = true;
    }

    private void setPos(Coordinate newCoord) {
        coordinate = newCoord;

    }

    //Updaters

    private void updateDirection() {
        currentDirection = nextDirection;
        pacManShape.setStartAngle(currentDirection * 90);
    }

    private void updateGraphic() {

//        context.fillArc(
//                newCoord.getX(),
//                newCoord.getY(),
//                diameter,
//                diameter,
//                currentDirection * 90,
//                360 - state,
//                ArcType.ROUND
//        );
//        pacManShape.setLength();
    }

    private void updatePos() {
        this.setLayoutX(coordinate.getX());
        this.setLayoutY(coordinate.getY());
    }

}
