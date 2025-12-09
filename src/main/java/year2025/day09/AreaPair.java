package year2025.day09;

import java.util.Objects;

import helper.Point;

// This class stores a pair of points and the distance between them
public class AreaPair implements Comparable<AreaPair> {
    public final Point point1;
    public final Point point2;
    public final long area;

    public AreaPair(final Point point1, final Point point2, final long distance) {
        this.point1 = point1;
        this.point2 = point2;
        this.area = distance;
    }


    @Override
    public int compareTo(final AreaPair other) {
        return Double.compare(this.area, other.area);
    }

    @Override
    public String toString() {
        return "AreaPair{" + "p1=" + point1 + ", p2=" + point2 + ", area=" + area + '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AreaPair that = (AreaPair)o;
        // Two pairs are equal if they consist of the same two points, regardless of order
        return point1.equals(that.point1) && point2.equals(that.point2)
                || point1.equals(that.point2) && point2.equals(that.point1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point1) + Objects.hash(point2); // Summing is one way for commutative property
    }
}