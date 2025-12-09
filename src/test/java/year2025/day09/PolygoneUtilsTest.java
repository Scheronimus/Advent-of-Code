package year2025.day09;

import java.util.List;

import org.junit.jupiter.api.Test;

import helper.Point;

public class PolygoneUtilsTest {

    @Test
    public void testIsPointInPolygon() {
        List<Point> poly = List.of(new Point(0, 0), new Point(10, 0), new Point(10, 10), new Point(0, 10));

        boolean inside = PolygoneUtils.isPointInPolygon(new Point(0, 0), poly);
        System.out.println(inside);
        inside = PolygoneUtils.isPointInPolygon(new Point(10, 0), poly);
        System.out.println(inside);
        inside = PolygoneUtils.isPointInPolygon(new Point(10, 10), poly);
        System.out.println(inside);
        inside = PolygoneUtils.isPointInPolygon(new Point(0, 10), poly);
        System.out.println(inside);
    }

    @Test
    public void isRectangleEdgeCutByOrthogonalPolygon() {
        List<Point> poly = List.of(new Point(0, 0), new Point(10, 0), new Point(10, 10), new Point(0, 10));
        List<Point> rect = PolygoneUtils.getRectangleCorners(new Point(0, 0), new Point(10, 10));

        boolean inside = PolygoneUtils.isRectangleEdgeCutByOrthogonalPolygon(rect, poly);
        System.out.println(inside);

    }


}
