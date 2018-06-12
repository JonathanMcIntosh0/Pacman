package main.mapComponents;

import javafx.scene.Group;
import javafx.scene.Node;
import main.mapComponents.blockTypes.GhostWallBlock;
import main.mapComponents.blockTypes.PathBlock;
import main.mapComponents.blockTypes.WallBlock;
import main.viewsAndModels.game.MapDatabase;

import java.util.ArrayList;
import java.util.Collection;

public class MapBackground extends Group {
    private final Collection<Node> blocks = new ArrayList<>();

    private final int numBlockWidth;
    private final int numBlockHeight;

    public MapBackground(int[][] maplayout) {
        this.numBlockWidth = maplayout[0].length;
        this.numBlockHeight = maplayout.length;

        createBackground(maplayout);
    }

//    public void resizeBackground(double initBlockSize) {
//        for (Node nodeBlock : this.getChildren()) {
//            MapBlock block = (MapBlock) nodeBlock;
//            block.resizeBlock(initBlockSize);
//        }
//    }

    private void createBackground(int[][] mapLayout) {
        for (int x = 0; x < numBlockWidth; x++) {
            for (int y = 0; y < numBlockHeight; y++) {
                switch (mapLayout[y][x]) {
                    case 0:
                        blocks.add(new WallBlock(new Coordinate(x, y), MapDatabase.INIT_MAP_BLOCK_SIZE));
                        break;
                    case 1:
                    case 2:
                    case 3:
                        blocks.add(new PathBlock(new Coordinate(x, y), MapDatabase.INIT_MAP_BLOCK_SIZE));
                        break;
                    case 4:
                        blocks.add(new GhostWallBlock(new Coordinate(x, y), MapDatabase.INIT_MAP_BLOCK_SIZE));
                }
            }
        }

        this.getChildren().addAll(blocks);
    }
}
