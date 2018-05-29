package main.mapGenerator;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.mapComponents.MapBackground;
import main.mapComponents.MapForeground;

import java.awt.*;

public class GameWindow extends Stage {
    private double blockSize;
    MapBackground background;
    MapForeground foreground;

    public GameWindow(double initBlockSize, int [][] mapLayout) {
        this.blockSize = initBlockSize;

        this.setTitle("Game Window");

        background = new MapBackground(blockSize, mapLayout);
        foreground = new MapForeground(blockSize);

        Parent root = new Group(background, foreground);

        Scene scene = new Scene(
                root,
                background.numBlockWidth * blockSize,
                background.numBlockHeight * blockSize,
                Color.GREY
        );

        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            double width = newValue.doubleValue();
            double height = scene.getHeight();
            resizeGame(width, height, true);
        });
        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            double width = scene.getWidth();
            double height = newValue.doubleValue();
            resizeGame(width, height, false);
        });

        this.setScene(scene);
    }

    private void resizeGame(double width, double height, boolean widthChanged) {
        double prevPrefHeight = background.numBlockHeight * blockSize;
        double prevPrefWidth = background.numBlockWidth * blockSize;
        if (prevPrefHeight > height) {
            blockSize = height / background.numBlockHeight;
        } else if (prevPrefWidth > width) {
            blockSize = width / background.numBlockWidth;
        } else if (prevPrefHeight < height && prevPrefWidth < width) {
            blockSize = (widthChanged) ? (width / background.numBlockWidth) : (height / background.numBlockHeight);
        } else {
            return;
        }

        background.resizeBackground(blockSize);
        foreground.resizeForeground(blockSize);
    }

    private static Node createTopMenu() {
        return new HBox();
    }
}
