package year2022.day15;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import testhelper.PuzzleUnitTest;

public class BeaconExclusionZoneTest extends PuzzleUnitTest {

    public BeaconExclusionZoneTest() throws IOException {
        super(new BeaconExclusionZone("year2022/day15/test_input"), 26, 56000011L);

    }

    @Override
    @Test
    public void answer1test() {
        if (expectedAnswer1 == null) {
            fail("Expected Result not yet defined");
        }
        assertEquals(expectedAnswer1, ((BeaconExclusionZone)puzzle).getAnswer1(10));
    }

    @Override
    @Test
    public void answer2test() {
        if (expectedAnswer2 == null) {
            fail("Expected Result not yet defined");
        }
        assertEquals(expectedAnswer2, ((BeaconExclusionZone)puzzle).getAnswer2(0, 20));
    }

}
