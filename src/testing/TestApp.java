package testing;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.mapComponents.MapBackground;
import main.mapGenerator.MapDatabase;
import main.movingParts.PacMan;

public class TestApp extends Application {

    double blockSize = 50;
    private Group root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Testing");

        root = new Group(new PacMan(50));

        Scene scene = new Scene(
                root,
                blockSize,
                blockSize,
                Color.GREY
        );

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
