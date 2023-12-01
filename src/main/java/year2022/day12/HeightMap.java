package year2022.day12;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HeightMap {

    List<List<Character>> heightMap = new ArrayList<>();
    int Sx;
    int Sy;
    int Ex;
    int Ey;

    public Character get(final int x, final int y) {
        return heightMap.get(y).get(x);
    }

    public void addLine(String line) {

        int indexS = line.indexOf("S");
        if (indexS != -1) {
            Sx = indexS;
            Sy = heightMap.size();
        }
        int indexE = line.indexOf("E");
        if (indexE != -1) {
            Ex = indexE;
            Ey = heightMap.size();
        }
        line = line.replace('S', 'a');
        line = line.replace('E', 'z');
        List<Character> list = line.chars().mapToObj((i) -> Character.valueOf((char)i)).collect(Collectors.toList());
        heightMap.add(list);
    }

    public void print() {
        for (List<Character> line : heightMap) {
            for (Character cell : line) {
                System.out.print(cell);
            }
            System.out.print("\n");
        }
    }

    public int getmaxX() {
        return heightMap.get(0).size();
    }

    public int getmaxY() {
        return heightMap.size();
    }

}
