package day15;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helper.Geometry;
import helper.Puzzle;

public class BeaconExclusionZone extends Puzzle {

    List<Sensor> sensors = new ArrayList<>();
    List<Point> sensorList = new ArrayList<>();
    List<Point> beaconList = new ArrayList<>();


    protected BeaconExclusionZone(final String input) throws IOException {
        super(input);
        Pattern sensorPattern =
                Pattern.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                Matcher sensorMatcher = sensorPattern.matcher(line);
                if (sensorMatcher.find()) {
                    Sensor s = new Sensor(
                            new Point(Integer.parseInt(sensorMatcher.group(1)),
                                    Integer.parseInt(sensorMatcher.group(2))),
                            new Point(Integer.parseInt(sensorMatcher.group(3)),
                                    Integer.parseInt(sensorMatcher.group(4))));
                    sensors.add(s);
                    sensorList.add(new Point(Integer.parseInt(sensorMatcher.group(1)),
                            Integer.parseInt(sensorMatcher.group(2))));
                    beaconList.add(new Point(Integer.parseInt(sensorMatcher.group(3)),
                            Integer.parseInt(sensorMatcher.group(4))));
                }
            }
        }
    }

    public String visualized(final List<Sensor> sensors, final List<Point> sensorList, final List<Point> beaconList) {
        StringBuilder sb = new StringBuilder();
        for (int y = -20; y < 30; y++) {
            for (int x = -20; x < 40; x++) {
                Point p = new Point(x, y);
                if (sensorList.contains(p)) {
                    sb.append('S');
                } else if (beaconList.contains(p)) {
                    sb.append('B');
                } else if (isInRangeOfSensor(p, sensors)) {
                    sb.append('#');
                } else {
                    sb.append('.');
                }
            }
            sb.append('\n');
        }
        return sb.toString();

    }

    private boolean isInRangeOfSensor(final Point p, final List<Sensor> sensors) {
        for (Sensor sensor : sensors) {
            if (!sensorList.contains(p) && !beaconList.contains(p)) {
                if (Geometry.manhattanDistance(sensor.sensor, p) <= sensor.covering) {
                    return true;
                }
            }
        }
        return false;
    }

    private void getOverallRange(final List<Sensor> sensors) {
        int xMax, xMin, yMax, yMin = 0;
        for (Sensor sensor : sensors) {

        }
    }


    @Override
    public Object getAnswer1() {
        String s = visualized(sensors, sensorList, beaconList);
        System.out.println(s);
        int result = 0;
        for (int i = -5; i < 30; i++) {
            Point p = new Point(i, 10);
            if (isInRangeOfSensor(p, sensors)) {
                result++;
            }

        }
        return result;
    }

    @Override
    public Object getAnswer2() {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(final String[] args) throws IOException {
        BeaconExclusionZone beaconExclusionZone = new BeaconExclusionZone("day15/input");
        System.out.println("Answer1: " + beaconExclusionZone.getAnswer1());

        System.out.println("Answer2: " + beaconExclusionZone.getAnswer2());
    }

}
