package day17;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Board {

    List<Point> rocks = new ArrayList<>();
    double height = 0;


    boolean isFree(int x, int y) {
        if (y <= 0 || x <= 0 || x >= 8 || rocks.contains(new Point(x, y))) {
            return false;
        }
        return true;

    }

    void addRock(Rock rock) {
        rocks.addAll(rock.points);
        for (Point point : rock.points) {
            if (point.y > height) {
                height = point.y;
            }
        }
    }

    List<Integer> getTopNivel() {
        List<Integer> topNivel = new ArrayList<>();
        for (int x = 1; x <= 7; x++) {
            for (int y = (int)height; y >= 0; y--) {
                if (y == 0 || rocks.contains(new Point(x, y))) {
                    topNivel.add(y - (int)height);
                    break;
                }
            }
        }
        return topNivel;
    }

    String visualized() {
        StringBuilder sb = new StringBuilder();
        for (int y = (int)height; y >= 0; y--) {
            for (int x = 0; x <= 8; x++) {
                if (x == 0 || x == 8) {
                    sb.append("|");

                } else if (y == 0) {
                    sb.append("-");
                } else if (rocks.contains(new Point(x, y))) {
                    sb.append("#");
                } else {
                    sb.append(".");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
