package year2025.day08;

import java.util.Objects;

import helper.Point3D;

// This class stores a pair of points and the distance between them
public class DistancePair implements Comparable<DistancePair> {
    public final Point3D point1;
    public final Point3D point2;
    public final double distance;

    public DistancePair(final Point3D point1, final Point3D point2, final double distance) {
        this.point1 = point1;
        this.point2 = point2;
        this.distance = distance;
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
        return point1.equals(that.point1) && point2.equals(that.point2)
                || point1.equals(that.point2) && point2.equals(that.point1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point1) + Objects.hash(point2); // Summing is one way for commutative property
    }
}