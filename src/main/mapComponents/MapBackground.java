package main.mapComponents;

import javafx.scene.Group;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Collection;

public class MapBackground extends Group{
    private double blockSize;

    Collection<Node> blocks = new ArrayList<>();

    public int numBlockWidth;
    public int numBlockHeight;

    public MapBackground(double initBlockSize, int[][] maplayout) {
        this.blockSize = initBlockSize;
        this.numBlockWidth = maplayout[0].length;
        this.numBlockHeight = maplayout.length;

        createBackground(blockSize, maplayout);
    }

    public void resizeBackground(double blockSize) {
        for (Node nodeBlock : this.getChildren()) {
            MapBlock block = (MapBlock) nodeBlock;
            block.resizeBlock(blockSize);
        }
    }

    private void createBackground(double blockSize, int[][] mapLayout) {
        for (int x = 0; x < numBlockWidth; x++) {
            for (int y = 0; y < numBlockHeight; y++) {
                if (mapLayout[y][x] == 0) {
                    blocks.add(new WallBlock(new Coordinate(x, y), blockSize));
                } else {
                    blocks.add(new PathBlock(new Coordinate(x, y), blockSize));
                }
            }
        }

        this.getChildren().addAll(blocks);
    }
}
