package year2025.day07;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import helper.CharGrid;
import helper.Point;

public class TachyonManifoldsDiagram extends CharGrid {

    int splitCount = 0;
    Map<Point, Long> countMap = new HashMap<>();
    Map<Point, Long> tempCountMap = new HashMap<>();

    public TachyonManifoldsDiagram(final List<String> tachyonManifoldsDiagram) {
        super(tachyonManifoldsDiagram);
    }

    public Set<Point> beam(Point start) {
        splitCount = 0;
        Set<Point> currentRay = new HashSet<>();
        countMap.put(start, 1L);
        int currentHeight = start.y();
        currentRay.add(start);

        for (int y = currentHeight + 1; y < height(); y++) {

            currentRay = beamOnce(currentRay);
            update(currentRay);
            print();
        }

        return currentRay;
    }


    public Set<Point> beamOnce(Point start) {
        Set<Point> currentRay = new HashSet<>();
        Long currentCount = countMap.get(start);
        if (get(start.x(), start.y() + 1) == '^') {
            splitCount++;
            Point p1 = new Point(start.x() - 1, start.y() + 1);
            currentRay.add(p1);
            updateTempCountMap(currentCount, p1);

            Point p2 = new Point(start.x() + 1, start.y() + 1);
            currentRay.add(p2);
            updateTempCountMap(currentCount, p2);
        } else {
            Point p = new Point(start.x(), start.y() + 1);
            currentRay.add(p);
            updateTempCountMap(currentCount, p);
        }
        return currentRay;
    }

    private void updateTempCountMap(Long currentCount, Point p) {
        if (tempCountMap.containsKey(p)) {
            tempCountMap.put(p, tempCountMap.get(p) + currentCount);
        } else {
            tempCountMap.put(p, currentCount);
        }
    }

    public Set<Point> beamOnce(Set<Point> startRay) {
        Set<Point> currentRay = new HashSet<>();
        tempCountMap = new HashMap<>();
        for (Point start : startRay) {
            currentRay.addAll(beamOnce(start));
        }
        countMap = tempCountMap;
        return currentRay;
    }

    public void update(final Set<Point> points) {
        for (Point point : points) {
            set(point.x(), point.y(), '|');
        }
    }
}

