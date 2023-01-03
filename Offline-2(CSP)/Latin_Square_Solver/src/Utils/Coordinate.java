package Utils;

public class Coordinate {
    private int x;
    private int y;

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

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "("+x+", "+y+")";
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o==null || !(o instanceof Coordinate)) {
            return false;
        }
        Coordinate coordinate = (Coordinate) o;
        return (x==coordinate.x && y==coordinate.y);
    }
}
