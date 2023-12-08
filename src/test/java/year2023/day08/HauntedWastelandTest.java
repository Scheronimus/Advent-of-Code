package year2023.day08;

import java.io.IOException;

import testhelper.PuzzleUnitTest;

public class HauntedWastelandTest extends PuzzleUnitTest {
    public HauntedWastelandTest() throws IOException {
        super(new HauntedWasteland("year2023/day08/test_input"), new HauntedWasteland("year2023/day08/test_input2"), 6,
                6L);
    }
}
