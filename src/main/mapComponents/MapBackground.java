package main.mapComponents;

import javafx.scene.Group;
import javafx.scene.Node;
import main.mapComponents.blockTypes.GhostWallBlock;
import main.mapComponents.blockTypes.PathBlock;
import main.mapComponents.blockTypes.WallBlock;
import main.viewsAndModels.game.MapDatabase;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Le background du jeu
 * Ceci est comprise de tout les MapBlock (un Wall, GhostWall et un Path)
 */
public class MapBackground extends Group {
    private final Collection<Node> blocks = new ArrayList<>(); // la liste de tous les MapBlock

    private final int numBlockWidth; // le nombre de case dans la largeur
    private final int numBlockHeight; // le nombre de case dans la hauteur

    /**
     *
     * @param maplayout le layout du map voir MapDatabase pour plus d'info
     */
    public MapBackground(int[][] maplayout) {
        this.numBlockWidth = maplayout[0].length;
        this.numBlockHeight = maplayout.length;

        createBackground(maplayout);
    }

    //Originalement pour la redimensionnalisement
//    public void resizeBackground(double initBlockSize) {
//        for (Node nodeBlock : this.getChildren()) {
//            MapBlock block = (MapBlock) nodeBlock;
//            block.resizeBlock(initBlockSize);
//        }
//    }

    /**
     * Crée le background en utilisant le mapLayout
     * @param mapLayout le layout du map voir MapDatabase pour plus d'info
     */
    private void createBackground(int[][] mapLayout) {
        for (int x = 0; x < numBlockWidth; x++) {
            for (int y = 0; y < numBlockHeight; y++) {
                switch (mapLayout[y][x]) {
                    case 0: //wall
                        blocks.add(new WallBlock(new Coordinate(x, y), MapDatabase.INIT_MAP_BLOCK_SIZE));
                        break;
                    case 1: //juste path
                    case 2: //coin
                    case 3: //Powerup
                        blocks.add(new PathBlock(new Coordinate(x, y), MapDatabase.INIT_MAP_BLOCK_SIZE));
                        break;
                    case 4: //ghostWall
                        blocks.add(new GhostWallBlock(new Coordinate(x, y), MapDatabase.INIT_MAP_BLOCK_SIZE));
                }
            }
        }

        this.getChildren().addAll(blocks);
    }
}
