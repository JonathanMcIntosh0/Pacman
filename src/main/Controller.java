package main;

import main.mapGenerator.GameWindow;
import main.mapGenerator.MapDatabase;

public class Controller {
    //Interfaces(View)
    GameWindow gameWindow;
    //Model
    MapDatabase database;

    public Controller(GameWindow gameWindow, MapDatabase dataBase) {
        this.gameWindow = gameWindow;
        this.database = dataBase;
    }

    public void runApplication() {
        runGameWindow();
    }

    public void runGameWindow() {
        gameWindow.show();
    }
}
