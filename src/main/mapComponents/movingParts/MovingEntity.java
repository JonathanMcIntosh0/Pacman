package main.mapComponents.movingParts;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import main.mapComponents.Coordinate;
import main.mapComponents.Direction;
import main.viewsAndModels.game.MapDatabase;

public abstract class MovingEntity extends Pane {
    private final String TAG;
    /*
    speed / 60
    *   60 f / s
    *   speed block/ sec
    *   speed * size / 60 = bl
    *   pix/s / f/s
    *   size = k pixels / block
    *   1 pixel / s = 1/60 pixel / f
    */
    static final int SIZE = MapDatabase.INIT_MAP_BLOCK_SIZE; // La grandeur du Entity

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
    public float dx = 0; //translation total sur les x
    public float dy = 0; //translation total sur les y

    /**
     * Un mouving entity c'est un pièce dans le jeu qui bouge
     * Peut être controller soit par un joueur ou par l'ordi
     * @param initBlockCoord Coordonée par case initial du Entity
     * @param initSpeedOfMove vitesse de déplacement initial
     */
    MovingEntity(Coordinate initBlockCoord, double initSpeedOfMove, String TAG) {
        this.TAG = TAG;

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
        int intDX = Math.round(dx);
        int intDY = Math.round(dy);
        if (Math.abs(intDX) >= 1 || Math.abs(intDY) >= 1) { // si déplacement total est 1 pixel ou plus bouge le pacman
            coordinate.translateX(intDX);
            coordinate.translateY(intDY);

            updateBlockCoord(intDX, intDY);

            dx = 0;
            dy = 0;
        }
    }

    /**
     * updates block coord
     */
    private void updateBlockCoord(int intDX, int intDY) {
        //get nouveau BlockCoord
        int newX = coordinate.getX() / SIZE;
        int newY = coordinate.getY() / SIZE;

        blockCoord.setX(newX);
        blockCoord.setY(newY);

        int intersecX = newX * SIZE; //La coordoné x du millieu du case qui se situe l'entity
        int intersecY = newY * SIZE; //La coordoné y du millieu du case qui se situe l'entity
        int errorX = intDX / 2;
        int errorY = intDY / 2;// une erreur pixel pour l'empêcher de le manquer // speedOfMove * SIZE / 60
        atIntersecton =
                (intersecX + errorX >= coordinate.getX() && intersecX - errorX <= coordinate.getX())
                        && (intersecY + errorY >= coordinate.getY() && intersecY - errorY <= coordinate.getY());
        System.out.println(this.toString());
        updatePos();
    }

    /**
     * set la graphique de Entity au coordonné
     */
    private void updatePos() {
        this.setLayoutX(coordinate.getX());
        this.setLayoutY(coordinate.getY());
    }

    @Override
    public String toString() {
        return TAG + ", Coord : " + coordinate.toString() + ", BC : " + blockCoord.toString() + ", Inter :" + atIntersecton;
    }

    /**
     * abstract methode pour set le graphique du ItemBlock
     */
    protected abstract void updateGraphic();
}
