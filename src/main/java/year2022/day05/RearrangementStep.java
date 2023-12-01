package year2022.day05;

public class RearrangementStep {
    int quantity;
    int start;
    int end;

    public RearrangementStep(final int quantity, final int start, final int end) {
        super();
        this.quantity = quantity;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "move " + quantity + " from " + start + " to " + end;
    }


}
