package year2025.day09;

import java.util.ArrayList;
import java.util.List;

import helper.Point;

public class PolygonePrinter {

    public static void visualizePolygonScaled(List<Point> polygon) {
        if (polygon == null || polygon.isEmpty()) {
            System.out.println("(empty)");
            return;
        }

        // Canvas size (you can increase these)
        int CANVAS_WIDTH = 120;
        int CANVAS_HEIGHT = 60;

        // Find bounds
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for (Point p : polygon) {
            minX = Math.min(minX, p.x());
            minY = Math.min(minY, p.y());
            maxX = Math.max(maxX, p.x());
            maxY = Math.max(maxY, p.y());
        }

        int rangeX = maxX - minX + 1;
        int rangeY = maxY - minY + 1;

        // Scaling factors
        double sx = (double)(CANVAS_WIDTH - 1) / rangeX;
        double sy = (double)(CANVAS_HEIGHT - 1) / rangeY;

        // Build canvas
        char[][] canvas = new char[CANVAS_HEIGHT][CANVAS_WIDTH];
        for (char[] row : canvas) {
            java.util.Arrays.fill(row, '.');
        }

        // Scale & plot vertices
        List<Point> scaled = new ArrayList<>();
        for (Point p : polygon) {
            int x = (int)((p.x() - minX) * sx);
            int y = (int)((p.y() - minY) * sy);
            scaled.add(new Point(x, y));
            canvas[y][x] = 'X';
        }

        // Draw edges (scaled)
        for (int i = 0; i < scaled.size(); i++) {
            Point a = scaled.get(i);
            Point b = scaled.get((i + 1) % scaled.size());
            drawLine(canvas, a.x(), a.y(), b.x(), b.y());
        }

        // Print canvas
        for (char[] row : canvas) {
            System.out.println(new String(row));
        }
    }

    // Bresenham line on the scaled canvas
    private static void drawLine(char[][] canvas, int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            if (y0 >= 0 && y0 < canvas.length && x0 >= 0 && x0 < canvas[0].length) {
                canvas[y0][x0] = '#';
            }

            if (x0 == x1 && y0 == y1)
                break;

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }
}
