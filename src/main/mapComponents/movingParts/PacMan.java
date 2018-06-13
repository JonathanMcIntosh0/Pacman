package main.mapComponents.movingParts;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import main.mapComponents.Coordinate;

/**
 * PacMan le Entity que control le joue
 * Voir MovingEntity pour plus d'info sur les methodes
 */
public class PacMan extends MovingEntity {
    private static final double INIT_SPEED_OF_MOVE = 3.5; // la vitesse de déplacement initial en case par seconde
    private static final double POWERED_SPEED = 5.0; // la vitesse de déplacement après qu'il mange un powerup
    private static final int SPEED_OF_ANIMATION = 10; // la vitesse que l'animation change d'état
    private static final double RADIUS = SIZE / 2; //pixels / block

    private boolean isOpening = true; //si la bouche est en train de s'ouvrire ou de fermer
    public boolean canMove = false; // si le Pacman peut bouger
    public boolean isPoweredUp = false; // si le PacMan à manger un powerup et c'est en effet

    private Paint color = Color.YELLOW; // la couleur du PacMan

    public PacMan(Coordinate initBlockCoord) {
        super(initBlockCoord, INIT_SPEED_OF_MOVE);

        //la timer qui anime le Pacman
        animTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //si peut bouger j'anime
                if (canMove) {
                    //si isOpening état + si faux état -
                    changeState(isOpening ? animState + SPEED_OF_ANIMATION : animState - SPEED_OF_ANIMATION);
                    updateGraphic();
                }
            }
        };
    }

    // Originalement pour redimensionaliser
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

    /**
     * change le pacman de l'état powered a normale ou vice versa
     * @param isPoweredUp si true set Powered
     */
    public void setPowered(boolean isPoweredUp) {
        this.isPoweredUp = isPoweredUp;
        speedOfMove = isPoweredUp ? POWERED_SPEED : INIT_SPEED_OF_MOVE;
        color = isPoweredUp ? Color.DEEPSKYBLUE : Color.YELLOW;
    }

    //Animation

    /**
     * change l'état de l'animation
     * @param newState la prochaine état d'animation
     */
    private void changeState(double newState) {
        animState = newState;
        // l'état reste entre 90 et 1
        if (newState > 90 - SPEED_OF_ANIMATION) isOpening = false;
        if (newState < 1 + SPEED_OF_ANIMATION) isOpening = true;
    }

    //Updaters

    @Override
    protected void updateGraphic() {
        this.getChildren().clear();
        Arc pacManShape = new Arc(
                RADIUS, //coordoné x du centre dans la paneau
                RADIUS, //coordoné y du centre dans la paneau
                RADIUS, //rayon x
                RADIUS, //rayon y
                currentDirection * 90 + animState / 2, //l'angle de commencent de l'arc
                360 - animState // la longeur du circonférence qui est rempli à partir du angle de commencement
        );
        pacManShape.setType(ArcType.ROUND);
        pacManShape.setFill(color);
        this.getChildren().add(pacManShape);
    }
}
