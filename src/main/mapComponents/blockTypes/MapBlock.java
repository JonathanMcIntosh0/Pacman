package main.mapComponents.blockTypes;

import javafx.scene.Group;
import javafx.scene.shape.Shape;
import main.mapComponents.Coordinate;

import java.util.ArrayList;
import java.util.List;

abstract class MapBlock extends Group{
    private final Coordinate blockCoord;
    final List<Shape> shapes = new ArrayList<>(); // Groupement des formes qui font le graphique

    /**
     * MapBlock c'est Un block du background
     * @param blockCoord coordonée du block dans le mapLayout
     * @param initBlockSize largeur initial
     */
    MapBlock(Coordinate blockCoord, double initBlockSize) {
        this.blockCoord = blockCoord;
        resizeBlock(initBlockSize);
    }

    /**
     * Originalement pour redimensionalser
     * @param newBlockSize nouveau largeur
     */
    private void resizeBlock(double newBlockSize) {
        this.shapes.clear();
        setCoords(newBlockSize);
        setGraphic(newBlockSize);
        this.getChildren().setAll(shapes);
    }

    /**
     * Position les coordonées du block
     * @param blockSize largeur actuel
     */
    private void setCoords(double blockSize) {
        this.setLayoutX(blockCoord.getX() * blockSize);
        this.setLayoutY(blockCoord.getY() * blockSize);
    }

    /**
     * abstract methode pour set le graphique du MapBlock
     * @param blockSize largeur que le graphique doit remplir
     */
    protected abstract void setGraphic(double blockSize);
}
