package main.mapComponents;

/**
 * Une coordonné avec un x et y
 */
public class Coordinate {
    private int x;
    private int y;

    // 0,0 is top left like graph

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void translateX(int dx) {
        this.x += dx;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void translateY(int dy) {
        this.y += dy;
    }

    /**
     * Pour déterminer si deux blockcoord sont dans la même case
     * @param coordinate
     * @return si ils sont égles ou non
     */
    public boolean isEqual(Coordinate coordinate) {
        return coordinate.getX() == x && coordinate.getY() == y;
    }

    @Override
    public String toString() {
        return "(" + x + " , " + y + ")";
    }
}
