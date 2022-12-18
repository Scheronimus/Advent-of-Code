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
}
