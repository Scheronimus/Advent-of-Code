package year2022.day22;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import year2022.day22.MoveForward;

public class MoveForwardTest {

    @Test
    public void testWrapArround() {
        MoveForward m = new MoveForward(10);
        int[] val = new int[] { 3, 9 };
        int res = m.wrapArround(5 + 15, val);

        Assertions.assertEquals(6, res);
    }

    @Test
    public void testWrapArround2() {
        MoveForward m = new MoveForward(10);
        int[] val = new int[] { 1, 5 };
        int res = m.wrapArround(3 + 3, val);

        Assertions.assertEquals(1, res);
    }

    @Test
    public void testWrapArroundNegative() {
        MoveForward m = new MoveForward(10);
        int[] val = new int[] { 1, 5 };
        int res = m.wrapArround(3 - 6, val);

        Assertions.assertEquals(2, res);
    }

}
