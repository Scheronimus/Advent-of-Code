package day15;

import java.awt.Point;

import helper.Geometry;

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


}
