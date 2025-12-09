package year2025.day09;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import helper.Geometry;
import helper.Point;
import helper.Puzzle;

public class MovieTheater extends Puzzle {
    List<Point> points = new ArrayList<>();

    public MovieTheater(final String input) throws IOException {
        super(input);
        parse(getLines());
    }

    private void parse(final List<String> lines) {
        for (String line : lines) {
            String[] splittedLine = line.split(",");
            points.add(new Point(Integer.parseInt(splittedLine[0]), Integer.parseInt(splittedLine[1])));

        }
    }

    public static List<AreaPair> findHigherAreaPairs(final List<Point> points) {

        List<AreaPair> allPairsWithArea = new ArrayList<>();

        // Generate all unique pairs and their areas
        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(i);
            for (int j = i + 1; j < points.size(); j++) {
                Point p2 = points.get(j);
                long area = Geometry.rectangleArea(p1, p2);
                allPairsWithArea.add(new AreaPair(p1, p2, area));

            }
        }

        // Sort all pairs by distance
        Collections.sort(allPairsWithArea, Collections.reverseOrder());


        return allPairsWithArea;
    }

    @Override
    public Object getAnswer1() {
        List<AreaPair> allPairsWithArea = findHigherAreaPairs(points);
        return allPairsWithArea.get(0).area;
    }


    @Override
    public Object getAnswer2() {
        long solution = 0;
        List<Point> polygone = new ArrayList<Point>(points);
        // PolygonePrinter.visualizePolygonScaled(polygone);

        List<AreaPair> allPairsWithArea = findHigherAreaPairs(points);

        for (AreaPair pair : allPairsWithArea) {
            if (PolygoneUtils.isRectangleInsidePolygon(pair.point1, pair.point2, polygone)) {
                solution = pair.area;
                break;
            }

        }

        return solution;
    }

    public static void main(final String[] args) throws IOException {
        MovieTheater puzzle = new MovieTheater("year2025/day09/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }
}
