package main.viewsAndModels.game;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import main.mapComponents.MapBackground;
import main.mapComponents.MapForeground;
import main.mapComponents.MapMiddleground;

public class GameWindow extends Stage {

    private final double gameWidth;

    public final MapMiddleground middleground;
    public final MapForeground foreground;

    private Text txtPoints;
    private Text txtTime;

    public GameWindow(int [][] mapLayout) {
        this.gameWidth = mapLayout[0].length * MapDatabase.INIT_MAP_BLOCK_SIZE;
        double gameHeight = mapLayout.length * MapDatabase.INIT_MAP_BLOCK_SIZE;

        this.setTitle("Game Window");

        MapBackground background = new MapBackground(mapLayout);
        middleground = new MapMiddleground(mapLayout);
        foreground = new MapForeground();

        Parent root = new Group(background, middleground, foreground, createDisplay());

        Scene scene = new Scene(
                root,
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

    public double getGameWidth() {
        return gameWidth;
    }

    private VBox createDisplay() {
        VBox display = new VBox(20);

        Text title = new Text("PAC-MAN");
        title.setFill(Color.YELLOW);
        title.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 25));
        title.setTextAlignment(TextAlignment.CENTER);

        Text pointTitle = new Text("Pointage :");
        pointTitle.setFont(Font.font(null, FontWeight.BOLD, 15));
        pointTitle.setUnderline(true);

        txtPoints = new Text("0");
        txtPoints.setTextAlignment(TextAlignment.CENTER);

        Text timeTitle = new Text("Temps :");
        timeTitle.setFont(Font.font(null, FontWeight.BOLD, 15));
        timeTitle.setUnderline(true);

        txtTime = new Text("00 : 00");
        txtTime.setTextAlignment(TextAlignment.CENTER);

        display.getChildren().addAll(title, pointTitle, txtPoints, timeTitle, txtTime);

        display.setPadding(new Insets(10));

        display.setLayoutX(gameWidth);
        return display;
    }

    public int getPoints() {
        return Integer.parseInt(txtPoints.getText());
    }

    public void setPoints(int points) {
        txtPoints.setText(Integer.toString(points));
    }

    public void setTime(long minute, long second) {
        txtTime.setText(String.format("%02d : %02d", minute, second));
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

