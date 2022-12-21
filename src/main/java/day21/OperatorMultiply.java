package day21;

public class OperatorMultiply implements IOperator {

    @Override
    public Polynomial run(final Polynomial left, final Polynomial right) {

        return left.times(right);
    }

    @Override
    public String toString() {
        return "*";
    }
}
