package helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuadraticEquationSolver {
    double a;
    double b;
    double c;

    public QuadraticEquationSolver(double a, double b, double c) {
        super();
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getDiscriminant() {
        return Math.pow(b, 2) - 4 * a * c;
    }

    public List<Double> getRoots() {
        List<Double> roots = new ArrayList<>();
        double delta = getDiscriminant();
        if (delta > 0) {
            double r1 = ((-b) + Math.sqrt(delta)) / 2 * a;
            roots.add(r1);
            double r2 = ((-b) - Math.sqrt(delta)) / 2 * a;
            roots.add(r2);

        } else if (delta == 0) {
            double r = (-b) / 2 * a;
            roots.add(r);
        } else {
            // no real root
            throw new UnsupportedOperationException("complex solutions not yet implemented");
        }

        Collections.sort(roots);
        return roots;
    }
}
