package main;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import main.mapComponents.Coordinate;
import main.mapComponents.items.Coin;
import main.mapComponents.items.ItemBlock;
import main.mapComponents.items.PowerUp;
import main.mapComponents.movingParts.Ghost;
import main.mapComponents.movingParts.MovingEntity;
import main.mapComponents.movingParts.PacMan;
import main.scoreBoard.Score;
import main.viewsAndModels.game.GameWindow;
import main.viewsAndModels.game.MapDatabase;
import main.mapComponents.Direction;
import main.viewsAndModels.menus.EndMenu;
import main.viewsAndModels.menus.StartMenu;

/**
 * Le grand controlleur de tout
 */
class Controller {
    //Interfaces(View)
    private StartMenu startMenu;
    private GameWindow gameWindow;
    private EndMenu endMenu;
    //Model
    private final MapDatabase database;

    // Threads / timers
    private final AnimationTimer moveTimer; // Timer qui loop constament pour update le jeux à 60 fps

    //Times for game
    private final int timeForComplete = 90; // temps avant la fin du jeu en secondes

    private Long startTime; // Time du commencement du jeu
    private Long totalElapsedTime; //temps écoulé après le début
    private Long powerUpStartTime; // temps exact que pacman mange un powerUp
    private Long elapsedPowerUpTIme; // temps écoulé après que Pacman mage un powerup
    private Long previousTime; // le temps précèdent que le timer à update

    public Controller(MapDatabase dataBase) {
        this.database = dataBase;

        moveTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Long currentTime = System.currentTimeMillis(); // temps actuel en ms
                //times for display clock
                totalElapsedTime = currentTime - startTime;
                Long elapsedSecs = timeForComplete - totalElapsedTime / 1000;
                gameWindow.setTime(
                        elapsedSecs / 60,
                        elapsedSecs % 60
                );

                if (gameWindow.foreground.pacMan.isPoweredUp) { //si pacman est powered
                    elapsedPowerUpTIme = (currentTime - powerUpStartTime) / 1000;
                    if (elapsedPowerUpTIme >= PowerUp.POWERUP_TIME) { // si temps total pour powerup est fini
                        powerUpStartTime = null;
                        gameWindow.foreground.pacMan.setPowered(false);
                    }
                }

                if (elapsedSecs <= 0) { //si il reste 0 sec arrête le jeu
                    endGame();
                }
                //times for moving
                Long timeElapsed = currentTime - previousTime; // temps écoulé entre updates
                previousTime = currentTime;

                for (int i = 0; i < gameWindow.foreground.entitys.length; i++) {
                    MovingEntity entity = gameWindow.foreground.entitys[i];
                    if (entity.atIntersecton) {
                        if (i == 0) pacmanIntersecUpdate((PacMan) entity);
                        else ghostIntersecUpdate((Ghost) entity);
                        //Si entity est sur la bordure
                        if (database.getBlockValue(getNextBlockCoord(
                                entity.getDirection(),
                                entity.getBlockCoord()
                        )) == -1) {
                            switch (entity.getDirection()) { //laisser switch et non if car si tu utilise un map avec des edge en haut ou en bas
                                case Direction.RIGHT:
                                    entity.dx += database.blockSize - gameWindow.getGameWidth();
                                    break;
                                case Direction.LEFT:
                                    entity.dx += gameWindow.getGameWidth() - database.blockSize;
                            }
                        }
                    }

                    if (i != 0 || gameWindow.foreground.pacMan.canMove) {
                        moveEntity(timeElapsed, entity);
                    }
                }
            }
        };
    }

    void runApplication() {
        runStartMenu();
    }

    /**
     * run startMenu
     */
    private void runStartMenu() {
        startMenu = new StartMenu();
        startMenu.getBtnStart().setOnAction(event -> {
            String name = startMenu.getName();
            if (name.length() > 10 || name.length() == 0) { // si nom n'est pas entre 1 et 10 char
                startMenu.setRedMsg();
                return;
            }
            database.setPlayerName(name);
            runGameWindow();
        });
        startMenu.show();
    }

    /**
     * run le jeu window
     */
    private void runGameWindow() {
        gameWindow = new GameWindow(database.mapLayout);
        gameWindow.getScene().setOnKeyPressed(this::keyPressed);
        gameWindow.show();
        if (startMenu.isShowing()) startMenu.close();
        if (endMenu != null && endMenu.isShowing()) endMenu.close();
    }

    /**
     * run EndMenu
     */
    private void runEndMenu() {
        endMenu = new EndMenu(new Score(database.getPlayerName(), gameWindow.getPoints()));
        endMenu.getBtnRestart().setOnAction(event -> runGameWindow());
        endMenu.show();
        gameWindow.close();
    }

    /**
     * Si keyPressed
     * @param event
     */
    private void keyPressed(KeyEvent event) {
        int newDirection = Direction.NULL;
        switch (event.getCode()) {
            case D:
            case RIGHT:
                newDirection = Direction.RIGHT;
                break;
            case S:
            case DOWN:
                newDirection = Direction.DOWN;
                break;
            case A:
            case LEFT:
                newDirection = Direction.LEFT;
                break;
            case W:
            case UP:
                newDirection = Direction.UP;
        }

        if (newDirection != Direction.NULL) { // Si pas w,a,s,d ou arrowKeys fait rien
            if (gameWindow.foreground.pacMan.getDirection() == Direction.NULL) { //si commencemnt du jeu
                startGame();
            }
            gameWindow.foreground.pacMan.setNextDirection(newDirection);
            if (!gameWindow.foreground.pacMan.canMove) {
                gameWindow.foreground.pacMan.canMove = true;
            }
        }
    }

    /**
     * Commence le jeu
     */
    private void startGame() {
        gameWindow.foreground.pacMan.animTimer.start();
        moveTimer.start();

        startTime = System.currentTimeMillis();
        previousTime = startTime;
    }

    /**
     * Arrête le jeu
     */
    private void endGame() {
        gameWindow.foreground.pacMan.animTimer.stop();
        moveTimer.stop();

        runEndMenu();
    }

    /**
     * si Pacman est au millieu d'un case, update
     */
    private void pacmanIntersecUpdate(PacMan pacMan) {
        //prochaine case si utilise nextDirection
        Coordinate nextBlockCoord = getNextBlockCoord(
                pacMan.getNextDirection(),
                pacMan.getBlockCoord()
        );

        //check if prochainecase gives possible nextblock
        if (isNextBlockPossible(nextBlockCoord, pacMan)) {
            //set current direction to nextdirection
            pacMan.setDirection();
        } else { //si pas possible continue avec currentDirection
            //prochaine case si utilise currentDirection
            nextBlockCoord = getNextBlockCoord(
                    pacMan.getDirection(),
                    pacMan.getBlockCoord()
            );

            //check if prochaine case does not give possible nextblock
            if (!isNextBlockPossible(nextBlockCoord, pacMan)) {
                //arrête de bouger
                pacMan.canMove = false;
                return;
            }
        }

        //updates between pacman and items
        ItemBlock currentItem = gameWindow.middleground.getItem(pacMan.getBlockCoord());
        if (currentItem != null && currentItem.isActive) { // si pacman est sur un ItemBlock active
            currentItem.hideItem();
            if (currentItem instanceof Coin) { //si coin
                gameWindow.setPoints(gameWindow.getPoints() + Coin.POINT_TOTAL);
            } else if (currentItem instanceof PowerUp) { //si powerUp
                powerUpStartTime = System.currentTimeMillis();
                pacMan.setPowered(true);
            }
        }
    }

    private void ghostIntersecUpdate(Ghost ghost) {
        int newDirection = -1;
        int numDirPossible = 0;
        boolean [] dirPosible = new boolean[4];
        //, down, right, up
//
//        int n;
//        if (direction != Direction.NULL) {
//            if (direction )
//            dirPosible[direction + 2 * n] = false;
//        }
        for (int direction = 0; direction < 4; direction++) {
            Coordinate nextBlockCoord = getNextBlockCoord(
                    direction,
                    ghost.getBlockCoord()
            );
            if (isNextBlockPossible(nextBlockCoord, ghost)) {
                dirPosible[direction] = true;
            } else dirPosible[direction] = false;
        }

        switch (ghost.getDirection()) {
            case Direction.RIGHT:
                dirPosible[Direction.LEFT] = false;
                break;
            case Direction.DOWN:
                dirPosible[Direction.UP] = false;
                break;
            case Direction.LEFT:
                dirPosible[Direction.RIGHT] = false;
                break;
            case Direction.UP:
                dirPosible[Direction.DOWN] = false;
        }

        for (int i = 0; i < 4; i++) {
            if (dirPosible[i]) numDirPossible++;
        }

        if (numDirPossible != 0) {
            int rand = (int) (Math.random() * numDirPossible + 1);
            while (rand > 0) {
                newDirection++;
                if (dirPosible[newDirection]) rand--;
            }
        }

        ghost.setNextDirection(newDirection);
        ghost.setDirection();
    }

    private void moveEntity(Long timeElapsed, MovingEntity entity) {
        // moveAmount = déplacement total entre les updates
        double moveAmount = entity.speedOfMove / 1000 * timeElapsed * MapDatabase.INIT_MAP_BLOCK_SIZE;
        switch (entity.getDirection()) {
            case Direction.RIGHT:
                entity.dx += moveAmount;
                break;
            case Direction.DOWN:
                entity.dy += moveAmount;
                break;
            case Direction.LEFT:
                entity.dx -= moveAmount;
                break;
            case Direction.UP:
                entity.dy -= moveAmount;
        }
        if (Math.abs(entity.dx) >= 1 || Math.abs(entity.dy) >= 1) { // si déplacement total est 1 pixel ou plus bouge le pacman
            entity.translatePos();
        }
    }

    /**
     * get blockCoord du prohaine case
     * @param direction direction du prochaine case
     * @param currentBlockCoord coordoné du case actuel
     * @return Coordoné du prochaine case
     */
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

    /**
     * @param nextBlockCoord coord du prochaine case
     * @return si entity peut déplacer au procahine case
     */
    private boolean isNextBlockPossible(Coordinate nextBlockCoord, MovingEntity entity) {
        int nextBlockValue = database.getBlockValue(nextBlockCoord);
        switch (nextBlockValue) {
            case 4: // ghostWall
//                if (entity instanceof Ghost && ((Ghost) entity).currentState == 0) return true;
            case 0: // wall
                return false;
            default: // anything else
                return true;
        }
    }
}
