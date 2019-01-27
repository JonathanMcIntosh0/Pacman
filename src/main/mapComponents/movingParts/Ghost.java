package main.mapComponents.movingParts;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import main.mapComponents.Coordinate;

public class Ghost extends MovingEntity{
    //SPEEDS OF MOVE
    private static final double INIT_SPEED_OF_MOVE = 3; // la vitesse de déplacement initial en case par seconde
    private static final double SCARED_SPEED = 2.5; // la vitesse de déplacement après qu'il mange un powerup
    private static final double DEAD_SPEED = 4.5;

    private static final int NORMAL_SPEED_OF_ANIM = 10; // la vitesse que l'animation change d'état
    private static final int SCARED_SPEED_OF_ANIM = 7;

    /*
    States :
        0 = normal
        1 = scared
        2 = dead
        3 = inbase
    */
    int currentState = 3;
    public int difficulty = 5; // distance of sight in blocks

    private static final Paint SCARED_COLOR = Color.BLUEVIOLET;
    private final Paint normalColor;
    private Paint currentColor;

    public Ghost(Coordinate initBlockCoord, Paint normalColor, String TAG) {
        super(initBlockCoord, INIT_SPEED_OF_MOVE, TAG);

        this.normalColor = normalColor;
        currentColor = normalColor;

        updateGraphic();
    }

    public void setCurrentState(int newState) {
        currentState = newState;
    }

    @Override
    protected void updateGraphic() {
        this.getChildren().clear();
        Rectangle ghostShape = new Rectangle(
                SIZE,
                SIZE,
                currentColor
        );
        this.getChildren().add(ghostShape);
    }
}
