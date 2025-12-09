package year2022.day15;

import helper.Geometry;
import helper.Point;

public class Sensor {
    Point sensor;
    Point closestBeacon;
    int covering;

    public Sensor(final Point sensor, final Point closestBeacon) {
        super();
        this.sensor = sensor;
        this.closestBeacon = closestBeacon;
        this.covering = Geometry.manhattanDistance(sensor, closestBeacon);
    }

    public int xMaxRangeIn(final int y) {
        return covering - Math.abs(sensor.y() - y) + sensor.x();
    }


}
