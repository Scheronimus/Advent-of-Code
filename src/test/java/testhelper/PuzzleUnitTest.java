package testhelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import helper.Puzzle;

public abstract class PuzzleUnitTest {
    protected Object expectedAnswer1;
    protected Object expectedAnswer2;
    protected Puzzle puzzle;
    protected Puzzle puzzle2;

    public PuzzleUnitTest(final Puzzle puzzle, final Object expectedAnswer1, final Object expectedAnswer2)
            throws IOException {
        this(puzzle, puzzle, expectedAnswer1, expectedAnswer2);
    }

    public PuzzleUnitTest(final Puzzle puzzle1, final Puzzle puzzle2, final Object expectedAnswer1,
            final Object expectedAnswer2) throws IOException {
        this.puzzle = puzzle1;
        this.puzzle2 = puzzle2;
        this.expectedAnswer1 = expectedAnswer1;
        this.expectedAnswer2 = expectedAnswer2;
    }


    @Test
    public void answer1test() {
        if (expectedAnswer1 == null) {
            fail("Expected Result not yet defined");
        }
        assertEquals(expectedAnswer1, puzzle.getAnswer1());
    }

    @Test
    public void answer2test() {
        if (expectedAnswer2 == null) {
            fail("Expected Result not yet defined");
        }
        assertEquals(expectedAnswer2, puzzle2.getAnswer2());
    }
}
