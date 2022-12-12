package day01;

import java.io.IOException;

import testhelper.PuzzleUnitTest;

public class CalorieCountingTest extends PuzzleUnitTest {
    public CalorieCountingTest() throws IOException {
        super(new CalorieCounting("day01/test_input"), 24000, 45000);
    }
}
