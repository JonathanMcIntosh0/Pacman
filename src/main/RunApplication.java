package main;

import javafx.application.Application;
import javafx.stage.Stage;
import main.viewsAndModels.game.MapDatabase;

/**
 * Ce qui run l'application
 */
public class RunApplication extends Application {

    private static MapDatabase database;

    public static void main(String[] args) {
        database = new MapDatabase();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Controller controller = new Controller(database);
        controller.runApplication();
    }
}
