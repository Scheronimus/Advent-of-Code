package day21;

public class OperatorDivide implements IOperator {


    @Override
    public Polynomial run(final Polynomial left, final Polynomial right) {
        if (right.getDegree() > 0) {
            throw new IllegalArgumentException("Division by polynome of degree 1 or more is not supported");
        }
        return left.divideBy(right.getCoef(0));
    }

    @Override
    public String toString() {
        return "/";
    }

}

