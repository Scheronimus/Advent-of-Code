package year2025.day08;

import java.io.IOException;

import testhelper.PuzzleUnitTest;

public class PlaygroundTest extends PuzzleUnitTest {
    public PlaygroundTest() throws IOException {
        super(new Playground("year2025/day08/inputTest"), 40, 25272L);
        ((Playground)this.puzzle).connection = 10; // special setting only for Tests
    }
}
