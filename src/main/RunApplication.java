package main;

import javafx.application.Application;
import javafx.stage.Stage;
import main.mapGenerator.GameWindow;
import main.mapGenerator.MapDatabase;

public class RunApplication extends Application {

    private static MapDatabase database;
    private static Controller controller;

    public static void main(String[] args) {
        database = new MapDatabase();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameWindow gameWindow = new GameWindow(MapDatabase.PRESET_MAP_LAYOUT);

        controller = new Controller(gameWindow, database);
        controller.runApplication();
    }
}
