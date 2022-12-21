package day21;

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
