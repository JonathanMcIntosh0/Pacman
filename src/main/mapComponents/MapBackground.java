package main.mapComponents;

import javafx.scene.Group;
import javafx.scene.Node;
import main.mapGenerator.MapDatabase;

import java.util.ArrayList;
import java.util.Collection;

public class MapBackground extends Group{
    Collection<Node> blocks = new ArrayList<>();

    public int numBlockWidth;
    public int numBlockHeight;

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
                if (mapLayout[y][x] == 0) {
                    blocks.add(new WallBlock(new Coordinate(x, y), MapDatabase.INIT_MAP_BLOCK_SIZE));
                } else {
                    blocks.add(new PathBlock(new Coordinate(x, y), MapDatabase.INIT_MAP_BLOCK_SIZE));
                }
            }
        }

        this.getChildren().addAll(blocks);
    }
}
