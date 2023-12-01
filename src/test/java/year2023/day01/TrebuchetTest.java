package year2023.day01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import testhelper.PuzzleUnitTest;

public class TrebuchetTest extends PuzzleUnitTest {


    public TrebuchetTest() throws IOException {
        super(new Trebuchet("year2023/day01/test_input"), 142, 281);
    }


    @Test
    @Override
    public void answer2test() {
        if (expectedAnswer2 == null) {
            fail("Expected Result not yet defined");
        }
        try {
            Trebuchet puzzle2 = new Trebuchet("year2023/day01/test_input2");
            assertEquals(expectedAnswer2, puzzle2.getAnswer2());
        } catch (IOException e) {
            fail();
        }

    }

    @Test
    public void testGetCalibartionValue() {
        // throw new RuntimeException("not yet implemented");

        int val = Trebuchet.getCalibartionValue("12a599sd36jlou");
        System.out.println(val);
    }

}
