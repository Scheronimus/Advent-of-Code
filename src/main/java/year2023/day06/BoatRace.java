package year2023.day06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoatRace {
    double time;
    double recordDistance;


    public BoatRace(final double time, final double recordDistance) {
        super();
        this.time = time;
        this.recordDistance = recordDistance;
    }


    double getDiscriminant() {
        return Math.pow(time, 2) - 4 * recordDistance;
    }

    List<Double> getRacines() {
        List<Double> racines = new ArrayList<>();
        double delta = getDiscriminant();
        double r1 = ((-time) - Math.sqrt(delta)) / -2;
        racines.add(r1);
        double r2 = (-time + Math.sqrt(delta)) / -2;
        racines.add(r2);
        Collections.sort(racines);
        return racines;
    }

    long getNumberOfWaysToBeatRecord() {
        List<Double> racines = getRacines();
        double r1 = racines.get(0);
        double r2 = racines.get(1);

        if (isInteger(r1)) {
            r1 = r1 + 1;
        }

        if (isInteger(r2)) {
            r2 = r2 - 1;
        }

        return (long)(Math.floor(r2) - Math.ceil(r1) + 1);
    }

    boolean isInteger(final double d) {
        return d == Math.floor(d) && !Double.isInfinite(d);
    }
}
