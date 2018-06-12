package main.mapComponents.items;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.mapComponents.Coordinate;

/**
 * argent qui donne des points
 * voir ItemBlock pour plus d'info sur les methodes
 */
public class Coin extends ItemBlock {
    public static final int POINT_TOTAL = 100; // pointage que vaut 1 coin
    private static final double COIN_BLOCK_RATIO = 0.25; // rapport entre le diametre du coin et le blocksize

    public Coin(Coordinate blockCoord, double blockSize) {
        super(blockCoord, blockSize);
    }

    @Override
    protected void setGraphic(double blockSize) {
        Circle coinShape = new Circle(
                blockSize / 2,
                blockSize / 2,
                blockSize * COIN_BLOCK_RATIO / 2,
                Color.GOLD
        );
        shapes.add(coinShape);
    }
}
