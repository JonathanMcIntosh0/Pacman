package main.mapComponents.items;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.mapComponents.Coordinate;

/**
 * powerUp qui donne un "speed Boost" pour un certain temps
 * voir ItemBlock pour plus d'info sur les methodes
 */
public class PowerUp extends ItemBlock {
    public static final int POWERUP_TIME = 2; // temps en secondes que dure le powerup
    private static final double POWERUP_BLOCK_RATIO = 0.5; // rapport entre le diametre du powerup et le blocksize

    public PowerUp(Coordinate blockCoord, double blockSize) {
        super(blockCoord, blockSize);
    }

    @Override
    protected void setGraphic(double blockSize) {
        Circle powerUpShape = new Circle(
                blockSize / 2,
                blockSize / 2,
                blockSize * POWERUP_BLOCK_RATIO / 2,
                Color.GREEN
        );
        shapes.add(powerUpShape);
    }
}
