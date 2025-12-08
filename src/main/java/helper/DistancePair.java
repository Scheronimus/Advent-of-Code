package helper;

import java.util.Objects;

// This class stores a pair of points and the distance between them
public class DistancePair implements Comparable<DistancePair> {
    public final Point3D point1;
    public final Point3D point2;
    public final double distance;

    public DistancePair(final Point3D point1, final Point3D point2, final double distance) {
        // Ensure point1 is always "smaller" than point2 to avoid duplicates like (A,B) and (B,A)
        // This assumes Point3D has a consistent way to be ordered (e.g., by hashcode or x, then y, then z)
        // For simplicity, we'll just store them as given, but be aware of how you handle duplicates in calling code.
        this.point1 = point1;
        this.point2 = point2;
        this.distance = distance;
    }

    // A better constructor that ensures canonical ordering
    public DistancePair(final Point3D p1, final Point3D p2) {
        // This canonicalizes the pair (A,B) vs (B,A) if you want to treat them as the same
        // For now, let's just assume the caller passes unique (ordered) pairs
        // and we'll handle uniqueness based on (point1, point2) identity
        if (p1.hashCode() < p2.hashCode()) { // Simple way to order; can be more robust
            this.point1 = p1;
            this.point2 = p2;
        } else {
            this.point1 = p2;
            this.point2 = p1;
        }
        this.distance = Geometry.euclidianDistance(p1, p2);
    }

    @Override
    public int compareTo(final DistancePair other) {
        return Double.compare(this.distance, other.distance);
    }

    @Override
    public String toString() {
        return "DistancePair{" + "p1=" + point1 + ", p2=" + point2 + ", dist=" + String.format("%.4f", distance) + '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DistancePair that = (DistancePair)o;
        // Two pairs are equal if they consist of the same two points, regardless of order
        // This check assumes Point3D's equals method is correctly implemented.
        return point1.equals(that.point1) && point2.equals(that.point2)
                || point1.equals(that.point2) && point2.equals(that.point1);
    }

    @Override
    public int hashCode() {
        // Hashing for commutative pairs is tricky. A simple approach is to sum hash codes,
        // but this has collision risks. A more robust way might be to ensure canonical order
        // during construction (as attempted in the second constructor).
        // For now, let's just use a simple one, but be aware of its limitations if you put these in HashSets/Maps.
        // If (A,B) and (B,A) are considered the same, you must guarantee they produce the same hashcode.
        return Objects.hash(point1) + Objects.hash(point2); // Summing is one way for commutative property
    }
}