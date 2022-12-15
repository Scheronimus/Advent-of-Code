package helper;

import java.awt.Point;

public class Geometry {
    public static int manhattanDistance(final Point a, final Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
}
