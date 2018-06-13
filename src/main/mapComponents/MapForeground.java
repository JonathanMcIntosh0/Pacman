package main.mapComponents;

import javafx.scene.Group;
import main.mapComponents.movingParts.PacMan;

/**
 * Le foreground du jeu
 * Ceci est comprise de tout les movingEntity (PacMan, et ghost(si je ajoute plsu tard))
 */
public class MapForeground extends Group {

    public PacMan pacMan;

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

        this.getChildren().add(pacMan);
    }
}
