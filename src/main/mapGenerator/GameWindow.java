package main.mapGenerator;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.mapComponents.MapBackground;
import main.mapComponents.MapForeground;
import main.movingParts.Direction;

import java.awt.*;

public class GameWindow extends Stage {
    private final int [][] mapLayout;

    private double blockSize;
    private double gameWidth;
    private double gameHeight;

    public final MapBackground background;
    public final MapForeground foreground;

    public GameWindow(double initBlockSize, int [][] mapLayout) {
        this.blockSize = initBlockSize;
        this.mapLayout = mapLayout;
        this.gameWidth = mapLayout[0].length * blockSize;
        this.gameHeight = mapLayout.length * blockSize;

        this.setTitle("Game Window");

        background = new MapBackground(blockSize, mapLayout);
        foreground = new MapForeground(blockSize, gameWidth, gameHeight);

        Parent root = new Group(background, foreground);

        Scene scene = new Scene(
                root,
                gameWidth,
                gameHeight,
                Color.GREY
        );
        this.setScene(scene);

        this.getScene().widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            double height = scene.getHeight();
            resizeGame(width, height, true);
        });
        this.getScene().heightProperty().addListener((observable, oldValue, newValue) -> {
            double width = scene.getWidth();
            double height = newValue.doubleValue();
            resizeGame(width, height, false);
        });
    }

    private void resizeGame(double newWidth, double newHeight, boolean widthChanged) {
        if (gameHeight > newHeight) {
            blockSize = newHeight / background.numBlockHeight;
        } else if (gameWidth > newWidth) {
            blockSize = newWidth / background.numBlockWidth;
        } else if (gameHeight < newHeight && gameWidth < newWidth) {
            blockSize = (widthChanged) ? (newWidth / background.numBlockWidth) : (newHeight / background.numBlockHeight);
        } else {
            return;
        }

        gameWidth = newWidth;
        gameHeight = newHeight;

        background.resizeBackground(blockSize);
        foreground.resizeForeground(blockSize, gameWidth, gameHeight);
    }
}

