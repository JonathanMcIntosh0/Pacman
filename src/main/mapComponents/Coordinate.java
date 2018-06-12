package main.mapComponents;

public class Coordinate {
    private final double x;
    private final double y;

    // 0,0 is top left like graph

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean equalsIgnoreFrac(Coordinate coordinate) {
        return Math.round(coordinate.getX()) == Math.round(x) && Math.round(coordinate.getY()) == Math.round(y);
    }
}
