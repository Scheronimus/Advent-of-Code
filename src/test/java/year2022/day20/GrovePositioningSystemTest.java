package year2022.day20;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import testhelper.PuzzleUnitTest;

public class GrovePositioningSystemTest extends PuzzleUnitTest {

    public GrovePositioningSystemTest() throws IOException {
        super(new GrovePositioningSystem("year2022/day20/test_input"), 3, 1623178306L);

    }

    private Value val(int i) {
        return new Value(i);
    }

    private List<Value> toList(int... values) {
        List<Value> file = new ArrayList<>();
        for (int value : values) {
            file.add(val(value));
        }
        return file;

    }


    @Test
    @Disabled
    public void testProcessValue1() {
        List<Value> file = toList(4, 5, 6, 1, 7, 8, 9);
        List<Value> res = ((GrovePositioningSystem)puzzle).processValue(file.get(3), file);
        List<Value> expected = toList(4, 5, 6, 7, 1, 8, 9);
        Assertions.assertEquals(expected, res);
    }

    @Test
    @Disabled
    public void testProcessValue2() {

        List<Value> file = toList(4, -2, 5, 6, 7, 8, 9);
        List<Value> res = ((GrovePositioningSystem)puzzle).processValue(file.get(1), file);
        List<Value> expected = toList(4, 5, 6, 7, 8, -2, 9);
        Assertions.assertEquals(expected, res);

    }

    @Test
    @Disabled
    public void testProcessValue3() {
        List<Value> file = toList(4, -2, 5, 6, 7, 8, 7);
        List<Value> res = ((GrovePositioningSystem)puzzle).processValue(file.get(6), file);
        List<Value> expected = toList(4, 5, 6, 7, 8, -2, 6);
        Assertions.assertEquals(expected, res);
    }


}
