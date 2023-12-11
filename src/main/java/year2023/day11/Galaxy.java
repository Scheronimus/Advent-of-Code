package year2023.day11;

public class Galaxy {
    @Override
    public String toString() {
        return "Galaxy [x=" + x + ", y=" + y + "]";
    }

    long x;
    long y;

    public Galaxy(final int x, final int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public long getDistanceTo(final Galaxy g) {

        return Math.abs(x - g.x) + Math.abs(y - g.y);
    }


}
