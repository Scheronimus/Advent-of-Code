package day21;

public class OperatorMinus implements IOperator {

    @Override
    public long run(final Long left, final Long right) {

        return left - right;
    }

    @Override
    public String toString() {
        return "-";
    }
}
