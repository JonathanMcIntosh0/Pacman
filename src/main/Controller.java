package main;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import main.mapComponents.Coordinate;
import main.mapComponents.items.Coin;
import main.mapComponents.items.ItemBlock;
import main.mapComponents.items.PowerUp;
import main.scoreBoard.Score;
import main.viewsAndModels.game.GameWindow;
import main.viewsAndModels.game.MapDatabase;
import main.mapComponents.Direction;
import main.viewsAndModels.menus.EndMenu;
import main.viewsAndModels.menus.StartMenu;

class Controller {
    //Interfaces(View)
    private StartMenu startMenu;
    private GameWindow gameWindow;
    private EndMenu endMenu;
    //Model
    private final MapDatabase database;

    // Threads / timers
    private final AnimationTimer moveTimer;

    //Times for game
    private final int timeForComplete = 30;

    private Long startTime;
    private Long totalElapsedTime;
    private Long powerUpStartTime;
    private Long elapsedPowerUpTIme;
    private Long previousTime;

    //variable qui aide avec mouvement du PacMan
    private double dx = 0; //translation total sur les x
    private double dy = 0; //translation total sur les y

    public Controller(MapDatabase dataBase) {
        this.database = dataBase;

        //gamewindow setup
        moveTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Long currentTime = System.currentTimeMillis();
                //times for dissplay
                totalElapsedTime = currentTime - startTime;
                Long elapsedSecs = timeForComplete - totalElapsedTime / 1000;
                gameWindow.setTime(
                        elapsedSecs / 60,
                        elapsedSecs % 60
                );

                if (gameWindow.foreground.pacMan.isPoweredUp) {
                    elapsedPowerUpTIme = (currentTime - powerUpStartTime) / 1000;
                    if (elapsedPowerUpTIme >= 2) {
                        powerUpStartTime = null;
                        gameWindow.foreground.pacMan.setPowered(false);
                    }
                }

                if (elapsedSecs <= 0) {
                    endGame();
                }
                //times for moving
                Long timeElapsed = currentTime - previousTime;
                previousTime = currentTime;

                //if pacman is at intersec
                if (gameWindow.foreground.pacMan.atIntersecton) {
                    pacmanIntersecUpdate();
                }

                if (gameWindow.foreground.pacMan.canMove) {

                    double moveAmount = gameWindow.foreground.pacMan.speedOfMove / 1000 * timeElapsed * MapDatabase.INIT_MAP_BLOCK_SIZE;
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
                    if (Math.abs(dx) >= 1 || Math.abs(dy) >= 1) {
                        gameWindow.foreground.pacMan.translatePos(dx, dy);
                        dx = 0;
                        dy = 0;
                    }
                }
            }
        };
    }

    public void runApplication() {
        runStartMenu();
    }

    private void runStartMenu() {
        startMenu = new StartMenu();
        startMenu.getBtnStart().setOnAction(event -> {
            String name = startMenu.getName();
            if (name.length() > 10 || name.length() == 0) {
                startMenu.setRedMsg();
                return;
            }
            database.setPlayerName(name);
            runGameWindow();
        });
        startMenu.show();
    }

    private void runGameWindow() {
        gameWindow = new GameWindow(database.mapLayout);
        gameWindow.getScene().setOnKeyPressed(this::keyPressed);
        gameWindow.show();
        if (startMenu.isShowing()) startMenu.close();
        if (endMenu != null && endMenu.isShowing()) endMenu.close();
    }

    private void runEndMenu() {
        endMenu = new EndMenu(new Score(database.getPlayerName(), gameWindow.getPoints()));
        endMenu.getBtnRestart().setOnAction(event -> runGameWindow());
        endMenu.show();
        gameWindow.close();
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

        if (newDirection != -1) {
            if (gameWindow.foreground.pacMan.getDirection() == -1) {
                startGame();
            }
            gameWindow.foreground.pacMan.setNextDirection(newDirection);
            if (!gameWindow.foreground.pacMan.canMove) {
                movePacman();
            }
        }
    }

    private void startGame() {
        gameWindow.foreground.pacMan.animTimer.start();
        moveTimer.start();

        startTime = System.currentTimeMillis();
        previousTime = startTime;
    }

    private void endGame() {
        gameWindow.foreground.pacMan.animTimer.stop();
        moveTimer.stop();

        runEndMenu();
    }

    private void movePacman() {
        gameWindow.foreground.pacMan.canMove = true;
    }

    private void pacmanIntersecUpdate() {
        Coordinate nextBlockCoord = getNextBlockCoord(
                gameWindow.foreground.pacMan.getNextDirection(),
                gameWindow.foreground.pacMan.getBlockCoord()
        );

        //check if nextdirection gives possible nextblock
        if (isNextBlockPossible(nextBlockCoord)) {
            //set current direction to nextdirection
            gameWindow.foreground.pacMan.setDirection();
        } else {
            nextBlockCoord = getNextBlockCoord(
                    gameWindow.foreground.pacMan.getDirection(),
                    gameWindow.foreground.pacMan.getBlockCoord()
            );

            //check if currentdirection does not give possible nextblock
            if (!isNextBlockPossible(nextBlockCoord)) {
                //stop moving
                gameWindow.foreground.pacMan.canMove = false;
                return;
            }
        }

        //if pacman is at edge
        if (database.getBlockValue(nextBlockCoord) == -1) {
            switch (gameWindow.foreground.pacMan.getDirection()) { //laisser switch si tu utilise un map avec des edge en haut ou en bas
                case Direction.RIGHT:
                    gameWindow.foreground.pacMan.translatePos(database.blockSize - gameWindow.getGameWidth(), 0);
                    break;
                case Direction.LEFT:
                    gameWindow.foreground.pacMan.translatePos(gameWindow.getGameWidth() - database.blockSize, 0);
            }
        }

        //updates between pacman and items
        ItemBlock currentItem = gameWindow.middleground.getItem(gameWindow.foreground.pacMan.getBlockCoord());
        if (currentItem != null && currentItem.isActive) {
            currentItem.hideItem();
            if (currentItem instanceof Coin) {
                gameWindow.setPoints(gameWindow.getPoints() + Coin.POINT_TOTAL);
            } else if (currentItem instanceof PowerUp) {
                powerUpStartTime = System.currentTimeMillis();
                gameWindow.foreground.pacMan.setPowered(true);
            }
        }
    }

    private static Coordinate getNextBlockCoord(int direction, Coordinate currentBlockCoord) {
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

    private boolean isNextBlockPossible(Coordinate nextBlockCoord) {
        int nextBlockValue = database.getBlockValue(nextBlockCoord);

        switch (nextBlockValue) {
            case 0: // wall
            case 4: // ghostWall
                return false;
            default: // anything else
                return true;
        }
    }
}
