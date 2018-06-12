package main.mapComponents;

import javafx.scene.Group;
import javafx.scene.Node;
import main.mapComponents.items.ItemBlock;
import main.mapComponents.items.Coin;
import main.mapComponents.items.PowerUp;
import main.viewsAndModels.game.MapDatabase;

import java.util.ArrayList;
import java.util.Collection;

public class MapMiddleground extends Group {
    private final Collection<Node> items = new ArrayList<>();

    private final int numBlockWidth;
    private final int numBlockHeight;

    public MapMiddleground(int[][] maplayout) {
        this.numBlockWidth = maplayout[0].length;
        this.numBlockHeight = maplayout.length;

        createMiddleground(maplayout);
    }

//    public void resizeMiddleground(double blockSize) {
//        for (Node nodeBlock : this.getChildren()) {
//            ItemBlock item = (ItemBlock) nodeBlock;
//            item.resizeItem(blockSize);
//        }
//    }

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

    public ItemBlock getItem(Coordinate blockCoord) {
        for (Node nodeBlock : this.getChildren()) {
            ItemBlock item = (ItemBlock) nodeBlock;
            if (item.blockCoord.equalsIgnoreFrac(blockCoord)) return item;
        }
        return null;
    }
}
