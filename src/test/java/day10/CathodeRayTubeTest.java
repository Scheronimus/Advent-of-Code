package day10;

import java.io.IOException;

import testhelper.PuzzleUnitTest;

public class CathodeRayTubeTest extends PuzzleUnitTest {

    public CathodeRayTubeTest() throws IOException {
        super(new CathodeRayTube("day10/test_input"), 13140,
                "##..##..##..##..##..##..##..##..##..##..\r\n" + "###...###...###...###...###...###...###.\r\n"
                        + "####....####....####....####....####....\r\n"
                        + "#####.....#####.....#####.....#####.....\r\n"
                        + "######......######......######......####\r\n" + "#######.......#######.......#######.....");

    }


}
