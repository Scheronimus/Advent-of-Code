package day14;

import java.util.ArrayList;
import java.util.List;

public class Slice {
    Point topLeft;
    Point bottomRight;

    Point source = new Point(500, 0);
    List<Point> rocks = new ArrayList<>();
    List<Point> sands = new ArrayList<>();

    public Slice(final Point topLeft, final Point bottomRight) {
        super();
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Slice() {
        super();
        this.topLeft = new Point(source.getX(), source.getY());
        this.bottomRight = new Point(source.getX(), source.getY());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = topLeft.getY(); y <= bottomRight.getY(); y++) {
            for (int x = topLeft.getX(); x <= bottomRight.getX(); x++) {
                Point p = new Point(x, y);
                if (source.equals(p)) {
                    sb.append('+');
                } else if (rocks.contains(p)) {
                    sb.append('#');
                } else if (sands.contains(p)) {
                    sb.append('O');
                } else {
                    sb.append('.');
                }

            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean dropSand(Point Start) {
        Point next = new Point(Start.getX(), Start.getY() + 1);

        while (!rocks.contains(next) && !sands.contains(next)) {
            if (next.getY() + 1 > this.bottomRight.getY()) {
                return false;
            }
            next.setY(next.getY() + 1);

        }

        Point left = new Point(next.getX() - 1, next.getY());
        if (!rocks.contains(left) && !sands.contains(left)) {
            return dropSand(left);
            // return;
        }
        Point right = new Point(next.getX() + 1, next.getY());

        if (!rocks.contains(right) && !sands.contains(right)) {
            return dropSand(right);

        }

        sands.add(generateNewPoint(next.getX(), next.getY() - 1));
        return true;

    }


    public void addStones(final DrawingInstruction instruction) {
        // for point:instruction.pointOrder
        for (int i = 0; i < instruction.pointOrder.size() - 1; i++) {
            printStonelineBetween(instruction.pointOrder.get(i), instruction.pointOrder.get(i + 1));
        }
    }

    public void printStonelineBetween(final Point point1, final Point point2) {
        if (point1.getY() == point2.getY()) {
            // Vertical
            int y = point1.getY();
            int xMin;
            int xMax;
            if (point1.getX() <= point2.getX()) {
                xMin = point1.getX();
                xMax = point2.getX();
            } else {
                xMin = point2.getX();
                xMax = point1.getX();
            }

            for (int i = xMin; i <= xMax; i++) {
                rocks.add(generateNewPoint(i, y));
            }
        } else if (point1.getX() == point2.getX()) {
            // Horizontal
            int x = point1.getX();
            int yMin;
            int yMax;
            if (point1.getY() <= point2.getY()) {
                yMin = point1.getY();
                yMax = point2.getY();
            } else {
                yMin = point2.getY();
                yMax = point1.getY();
            }

            for (int i = yMin; i <= yMax; i++) {
                rocks.add(generateNewPoint(x, i));
            }
        } else {
            throw new RuntimeException("Only horizontal && vertical line are supported");
        }
    }

    private Point generateNewPoint(final int x, final int y) {

        if (x < topLeft.getX()) {
            topLeft.setX(x);
        }
        if (y < topLeft.getY()) {
            topLeft.setY(y);
        }

        if (x > bottomRight.getX()) {
            bottomRight.setX(x);
        }
        if (y > bottomRight.getY()) {
            bottomRight.setY(y);
        }
        return new Point(x, y);

    }

}
