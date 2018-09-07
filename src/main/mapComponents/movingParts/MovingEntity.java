package main.mapComponents.movingParts;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import main.mapComponents.Coordinate;
import main.mapComponents.Direction;
import main.viewsAndModels.game.MapDatabase;

public abstract class MovingEntity extends Pane {
    static final double SIZE = MapDatabase.INIT_MAP_BLOCK_SIZE; // La grandeur du Entity

    public double speedOfMove; // La vitesse de deplacement en case par seconde

    public AnimationTimer animTimer; //La timer utiliser pour animer un Entity

    Coordinate blockCoord; //coordonée du case dans le mapLayout que la Entity est dedans
    private Coordinate coordinate; //coordonée du Entity

    int currentDirection = Direction.NULL; //La newDirection de mouvement courrament
    int nextDirection = Direction.NULL; //La prochaine newDirection que l'Entity veut aller
    // 0 = right, 1 = up, 2 = left, 3 = down, -1 = null
    double animState = 0; // état de l'animation

    public boolean atIntersecton = true; // Si le Entity est au millieu d'un case

    //variable qui aide avec mouvement des entitys
    public double dx = 0; //translation total sur les x
    public double dy = 0; //translation total sur les y

    /**
     * Un mouving entity c'est un pièce dans le jeu qui bouge
     * Peut être controller soit par un joueur ou par l'ordi
     * @param initBlockCoord Coordonée par case initial du Entity
     * @param initSpeedOfMove vitesse de déplacement initial
     */
    MovingEntity(Coordinate initBlockCoord, double initSpeedOfMove) {
        this.blockCoord = initBlockCoord;
        this.speedOfMove = initSpeedOfMove;
        this.coordinate = new Coordinate(
                blockCoord.getX() * SIZE,
                blockCoord.getY() * SIZE
        );

        updatePos();
        updateGraphic();
    }

    /**
     * set la prochaine direction
     * @param newDirection
     */
    public void setNextDirection(int newDirection) {
        this.nextDirection = newDirection;
    }

    /**
     * change la direction courrante au prochaine direction
     */
    public void setDirection() {
        currentDirection = nextDirection;
    }

    public Coordinate getBlockCoord() {
        return blockCoord;
    }

    public int getDirection() {
        return currentDirection;
    }

    public int getNextDirection() {
        return nextDirection;
    }

    /**
     * déplace l'Entity de dx et dy pixels
     */
    public void translatePos() {
        setPos(new Coordinate(
                coordinate.getX() + dx,
                coordinate.getY() + dy
        ));
        dx = 0;
        dy = 0;
    }

    /**
     * déplace l'Entity à la Coordoné newCoord
     * @param newCoord nouvelle coordoné
     */
    private void setPos(Coordinate newCoord) {
        coordinate = newCoord;

        //get nouveau BlockCoord
        double newX = coordinate.getX() / SIZE;
        double newY = coordinate.getY() / SIZE;
        blockCoord = new Coordinate(
                newX,
                newY
        );

        int intersecX = (int) Math.round(newX); //La coordoné x du millieu du case qui se situe l'entity
        int intersecY = (int) Math.round(newY); //La coordoné y du millieu du case qui se situe l'entity
        double error = speedOfMove/ 3 / SIZE; // une erreur de 2 pixel pour l'empêcher de le manquer
        atIntersecton = (intersecX + error > newX && intersecX - error < newX) && (intersecY + error > newY && intersecY - error < newY);
        updatePos();
    }

    /**
     * set la graphique de Entity au coordonné
     */
    private void updatePos() {
        this.setLayoutX(coordinate.getX());
        this.setLayoutY(coordinate.getY());
    }

    /**
     * abstract methode pour set le graphique du ItemBlock
     */
    protected abstract void updateGraphic();
}
