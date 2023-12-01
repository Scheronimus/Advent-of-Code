package year2022.day18;

import java.util.ArrayList;
import java.util.List;

public class Coloring {
    List<Cube> cubes = new ArrayList<>();
    private List<Cube> colored = new ArrayList<>();
    DrawMax max;

    public Coloring(List<Cube> cubes, DrawMax max) {
        super();
        this.cubes = cubes;
        this.max = new DrawMax(max.xMin - 1, max.xMax + 1, max.yMin - 1, max.yMax + 1, max.zMin - 1, max.zMax + 1);
    }

    public void color() {
        Cube startCube = new Cube(max.xMin, max.yMin, max.zMin);
        colored.add(startCube);
        color(startCube);
    }


    public void color(Cube start) {
        List<Cube> adjacentes = start.getAdjacent();
        for (Cube adj : adjacentes) {
            if (!isOutOfBorder(adj) && !colored.contains(adj) && !cubes.contains(adj)) {
                colored.add(adj);
                color(adj);
            }
        }
    }

    public List<Cube> getNotColored() {
        List<Cube> notColored = new ArrayList<>();
        for (int x = max.xMin; x <= max.xMax; x++) {
            for (int y = max.yMin; y <= max.yMax; y++) {
                for (int z = max.zMin; z <= max.zMax; z++) {
                    Cube c = new Cube(x, y, z);
                    if (!colored.contains(c) && !cubes.contains(c)) {
                        notColored.add(c);
                    }
                }
            }
        }
        return notColored;
    }

    public boolean isOutOfBorder(Cube cube) {
        return cube.point.getX() > max.xMax || cube.point.getY() > max.yMax || cube.point.getZ() > max.zMax
                || cube.point.getX() < max.xMin || cube.point.getY() < max.yMin || cube.point.getZ() < max.zMin;
    }
}
