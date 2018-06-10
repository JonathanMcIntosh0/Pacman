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
    AnimationTimer pacManMoveTimer;

    //Times
    Long previousTime;
    double dx = 0;
    double dy = 0;

    public Controller(GameWindow gameWindow, MapDatabase dataBase) {
        this.gameWindow = gameWindow;
        this.database = dataBase;

        pacManMoveTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Long currentTime = System.currentTimeMillis();
                Long timeElapsed = currentTime - previousTime;
                previousTime = currentTime;

                double moveAmount = gameWindow.foreground.pacMan.SPEED_OF_MOVE * timeElapsed / MapDatabase.INIT_MAP_BLOCK_SIZE;
                switch (gameWindow.foreground.pacMan.getDirection()) {
                    case Direction.RIGHT:
                        dx += moveAmount;
                        break;
                    case Direction.DOWN:
                        dy += moveAmount;
                        break;
                    case Direction.LEFT:
                        dx -= moveAmount;
                        break;
                    case Direction.UP:
                        dy -= moveAmount;
                }
                if (dx >= 1 || dy >= 1) {
                    gameWindow.foreground.pacMan.translatePos(dx, dy);
                    dx = 0;
                    dy = 0;
                }

            }
        };

        pacmanThread = new Thread(() -> {
            do {
                Coordinate nextBlockCoord = getNextBlockCoord(
                        gameWindow.foreground.pacMan.getDirection(),
                        gameWindow.foreground.pacMan.getBlockCoord()
                );

                int nextBlockValue = dataBase.getBlockValue(nextBlockCoord);

                switch (nextBlockValue) {
                    case 0: // wall
                    case 4: // ghostWall
                        return;
                    case -1: // edge

                    default: // anything else
//                        gameWindow.foreground.pacMan.move(nextBlockCoord);
                }
            } while (true);
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
            int newBlockValue = database.getBlockValue(getNextBlockCoord(newDirection, gameWindow.foreground.pacMan.getBlockCoord()));
            if (newBlockValue != 0 && newBlockValue != 4) {
                gameWindow.foreground.pacMan.setNextDirection(newDirection);
                gameWindow.foreground.pacMan.setDirection();
                movePacman();
            }
        }
    }

    private void movePacman() {
        if (!gameWindow.foreground.pacMan.isMoving) {
            previousTime = System.currentTimeMillis();
            gameWindow.foreground.pacMan.isMoving = true;
            gameWindow.foreground.pacMan.pacManAnimTimer.start();
            pacManMoveTimer.start();
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
}
