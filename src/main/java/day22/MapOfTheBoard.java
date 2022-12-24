package day22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import helper.Direction;

public class MapOfTheBoard {
    List<String> values = new ArrayList<>();
    Map<Integer, int[]> rows = new HashMap<>();
    Map<Integer, int[]> columns = new HashMap<>();


    public MapOfTheBoard(List<String> lines) {
        super();
        this.values = lines;

        generateMaps(values);
    }


    public char get(int x, int y) {
        String line = values.get(y - 1);
        return line.charAt(x - 1);

    }

    public Position getStatingPoint() {
        int x = values.get(0).indexOf('.') + 1;

        return new Position(x, 1, Direction.RIGHT);

    }


    private void generateMaps(List<String> values) {
        int globalXmax = 0;
        for (Integer y = 1; y <= values.size(); y++) {
            String line = values.get(y - 1);
            int xMax = line.length();
            int hash = line.indexOf('#');
            int dot = line.indexOf('.');
            if (hash == -1)
                hash = values.size();
            if (dot == -1)
                dot = values.size();
            int xMin = Math.min(hash, dot);
            rows.put(y, new int[] { xMin + 1, xMax });
            globalXmax = Math.max(globalXmax, xMax);
        }

        for (Integer x = 1; x <= globalXmax; x++) {
            int yMin = 1;
            int yMax = 1;
            for (Integer y = 1; y <= values.size(); y++) {
                String line = values.get(y - 1);
                if ((line.length() < x) || (line.charAt(x - 1) == ' ')) {
                    if (yMin == yMax) {
                        yMin++;
                        yMax++;

                    } else {
                        yMax--;
                        break;
                    }
                } else {
                    yMax++;
                }
            }
            if (yMax > values.size()) {
                yMax = values.size();
            }
            columns.put(x, new int[] { yMin, yMax });

        }


    }
}
