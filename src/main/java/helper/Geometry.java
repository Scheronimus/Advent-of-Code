package helper;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public static List<DistancePair> findKShorterDistancePairs(final List<Point3D> points, final int k) {
        if (points == null || points.size() < 2 || k <= 0) {
            System.out.println("Invalid input: List is null, contains less than 2 points, or k is not positive.");
            return new ArrayList<>();
        }

        List<DistancePair> allPairsWithDistances = new ArrayList<>();

        // Generate all unique pairs and their distances
        for (int i = 0; i < points.size(); i++) {
            Point3D p1 = points.get(i);
            for (int j = i + 1; j < points.size(); j++) { // j starts from i+1 to avoid duplicates (A,B) and (B,A) and
                                                          // self-comparison
                Point3D p2 = points.get(j);
                double distance = euclidianDistance(p1, p2);
                allPairsWithDistances.add(new DistancePair(p1, p2, distance));
            }
        }

        // Sort all pairs by distance
        Collections.sort(allPairsWithDistances);

        // Return the first K pairs, or fewer if there aren't K total pairs
        return allPairsWithDistances.subList(0, Math.min(k, allPairsWithDistances.size()));
    }
}
