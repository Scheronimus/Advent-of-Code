package year2022.day14;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import year2022.day14.DrawingInstruction;
import year2022.day14.Point;
import year2022.day14.Slice;

public class SliceTest {

    @Test
    public void testToString() {
        Slice slice = new Slice();
        List<Point> points = new ArrayList<>();
        points.add(new Point(498, 4));
        points.add(new Point(498, 6));
        points.add(new Point(496, 6));
        DrawingInstruction inst = new DrawingInstruction(points);

        List<Point> points2 = new ArrayList<>();
        points2.add(new Point(503, 4));
        points2.add(new Point(502, 4));
        points2.add(new Point(502, 9));
        points2.add(new Point(494, 9));
        DrawingInstruction inst2 = new DrawingInstruction(points2);

        slice.addStones(inst);
        slice.addStones(inst2);
        System.out.println(slice.toString());
    }


}
