package helper;

import java.awt.Point;

public class Geometry {
    public static int manhattanDistance(final Point a, final Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public static double euclidianDistance(final Point3D a, final Point3D b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        double dz = a.getZ() - b.getZ();

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }


}
