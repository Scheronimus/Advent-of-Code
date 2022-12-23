package day22;

import java.util.ArrayList;
import java.util.List;

import helper.Direction;

public class MapOfTheBoard {
    List<String> lines = new ArrayList<>();

    public void add(String line) {
        lines.add(line);
    }

    public char get(int x, int y) {
        String line = lines.get(y - 1);
        return line.charAt(x - 1);

    }

    public Position getStatingPoint() {
        int x = lines.get(0).indexOf('.') + 1;

        return new Position(x, 1, Direction.RIGHT);

    }
}
