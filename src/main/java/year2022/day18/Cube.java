package year2022.day18;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import helper.Point3D;

public class Cube {
    Point3D point;
    int side = 6;

    public Cube(int x, int y, int z) {
        super();
        point = new Point3D(x, y, z);
    }

    @Override
    public String toString() {
        return point.toString();
    }

    public int getSide() {
        return side;
    }

    List<Cube> getAdjacent() {
        List<Cube> adjacent = new ArrayList<>();
        int x = point.getX();
        int y = point.getY();
        int z = point.getZ();

        adjacent.add(new Cube(x + 1, y, z));
        adjacent.add(new Cube(x - 1, y, z));
        adjacent.add(new Cube(x, y + 1, z));
        adjacent.add(new Cube(x, y - 1, z));
        adjacent.add(new Cube(x, y, z + 1));
        adjacent.add(new Cube(x, y, z - 1));

        return adjacent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(point);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cube other = (Cube)obj;
        return Objects.equals(point, other.point);
    }


}
