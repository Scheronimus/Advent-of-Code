package year2025.day09;

import java.util.ArrayList;
import java.util.List;

import helper.Point;

public class PolygoneUtils {

    // --------------------------
    // RECTANGLE CORNERS
    // --------------------------
    public static List<Point> getRectangleCorners(Point a, Point b) {
        int minX = Math.min(a.x(), b.x());
        int maxX = Math.max(a.x(), b.x());
        int minY = Math.min(a.y(), b.y());
        int maxY = Math.max(a.y(), b.y());

        List<Point> corners = new ArrayList<>(4);
        corners.add(new Point(minX, minY));
        corners.add(new Point(maxX, minY));
        corners.add(new Point(maxX, maxY));
        corners.add(new Point(minX, maxY));
        return corners;
    }

    // --------------------------
    // POINT ON SEGMENT
    // --------------------------
    private static boolean pointOnSegment(Point p, Point a, Point b) {
        long cross = (long)(p.y() - a.y()) * (b.x() - a.x()) - (long)(p.x() - a.x()) * (b.y() - a.y());
        if (cross != 0)
            return false; // not collinear

        long dot = (long)(p.x() - a.x()) * (b.x() - a.x()) + (long)(p.y() - a.y()) * (b.y() - a.y());
        if (dot < 0)
            return false;

        long sqLen = (long)(b.x() - a.x()) * (b.x() - a.x()) + (long)(b.y() - a.y()) * (b.y() - a.y());
        return dot <= sqLen;
    }

    // --------------------------
    // POINT IN POLYGON (with boundary)
    // --------------------------
    public static boolean isPointInPolygon(Point pt, List<Point> poly) {
        boolean inside = false;
        for (int i = 0, j = poly.size() - 1; i < poly.size(); j = i++) {
            Point a = poly.get(j);
            Point b = poly.get(i);

            if (pointOnSegment(pt, a, b))
                return true; // on edge

            boolean intersects = ((a.y() > pt.y()) != (b.y() > pt.y()))
                    && (pt.x() < (long)(b.x() - a.x()) * (pt.y() - a.y()) / (b.y() - a.y()) + a.x());
            if (intersects)
                inside = !inside;
        }
        return inside;
    }


    // Returns true if edges properly cross, ignoring endpoints and full overlap
    private static boolean orthogonalEdgeCuts(Point r1, Point r2, Point p1, Point p2) {
        boolean rH = r1.y() == r2.y();
        boolean pH = p1.y() == p2.y();

        if (rH && pH) {
            if (r1.y() != p1.y())
                return false;
            int rMin = Math.min(r1.x(), r2.x());
            int rMax = Math.max(r1.x(), r2.x());
            int pMin = Math.min(p1.x(), p2.x());
            int pMax = Math.max(p1.x(), p2.x());
            // ignore full overlap and touching
            return rMin < pMax && pMin < rMax && !(rMin >= pMin && rMax <= pMax) && !(pMin >= rMin && pMax <= rMax);
        }

        if (!rH && !pH) {
            if (r1.x() != p1.x())
                return false;
            int rMin = Math.min(r1.y(), r2.y());
            int rMax = Math.max(r1.y(), r2.y());
            int pMin = Math.min(p1.y(), p2.y());
            int pMax = Math.max(p1.y(), p2.y());
            return rMin < pMax && pMin < rMax && !(rMin >= pMin && rMax <= pMax) && !(pMin >= rMin && pMax <= rMax);
        }

        // horizontal vs vertical
        Point h = rH ? r1 : p1;
        Point h2 = rH ? r2 : p2;
        Point v = rH ? p1 : r1;
        Point v2 = rH ? p2 : r2;

        int hxMin = Math.min(h.x(), h2.x());
        int hxMax = Math.max(h.x(), h2.x());
        int vyMin = Math.min(v.y(), v2.y());
        int vyMax = Math.max(v.y(), v2.y());

        // proper intersection strictly inside intervals
        return v.x() > hxMin && v.x() < hxMax && h.y() > vyMin && h.y() < vyMax;
    }


    public static boolean isRectangleEdgeCutByOrthogonalPolygon(List<Point> rect, List<Point> polygon) {
        for (int i = 0; i < rect.size(); i++) {
            Point a1 = rect.get(i);
            Point a2 = rect.get((i + 1) % rect.size());

            for (int j = 0; j < polygon.size(); j++) {
                Point b1 = polygon.get(j);
                Point b2 = polygon.get((j + 1) % polygon.size());

                if (orthogonalEdgeCuts(a1, a2, b1, b2)) {
                    return true;
                }
            }
        }
        return false;
    }

    // --------------------------
    // MAIN: CHECK RECTANGLE FULLY INSIDE POLYGON
    // --------------------------
    public static boolean isRectangleInsidePolygon(Point p1, Point p2, List<Point> polygon) {
        List<Point> rect = getRectangleCorners(p1, p2);

        // Check that each corner is inside polygon (points on edges count as inside)

        for (Point corner : rect) {
            if (!isPointInPolygon(corner, polygon)) {
                return false;
            }
        }

        if (isRectangleEdgeCutByOrthogonalPolygon(rect, polygon)) {
            return false;
        }


        return true;
    }
}