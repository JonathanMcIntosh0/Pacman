package main.mapGenerator;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.mapComponents.MapBackground;
import main.mapComponents.MapForeground;

public class GameWindow extends Stage {
    private final int [][] mapLayout;

    private double gameWidth;
    private double gameHeight;

    public final MapBackground background;
    public final MapForeground foreground;

    public GameWindow(int [][] mapLayout) {
        this.mapLayout = mapLayout;
        this.gameWidth = mapLayout[0].length * MapDatabase.INIT_MAP_BLOCK_SIZE;
        this.gameHeight = mapLayout.length * MapDatabase.INIT_MAP_BLOCK_SIZE;

        this.setTitle("Game Window");

        background = new MapBackground(mapLayout);
        foreground = new MapForeground();

        Parent root = new Group(background, foreground);

        Scene scene = new Scene(
                root,
                gameWidth,
                gameHeight,
                Color.GREY
        );
        this.setScene(scene);

//        this.getScene().widthProperty().addListener((observable, oldValue, newValue) -> {
//            double width = newValue.doubleValue();
//            double height = scene.getHeight();
//            resizeGame(width, height, true);
//        });
//        this.getScene().heightProperty().addListener((observable, oldValue, newValue) -> {
//            double width = scene.getWidth();
//            double height = newValue.doubleValue();
//            resizeGame(width, height, false);
//        });
    }

//    private void resizeGame(double newWidth, double newHeight, boolean widthChanged) {
//        if (gameHeight > newHeight) {
//            initBlockSize = newHeight / background.numBlockHeight;
//        } else if (gameWidth > newWidth) {
//            initBlockSize = newWidth / background.numBlockWidth;
//        } else if (gameHeight < newHeight && gameWidth < newWidth) {
//            initBlockSize = (widthChanged) ? (newWidth / background.numBlockWidth) : (newHeight / background.numBlockHeight);
//        } else {
//            return;
//        }
//
//        gameWidth = newWidth;
//        gameHeight = newHeight;
//
//        background.resizeBackground(initBlockSize);
//        foreground.resizeForeground(initBlockSize);
//    }
}

