package day21;

public class OperatorDivide implements IOperator {


    @Override
    public long run(final Long left, final Long right) {
        if (right == 0) {
            throw new IllegalArgumentException("Division by 0 not allowed");
        }
        return left / right;
    }

    @Override
    public String toString() {
        return "/";
    }

}

