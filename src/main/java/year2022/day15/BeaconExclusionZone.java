package year2022.day15;

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
    SensorsMap sensorsMap;

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
            sensorsMap = new SensorsMap(sensors, sensorList, beaconList);
        }
    }


    @Override
    public Object getAnswer1() {
        throw new RuntimeException("Use other function!!");
    }

    public Object getAnswer1(int y) {

        // System.out.println(sensorsMap.visualized(0, 20));
        return sensorsMap.getCoveredSpotInY(y);
    }

    @Override
    public Object getAnswer2() {
        throw new RuntimeException("Use other function!!");
    }

    public Object getAnswer2(int min, int max) {
        Point p = sensorsMap.findBlindSpotBetween(min, max);
        return p.x * 4000000L + p.y;
    }

    public static void main(final String[] args) throws IOException {
        BeaconExclusionZone beaconExclusionZone = new BeaconExclusionZone("year2022/day15/input");
        System.out.println("Answer1: " + beaconExclusionZone.getAnswer1(2000000));

        System.out.println("Answer2: " + beaconExclusionZone.getAnswer2(0, 4000000));
    }

}
