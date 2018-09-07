package main.mapComponents;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import main.mapComponents.movingParts.Ghost;
import main.mapComponents.movingParts.MovingEntity;
import main.mapComponents.movingParts.PacMan;

/**
 * Le foreground du jeu
 * Ceci est comprise de tout les movingEntity (PacMan, et ghost(si je ajoute plsu tard))
 */
public class MapForeground extends Group {

    public PacMan pacMan;
    public Ghost ghostBlue;
    public Ghost ghostRed;
    public Ghost ghostOrange;
    public Ghost ghostPink;

    public MovingEntity [] entitys;

    public MapForeground() {
        createForeground();
    }

    //originalement pour la redimensionalisation
//    public void resizeForeground(double initBlockSize) {
//        pacMan.resize(initBlockSize);
//    }

    /**
     * crée le foreground
     */
    private void createForeground() {

        this.pacMan = new PacMan(new Coordinate(10, 15));

        this.ghostRed = new Ghost(new Coordinate(10, 7), Color.RED);
        this.ghostBlue = new Ghost(new Coordinate(10, 9), Color.SKYBLUE);
        this.ghostPink = new Ghost(new Coordinate(9, 9), Color.PINK);
        this.ghostOrange = new Ghost(new Coordinate(11, 9), Color.ORANGE);

        entitys = new MovingEntity[] {
                pacMan,
                ghostRed,
                ghostBlue,
                ghostOrange,
                ghostPink
        };
        this.getChildren().addAll(entitys);
    }
}
