package year2023.day10;

import java.util.ArrayList;
import java.util.List;

public class Maze {
    List<String> mazeMap = new ArrayList<>();

    public Maze(List<String> mazeMap) {
        super();
        this.mazeMap = mazeMap;
        // System.out.println(getStartingPoint());
        // System.out.println(getTile(2, 0));
    }

    public Tile getStartingPoint() {
        int i = 0;
        for (String line : mazeMap) {
            int index = line.indexOf("S");
            if (index >= 0) {
                return new Tile(index, i, 'S');
            }
            i++;
        }
        return null;
    }

    public Tile getTile(int x, int y) {
        return new Tile(x, y, mazeMap.get(y).charAt(x));
    }


}
