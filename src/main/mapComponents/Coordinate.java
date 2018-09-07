package main.mapComponents;

/**
 * Une coordonné avec un x et y
 */
public class Coordinate {
    private double x;
    private double y;

    // 0,0 is top left like graph

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void translateX(double dx) {
        this.x += dx;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void translateY(double dy) {
        this.y += dy;
    }

    /**
     * Pour déterminer si deux blockcoord sont dans la même case
     * @param coordinate
     * @return si ils sont égles ou non
     */
    public boolean equalsIgnoreFrac(Coordinate coordinate) {
        return Math.round(coordinate.getX()) == Math.round(x) && Math.round(coordinate.getY()) == Math.round(y);
    }
}
