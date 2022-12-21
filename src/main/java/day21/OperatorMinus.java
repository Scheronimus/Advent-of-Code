package day21;

public class OperatorMinus implements IOperator {

    @Override
    public Polynomial run(final Polynomial left, final Polynomial right) {

        return left.minus(right);
    }

    @Override
    public String toString() {
        return "-";
    }
}
