package main.mapComponents.movingParts;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import main.mapComponents.Coordinate;
import main.viewsAndModels.game.MapDatabase;

public abstract class MovingEntity extends Pane {
    static final double SIZE = MapDatabase.INIT_MAP_BLOCK_SIZE;

//    protected int speedOfAnim;
    public double speedOfMove;

    public AnimationTimer animTimer;

    Coordinate blockCoord;
    private Coordinate coordinate;

    int currentDirection = -1; // 0 = right, 1 = up, 2 = left, 3 = down
    int nextDirection = -1;
    double animState = 0;

    public boolean atIntersecton = true;

    MovingEntity(Coordinate initBlockCoord, double initSpeedOfMove) {
        this.blockCoord = initBlockCoord;
        this.speedOfMove = initSpeedOfMove;
        this.coordinate = new Coordinate(
                blockCoord.getX() * SIZE,
                blockCoord.getY() * SIZE
        );

        updatePos();
        updateGraphic();
    }

    public void setNextDirection(int direction) {
        this.nextDirection = direction;
    }

    public void setDirection() {
        currentDirection = nextDirection;
    }

    public void translatePos(double dx, double dy) {
        setPos(new Coordinate(
                coordinate.getX() + dx,
                coordinate.getY() + dy
        ));
    }

    private void setPos(Coordinate newCoord) {
        coordinate = newCoord;
        double newX = coordinate.getX() / SIZE;
        double newY = coordinate.getY() / SIZE;
        blockCoord = new Coordinate(
                newX,
                newY
        );

        int intersecX = (int) Math.round(newX);
        int intersecY = (int) Math.round(newY);
        double error = 2 / SIZE;
        atIntersecton = (intersecX + error > newX && intersecX - error < newX) && (intersecY + error > newY && intersecY - error < newY);
        updatePos();
    }

    private void updatePos() {
        this.setLayoutX(coordinate.getX());
        this.setLayoutY(coordinate.getY());
    }

    protected abstract void updateGraphic();
}
