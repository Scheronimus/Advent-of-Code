package day14;

import java.util.Objects;

public class Point {

    private int x, y;

    public Point(final int x1, final int y1) {
        x = x1;
        y = y1;
    }

    protected void setX(final int x) {
        this.x = x;
    }

    protected void setY(final int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    };

    public int getY() {
        return y;
    };

    public double distance(final Point p) {
        return Math.sqrt(Math.pow(p.getX() - x, 2) + Math.pow(p.getY() - y, 2));
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Point other = (Point)obj;
        return x == other.x && y == other.y;
    }


}

