package year2023.day01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import testhelper.PuzzleUnitTest;

public class TrebuchetTest extends PuzzleUnitTest {


    public TrebuchetTest() throws IOException {
        super(new Trebuchet("year2023/day01/test_input"), new Trebuchet("year2023/day01/test_input2"), 142, 281);
    }


    @Test
    public void testGetCalibartionValue() {
        // throw new RuntimeException("not yet implemented");

        int val = Trebuchet.getCalibartionValue("12a599sd36jlou");
        assertEquals(16, val);
    }

    @Test
    public void testGetCalibartionValue2() {
        // throw new RuntimeException("not yet implemented");

        int val = Trebuchet.getCalibartionValue2("five92a599sd36jlouonetz");
        assertEquals(51, val);
    }

}
