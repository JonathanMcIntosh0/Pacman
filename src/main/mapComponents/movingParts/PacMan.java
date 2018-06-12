package main.mapComponents.movingParts;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import main.mapComponents.Coordinate;

public class PacMan extends MovingEntity {
    private static final double INIT_SPEED_OF_MOVE = 3.5; // blocks / millisec , 1 sec = 1000 milli
    private static final double POWERED_SPEED = 5.0;
    private static final int SPEED_OF_ANIMATION = 10;
    private static final double RADIUS = SIZE / 2;

    private boolean isOpening = true;
    public boolean canMove = false;
    public boolean isPoweredUp = false;

    private Paint color = Color.YELLOW;

    public PacMan(Coordinate initBlockCoord) {
        super(initBlockCoord, INIT_SPEED_OF_MOVE);

        animTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (canMove) {
                    changeState(isOpening ? animState + SPEED_OF_ANIMATION : animState - SPEED_OF_ANIMATION);
                    updateGraphic();
                }
            }
        };
    }

/*
    public void resize(double newDiameter) {
        Coordinate newCoord = new Coordinate(
                coordinate.getX() / this.diameter * newDiameter,
                coordinate.getY() / this.diameter * newDiameter
        );

        this.diameter = newDiameter;
        this.resize(diameter, diameter);

        updateGraphic();
        setPos(newCoord);
        updatePos();
    }
*/


    public Coordinate getBlockCoord() {
        return blockCoord;
    }

    public int getDirection() {
        return currentDirection;
    }

    public int getNextDirection() {
        return nextDirection;
    }

    public void setPowered(boolean isPoweredUp) {
        this.isPoweredUp = isPoweredUp;
        speedOfMove = isPoweredUp ? POWERED_SPEED : INIT_SPEED_OF_MOVE;
        color = isPoweredUp ? Color.DEEPSKYBLUE : Color.YELLOW;
    }

    //Animation

    private void changeState(double newState) {
        animState = newState;
        if (newState > 90 - SPEED_OF_ANIMATION) isOpening = false;
        if (newState < 1 + SPEED_OF_ANIMATION) isOpening = true;
    }

    //Updaters

    @Override
    protected void updateGraphic() {
        this.getChildren().clear();
        Arc pacManShape = new Arc(
                RADIUS,
                RADIUS,
                RADIUS,
                RADIUS,
                currentDirection * 90 + animState / 2,
                360 - animState
        );
        pacManShape.setType(ArcType.ROUND);
        pacManShape.setFill(color);
        this.getChildren().add(pacManShape);
    }
}
