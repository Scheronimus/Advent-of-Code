package day21;

import java.io.IOException;

import helper.Puzzle;
import testhelper.PuzzleUnitTest;

public class MonkeyMathTest extends PuzzleUnitTest {

    public MonkeyMathTest(final Puzzle puzzle, final Object expectedAnswer1, final Object expectedAnswer2)
            throws IOException {
        super(new MonkeyMath("day21/test_input"), 152, null);
    }

}
