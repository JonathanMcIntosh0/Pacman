package main.mapComponents;

import javafx.scene.Group;
import javafx.scene.Node;
import main.mapComponents.items.ItemBlock;
import main.mapComponents.items.Coin;
import main.mapComponents.items.PowerUp;
import main.viewsAndModels.game.MapDatabase;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Le middleground du map
 * Ceci est comprise de tout les ItemBlock (Powerup, coins et Extra points(si je veut l'agouter))
 */
public class MapMiddleground extends Group {
    private final Collection<Node> items = new ArrayList<>(); // la liste de tous les Itemblock

    private final int numBlockWidth; // le nombre de case dans la largeur
    private final int numBlockHeight; // le nombre de case dans la hauteur

    public MapMiddleground(int[][] maplayout) {
        this.numBlockWidth = maplayout[0].length;
        this.numBlockHeight = maplayout.length;

        createMiddleground(maplayout);
    }

    //Originalement pour la redimensionnalisement
//    public void resizeMiddleground(double blockSize) {
//        for (Node nodeBlock : this.getChildren()) {
//            ItemBlock item = (ItemBlock) nodeBlock;
//            item.resizeItem(blockSize);
//        }
//    }

    /**
     * Crée le middleground en utilisant le mapLayout
     * @param mapLayout le layout du map voir MapDatabase pour plus d'info
     */
    private void createMiddleground(int[][] mapLayout) {
        for (int x = 0; x < numBlockWidth; x++) {
            for (int y = 0; y < numBlockHeight; y++) {
                switch (mapLayout[y][x]) {
                    case 2:
                        items.add(new Coin(new Coordinate(x, y), MapDatabase.INIT_MAP_BLOCK_SIZE));
                        break;
                    case 3:
                        items.add(new PowerUp(new Coordinate(x, y), MapDatabase.INIT_MAP_BLOCK_SIZE));
                        break;
                }
            }
        }

        this.getChildren().addAll(items);
    }

    /**
     * get ItemBlock au blockCoord donné
     * @param blockCoord la cordonnée du case en question
     * @return Item au blockCoord ou null si n'existe pas
     */
    public ItemBlock getItem(Coordinate blockCoord) {
        for (Node nodeBlock : this.getChildren()) {
            ItemBlock item = (ItemBlock) nodeBlock;
            if (item.blockCoord.isEqual(blockCoord)) return item;
        }
        return null;
    }
}
