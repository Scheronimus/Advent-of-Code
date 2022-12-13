package day13;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import helper.Puzzle;

public class DistressSignal extends Puzzle {

    List<List<List<Object>>> pairs = new ArrayList<>();
    List<List<Object>> fullList = new ArrayList<>();

    protected DistressSignal(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;
            List<List<Object>> pair = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    NestedArrayStringParser parser = new NestedArrayStringParser();
                    List<Object> result = parser.buildList(line);
                    pair.add(result);
                    fullList.add(result);
                    if (pair.size() == 2) {
                        pairs.add(pair);
                        pair = new ArrayList<>();
                    }
                }
            }
        }
    }

    private int compare(final List<Object> list1, final List<Object> list2) {
        int i = 0;
        for (Object object : list1) {
            Object toCompare;
            try {
                toCompare = list2.get(i++);
            } catch (IndexOutOfBoundsException e) {
                return -1;
            }
            if (object instanceof Integer integer1 && toCompare instanceof Integer integer2) {
                int res = compare(integer1, integer2);
                if (res != 0) {
                    return res;
                }
            } else if (object instanceof Integer integer1) {
                List<Object> tempList = new ArrayList<>();
                tempList.add(integer1);
                int res = compare(tempList, (ArrayList<Object>)toCompare);
                if (res != 0) {
                    return res;
                }

            } else if (toCompare instanceof Integer integer2) {
                List<Object> tempList = new ArrayList<>();
                tempList.add(integer2);
                int res = compare((ArrayList<Object>)object, tempList);
                if (res != 0) {
                    return res;
                }

            } else {
                int res = compare((ArrayList<Object>)object, (ArrayList<Object>)toCompare);
                if (res != 0) {
                    return res;
                }
            }

        }
        if (list1.size() == list2.size()) {
            return 0;
        }
        return 1;

    }

    private int compare(final Integer obj1, final Integer obj2) {
        if (obj1 == obj2) {
            return 0;
        } else if (obj1 > obj2) {
            return -1;
        } else if (obj1 < obj2) {
            return 1;
        }
        return 0;
    }

    @Override
    public Object getAnswer1() {
        int index = 1;
        int result = 0;
        for (List<List<Object>> pair : pairs) {
            int res = compare(pair.get(0), pair.get(1));
            if (res == 1) {
                result += index;
            }
            index++;
        }
        return result;
    }

    @Override
    public Object getAnswer2() {
        NestedArrayStringParser parser = new NestedArrayStringParser();
        List<Object> dividerPackets1 = parser.buildList("[[2]]");
        fullList.add(dividerPackets1);

        parser = new NestedArrayStringParser();
        List<Object> dividerPackets2 = parser.buildList("[[6]]");
        fullList.add(dividerPackets2);

        Collections.sort(fullList, (left, right) -> compare(right, left));
        return (fullList.indexOf(dividerPackets1) + 1) * (fullList.indexOf(dividerPackets2) + 1);
    }

    public static void main(final String[] args) throws IOException {
        DistressSignal distressSignal = new DistressSignal("day13/input");
        System.out.println("Answer1: " + distressSignal.getAnswer1());
        System.out.println("Answer2: " + distressSignal.getAnswer2());
    }

}
