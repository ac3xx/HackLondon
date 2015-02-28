package frontend;

/**
 * Created by James on 28/02/15.
 */
public class TileLocation {
    private int x;
    private int y;

    public TileLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object other) {
        return this.x == ((TileLocation) other).x
                &&
                this.y == ((TileLocation) other).y;
    }

    @Override
    public int hashCode() {
        Integer xi = x;
        Integer yi = y;
        return (xi.hashCode() + "," + yi.hashCode()).hashCode();
    }
}
