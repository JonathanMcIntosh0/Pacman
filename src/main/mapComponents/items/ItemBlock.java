package main.mapComponents.items;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import main.mapComponents.Coordinate;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemBlock extends Pane{

    public final Coordinate blockCoord;
    final List<Shape> shapes = new ArrayList<>(); // Groupement des formes qui font le graphique

    public boolean isActive; //si c'est utiliser ou non

    /**
     * ItemBlock c'est un item que PacMan peut manger
     * @param blockCoord coordonée du item dans le mapLayout
     * @param blockSize largeur d'un case du jeu
     */
    ItemBlock(Coordinate blockCoord, int blockSize) {
        this.blockCoord = blockCoord;
        this.resize(blockSize, blockSize);
        setCoords(blockSize);
        setGraphic(blockSize);
        showItem();
    }

    /**
     * montre le item
     */
    private void showItem() {
        this.getChildren().setAll(shapes);
        isActive = true;
    }

    /**
     * cache le item
     */
    public void hideItem() {
        this.getChildren().clear();
        isActive = false;
    }

    /**
     * Position les coordonées du Item
     * @param blockSize largeur actuel d'un case
     */
    private void setCoords(int blockSize) {
        this.setLayoutX(blockCoord.getX() * blockSize);
        this.setLayoutY(blockCoord.getY() * blockSize);
    }

    /**
     * abstract methode pour set le graphique du ItemBlock
     * @param blockSize largeur que le graphique doit remplir
     */
    protected abstract void setGraphic(int blockSize);
}
