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
        return null;

    }

    @Override
    public Object getAnswer1() {
        int result = 0;
        for (int i = -5; i < 30; i++) {
            Point p = new Point(i, 11);
            for (Sensor sensor : sensors) {
                if (!sensorList.contains(p) && !beaconList.contains(p)) {
                    if (sensor.sensor.distance(p) <= sensor.covering) {
                        result++;
                        break;
                    }
                }
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
