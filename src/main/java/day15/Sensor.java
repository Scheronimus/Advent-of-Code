package day15;

import java.awt.Point;

public class Sensor {
    Point sensor;
    Point closestBeacon;
    Double covering;

    public Sensor(final Point sensor, final Point closestBeacon) {
        super();
        this.sensor = sensor;
        this.closestBeacon = closestBeacon;
        this.covering = sensor.distance(closestBeacon);
    }


}
