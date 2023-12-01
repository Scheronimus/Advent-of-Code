package year2022.day17;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Rock {
    List<Point> points;
    List<Point> bottomPoints;
    List<Point> rightPoints;
    List<Point> leftPoints;


    public Rock(List<Point> points, List<Point> bottomPoints, List<Point> rightPoints, List<Point> leftPoints) {
        super();
        this.points = points;
        this.bottomPoints = bottomPoints;
        this.rightPoints = rightPoints;
        this.leftPoints = leftPoints;
    }

    public void fall() {
        for (Point point : points) {
            point.setLocation(point.x, point.y - 1);
        }
    }

    public void moveRight() {
        for (Point point : points) {
            point.setLocation(point.x + 1, point.y);
        }
    }

    public void moveLeft() {
        for (Point point : points) {
            point.setLocation(point.x - 1, point.y);
        }
    }

    public boolean canFall(Board b) {
        for (Point point : bottomPoints) {
            if (!b.isFree(point.x, point.y - 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean canMoveRight(Board b) {
        for (Point point : rightPoints) {
            if (!b.isFree(point.x + 1, point.y)) {
                return false;
            }
        }
        return true;
    }

    public boolean canMoveLeft(Board b) {
        for (Point point : leftPoints) {
            if (!b.isFree(point.x - 1, point.y)) {
                return false;
            }
        }
        return true;
    }

    public static Rock createRock(RockShape shape, int heigth) {
        List<Point> points = new ArrayList<>();
        List<Point> bottomPoints = new ArrayList<>();
        List<Point> rightPoints = new ArrayList<>();
        List<Point> leftPoints = new ArrayList<>();
        int minY = heigth + 4;
        int minX = 3;
        switch (shape) {
            case MINUS: {
                createPoint(minX, minY, points, false, rightPoints, true, leftPoints, true, bottomPoints);
                createPoint(minX + 1, minY, points, false, rightPoints, false, leftPoints, true, bottomPoints);
                createPoint(minX + 2, minY, points, false, rightPoints, false, leftPoints, true, bottomPoints);
                createPoint(minX + 3, minY, points, true, rightPoints, false, leftPoints, true, bottomPoints);
                break;
            }
            case PLUS: {
                createPoint(minX + 1, minY, points, true, rightPoints, true, leftPoints, true, bottomPoints);
                createPoint(minX, minY + 1, points, false, rightPoints, true, leftPoints, true, bottomPoints);
                createPoint(minX + 1, minY + 1, points, false, rightPoints, false, leftPoints, false, bottomPoints);
                createPoint(minX + 2, minY + 1, points, true, rightPoints, false, leftPoints, true, bottomPoints);
                createPoint(minX + 1, minY + 2, points, true, rightPoints, true, leftPoints, false, bottomPoints);
                break;
            }
            case L: {
                createPoint(minX, minY, points, false, rightPoints, true, leftPoints, true, bottomPoints);
                createPoint(minX + 1, minY, points, false, rightPoints, false, leftPoints, true, bottomPoints);
                createPoint(minX + 2, minY, points, true, rightPoints, false, leftPoints, true, bottomPoints);
                createPoint(minX + 2, minY + 1, points, true, rightPoints, true, leftPoints, false, bottomPoints);
                createPoint(minX + 2, minY + 2, points, true, rightPoints, true, leftPoints, false, bottomPoints);
                break;
            }
            case LINE: {
                createPoint(minX, minY, points, true, rightPoints, true, leftPoints, true, bottomPoints);
                createPoint(minX, minY + 1, points, true, rightPoints, true, leftPoints, false, bottomPoints);
                createPoint(minX, minY + 2, points, true, rightPoints, true, leftPoints, false, bottomPoints);
                createPoint(minX, minY + 3, points, true, rightPoints, true, leftPoints, false, bottomPoints);
                break;
            }
            case SQUARE: {
                createPoint(minX, minY, points, false, rightPoints, true, leftPoints, true, bottomPoints);
                createPoint(minX + 1, minY, points, true, rightPoints, false, leftPoints, true, bottomPoints);
                createPoint(minX, minY + 1, points, false, rightPoints, true, leftPoints, false, bottomPoints);
                createPoint(minX + 1, minY + 1, points, true, rightPoints, false, leftPoints, false, bottomPoints);
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + shape);
        }
        return new Rock(points, bottomPoints, rightPoints, leftPoints);

    }

    private static void createPoint(int x, int y, List<Point> points, boolean right, List<Point> rightPoints,
            boolean left, List<Point> leftPoints, boolean bottom, List<Point> bottomPoints) {
        Point p4 = new Point(x, y);
        points.add(p4);
        if (right) {
            rightPoints.add(p4);
        }
        if (left) {
            leftPoints.add(p4);
        }
        if (bottom) {
            bottomPoints.add(p4);
        }
    }


}
