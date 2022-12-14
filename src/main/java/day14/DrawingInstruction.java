package day14;

import java.util.List;

public class DrawingInstruction {
    List<Point> pointOrder;

    public DrawingInstruction(final List<Point> pointOrder) {
        super();
        this.pointOrder = pointOrder;
    }

    @Override
    public String toString() {
        return pointOrder.toString();
    }

}
