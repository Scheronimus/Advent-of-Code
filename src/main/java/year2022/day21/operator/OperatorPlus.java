package year2022.day21.operator;

import helper.Polynomial;

public class OperatorPlus implements IOperator {

    @Override
    public Polynomial run(final Polynomial left, final Polynomial right) {

        return left.plus(right);
    }

    @Override
    public String toString() {
        return "+";
    }

}
