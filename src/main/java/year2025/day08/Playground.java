package year2025.day08;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import helper.DistancePair;
import helper.Geometry;
import helper.Point3D;
import helper.Puzzle;

public class Playground extends Puzzle {
    List<Point3D> points = new ArrayList<>();
    int connection = 1000;

    public Playground(final String input) throws IOException {
        super(input);
        parse(getLines());
    }

    private void parse(final List<String> lines) {

        for (String line : lines) {
            String[] splittedLine = line.split(",");
            // Integer.parseInt(splittedLine[0])
            points.add(new Point3D(Integer.parseInt(splittedLine[0]), Integer.parseInt(splittedLine[1]),
                    Integer.parseInt(splittedLine[2])));

        }

        // System.out.println(points);

    }


    @Override
    public Object getAnswer1() {
        Map<Point3D, Integer> groups = initateGroup();

        // System.out.println(groups);

        List<DistancePair> res = Geometry.findKShorterDistancePairs(points, connection);
        // System.out.println(res);
        // System.out.println(res.size());

        for (DistancePair connection : res) {
            updateGroups(groups, connection);
        }

        List<Integer> values = countByGroup(groups);
        return values.get(0) * values.get(1) * values.get(2);
    }

    private Map<Point3D, Integer> initateGroup() {
        Map<Point3D, Integer> groups = new HashMap<>();
        int index = 0;
        for (Point3D point : points) {
            groups.put(point, index);
            index++;
        }
        return groups;
    }

    private void updateGroups(final Map<Point3D, Integer> groups, final DistancePair connection) {
        Integer group1 = groups.get(connection.point1);
        Integer group2 = groups.get(connection.point2);

        if (group1 != group2) {

            int oldValueToFind = group2;
            int newValueToSet = group1;

            // Iterate through the entries and update
            for (Map.Entry<Point3D, Integer> entry : groups.entrySet()) {
                if (entry.getValue().equals(oldValueToFind)) {
                    entry.setValue(newValueToSet); // Safely updates the value associated with this key
                }
            }
        }
    }

    private List<Integer> countByGroup(final Map<Point3D, Integer> groups) {
        // System.out.println(groups);

        Map<Integer, Integer> countsMap = new HashMap<>();
        for (Integer value : groups.values()) {
            countsMap.put(value, countsMap.getOrDefault(value, 0) + 1);
        }

        // System.out.println(countsMap);
        List<Integer> values = new ArrayList<>(countsMap.values());
        // System.out.println(values);
        Collections.sort(values, Collections.reverseOrder());
        // System.out.println(values);
        return values;
    }

    @Override
    public Object getAnswer2() {
        long solution = 0;
        Map<Point3D, Integer> groups = initateGroup();

        // System.out.println(groups);

        List<DistancePair> res = Geometry.findShorterDistancePairs(points);


        for (DistancePair connection : res) {
            updateGroups(groups, connection);
            if (countByGroup(groups).get(0) == points.size()) {
                solution = (long)connection.point1.getX() * (long)connection.point2.getX();
                break;
            }
        }


        return solution;
    }

    public static void main(final String[] args) throws IOException {
        Playground puzzle = new Playground("year2025/day08/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }
}
