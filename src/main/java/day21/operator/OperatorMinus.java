package day21.operator;

import helper.Polynomial;

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
