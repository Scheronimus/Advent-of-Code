package day17;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Board {

    List<Point> rocks = new ArrayList<>();
    long height = 0;


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
}
