package main.mapComponents;

public class Coordinate {
    final int x;
    final int y;

    // 0,0 is top left like graph

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
