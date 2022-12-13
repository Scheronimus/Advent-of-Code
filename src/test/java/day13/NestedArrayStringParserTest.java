package day13;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NestedArrayStringParserTest {

    @Test
    public void testBuildList() {
        NestedArrayStringParser parser = new NestedArrayStringParser();


        List<Object> res;

        res = parser.buildList("[1,[2,[3,[4,[5,6,7]]]],8,9]");
        Assertions.assertEquals("[1, [2, [3, [4, [5, 6, 7]]]], 8, 9]", res.toString());

        parser = new NestedArrayStringParser();
        res = parser.buildList("[3,[4,3],5]");
        Assertions.assertEquals("[3, [4, 3], 5]", res.toString());

        parser = new NestedArrayStringParser();
        res = parser.buildList("[[],[10,3,10,9]]");
        Assertions.assertEquals("[[], [10, 3, 10, 9]]", res.toString());

    }


}
