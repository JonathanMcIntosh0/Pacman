package testing;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.mapComponents.Coordinate;
import main.mapComponents.MapBackground;
import main.mapGenerator.MapDatabase;
import main.movingParts.Direction;
import main.movingParts.PacMan;

public class TestApp extends Application {

    double blockSize = 50;
    private Group root;
    private static PacMan pacMan;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Testing");
        pacMan = new PacMan(50, new Coordinate(0, 0));

        root = new Group(pacMan);

        Scene scene = new Scene(
                root,
                blockSize,
                blockSize,
                Color.GREY
        );

        scene.setOnKeyPressed(event -> keyPressed(event));

        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private static void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case RIGHT:
                pacMan.setDirection(Direction.RIGHT);
                break;
            case DOWN:
                pacMan.setDirection(Direction.DOWN);
                break;
            case LEFT:
                pacMan.setDirection(Direction.LEFT);
                break;
            case UP:
                pacMan.setDirection(Direction.UP);
                break;
        }
    }

    //https://stackoverflow.com/questions/29962395/how-to-write-a-keylistener-for-javafx
}
