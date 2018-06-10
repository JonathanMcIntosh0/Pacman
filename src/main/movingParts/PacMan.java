package main.movingParts;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import main.mapComponents.Coordinate;
import main.mapGenerator.MapDatabase;

public class PacMan extends Pane {
    public static final double SPEED_OF_MOVE = 1 / 1000; // blocks / millisec , 1 sec = 1000 milli
    private static final int SPEED_OF_ANIMATION = 5;
    private static final double RADIUS = MapDatabase.INIT_MAP_BLOCK_SIZE / 2;

    public AnimationTimer pacManAnimTimer;

    Arc pacManShape;

    Coordinate blockCoord;
    Coordinate coordinate;

    int currentDirection = -1; // 0 = right, 1 = up, 2 = left, 3 = down
    int nextDirection = -1;
    double state = 0;
    boolean isOpening = true;

    public boolean isMoving = false;
    public boolean atIntersecton = true;

    public PacMan(Coordinate initBlockCoord) {
        this.blockCoord = initBlockCoord;
        this.coordinate = new Coordinate(
                blockCoord.getX() * MapDatabase.INIT_MAP_BLOCK_SIZE,
                blockCoord.getY() * MapDatabase.INIT_MAP_BLOCK_SIZE
        );
        this.updatePos();
        this.updateGraphic();

        pacManAnimTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isMoving) return;
                changeState(isOpening ? state + SPEED_OF_ANIMATION : state - SPEED_OF_ANIMATION);
                updateGraphic();
            }
        };
    }

//    public void resize(double newDiameter) {
//        Coordinate newCoord = new Coordinate(
//                coordinate.getX() / this.diameter * newDiameter,
//                coordinate.getY() / this.diameter * newDiameter
//        );
//
//        this.diameter = newDiameter;
//        this.resize(diameter, diameter);
//
//        updateGraphic();
//        setPos(newCoord);
//        updatePos();
//    }

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

    public void setNextDirection(int direction) {
        this.nextDirection = direction;
    }

    //Animation

    private void changeState(double newState) {
        state = newState;
        if (newState > 90 - SPEED_OF_ANIMATION) isOpening = false;
        if (newState < 1 + SPEED_OF_ANIMATION) isOpening = true;
    }

    public void translatePos(double dx, double dy) {
        setPos(new Coordinate(
                coordinate.getX() + dx,
                coordinate.getY() + dy
        ));
    }

    private void setPos(Coordinate newCoord) {
        coordinate = newCoord;
        double newX = coordinate.getX() / MapDatabase.INIT_MAP_BLOCK_SIZE;
        double newY = coordinate.getY() / MapDatabase.INIT_MAP_BLOCK_SIZE;
        Coordinate newBlockCoord = new Coordinate(
                newX,
                newY
        );
        blockCoord = newBlockCoord;
        if (Math.round(newX) == newX && Math.round(newY) == newY) {
            atIntersecton = true;
        } else {
            atIntersecton = false;
        }
        updatePos();
    }

    public void setDirection() {
        currentDirection = nextDirection;
    }

    //Updaters

    private void updateGraphic() {
        this.getChildren().clear();
        pacManShape = new Arc(
                RADIUS,
                RADIUS,
                RADIUS,
                RADIUS,
                currentDirection * 90 + state / 2,
                360 - state
        );
        pacManShape.setType(ArcType.ROUND);
        pacManShape.setFill(Color.YELLOW);
        this.getChildren().add(pacManShape);
    }

    private void updatePos() {
        this.setLayoutX(coordinate.getX());
        this.setLayoutY(coordinate.getY());
    }

}
