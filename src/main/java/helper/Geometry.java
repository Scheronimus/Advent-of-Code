package helper;

public class Geometry {
    public static int manhattanDistance(final Point a, final Point b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    public static double euclidianDistance(final Point3D a, final Point3D b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        double dz = a.getZ() - b.getZ();

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public static long rectangleArea(final Point a, final Point b) {
        return (long)(Math.abs(a.x() - b.x()) + 1) * (long)(Math.abs(a.y() - b.y()) + 1);
    }


}
