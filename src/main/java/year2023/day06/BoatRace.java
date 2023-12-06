package year2023.day06;

import java.util.List;

import helper.QuadraticEquationSolver;

public class BoatRace {

    QuadraticEquationSolver solver;


    public BoatRace(final double time, final double recordDistance) {
        super();

        // (x)(time-x)>recordDistance
        // -x^2+x*time-recordDistance>0
        solver = new QuadraticEquationSolver(-1, time, -recordDistance);
    }


    long getNumberOfWaysToBeatRecord() {
        List<Double> roots = solver.getRoots();

        double r1 = roots.get(0);
        double r2 = roots.get(1);


        // The equation is strict so we need to check if the root are integer to exclude them
        if (isInteger(r1)) {
            r1 = r1 + 1;
        }

        if (isInteger(r2)) {
            r2 = r2 - 1;
        }

        // return the number of integer included between the 2 roots.
        return (long)(Math.floor(r2) - Math.ceil(r1) + 1);
    }

    boolean isInteger(final double d) {
        return d == Math.floor(d) && !Double.isInfinite(d);
    }
}
