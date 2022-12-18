package day18;

import java.util.ArrayList;
import java.util.List;

public class Shape {
    List<Cube> cubes = new ArrayList<>();
    int side = 0;

    public void addCube(Cube cube) {
        int cubeSide = cube.getSide();
        List<Cube> adjacents = cube.getAdjacent();

        for (Cube adjacent : adjacents) {
            if (cubes.contains(adjacent)) {
                side--;
                cubeSide--;
            }
        }
        cubes.add(cube);
        side += cubeSide;
    }

    public DrawMax getXMax() {
        Integer xMax = null;
        Integer xMin = null;
        Integer yMax = null;
        Integer yMin = null;
        Integer zMax = null;
        Integer zMin = null;

        for (Cube cube : cubes) {
            int x = cube.point.getX();
            int y = cube.point.getY();
            int z = cube.point.getZ();

            if (xMax == null || x > xMax) {
                xMax = x;
            }
            if (xMin == null || x < xMin) {
                xMin = x;
            }
            if (yMax == null || y > yMax) {
                yMax = y;
            }
            if (yMin == null || y < yMin) {
                yMin = y;
            }
            if (zMax == null || z > zMax) {
                zMax = z;
            }
            if (zMin == null || z < zMin) {
                zMin = z;
            }
        }
        return new DrawMax(xMin, xMax, yMin, yMax, zMin, zMax);
    }
}
