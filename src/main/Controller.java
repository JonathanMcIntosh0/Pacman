package main;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import main.mapComponents.Coordinate;
import main.mapGenerator.GameWindow;
import main.mapGenerator.MapDatabase;
import main.movingParts.Direction;

public class Controller {
    //Interfaces(View)
    GameWindow gameWindow;
    //Model
    MapDatabase database;

    // Threads / timers
    Thread pacmanThread;

    public Controller(GameWindow gameWindow, MapDatabase dataBase) {
        this.gameWindow = gameWindow;
        this.database = dataBase;

        pacmanThread = new Thread(() -> {
            gameWindow.foreground.pacMan.canMove = true;
            do {
                Coordinate nextBlockCoord = getNextBlockCoord(
                        gameWindow.foreground.pacMan.getDirection(),
                        gameWindow.foreground.pacMan.getBlockCoord()
                );

                int nextBlockValue = getBlockValue(nextBlockCoord);

                switch (nextBlockValue) {
                    case 0: // wall
                    case 4: // ghostWall
                        gameWindow.foreground.pacMan.canMove = false;
                        break;
                    case -1: // edge

                    default: // anything else
                        gameWindow.foreground.pacMan.move(nextBlockCoord);
                }
            } while (gameWindow.foreground.pacMan.canMove);
        });
    }

    public void runApplication() {
        runGameWindow();
    }

    public void runGameWindow() {
        gameWindow.getScene().setOnKeyPressed(this::keyPressed);
        gameWindow.show();
    }

    private void keyPressed(KeyEvent event) {
        int newDirection = -1;
        switch (event.getCode()) {
            case RIGHT:
                newDirection = Direction.RIGHT;
                break;
            case DOWN:
                newDirection = Direction.DOWN;
                break;
            case LEFT:
                newDirection = Direction.LEFT;
                break;
            case UP:
                newDirection = Direction.UP;
        }

        if (gameWindow.foreground.pacMan.getDirection() != newDirection && newDirection != -1) {
            //If same direction do nothing else calculate next block pos or have it calculate when it's at an intersection
            int newBlockValue = getBlockValue(getNextBlockCoord(newDirection, gameWindow.foreground.pacMan.getBlockCoord()));
            if (newBlockValue != 0 && newBlockValue != 4) {
                gameWindow.foreground.pacMan.setDirection(newDirection);
                movePacman();
            }
        }
    }

    private void movePacman() {
        if (!pacmanThread.isAlive()) {
            pacmanThread.run();
        }
    }

    public static Coordinate getNextBlockCoord(int direction, Coordinate currentBlockCoord) {
        int dy = 0;
        int dx = 0;
        switch (direction) {
            case Direction.RIGHT:
                dx++;
                break;
            case Direction.LEFT:
                dx--;
                break;
            case Direction.DOWN:
                dy++;
                break;
            case Direction.UP:
                dy--;
        }

        return new Coordinate(currentBlockCoord.getX() + dx, currentBlockCoord.getY() + dy);
    }

    private int getBlockValue(Coordinate blockCoord) {
        try {
            return database.mapLayout[(int) blockCoord.getY()][(int) blockCoord.getX()];
        } catch (ArrayIndexOutOfBoundsException e) {
            return -1;
        }
    }
}
