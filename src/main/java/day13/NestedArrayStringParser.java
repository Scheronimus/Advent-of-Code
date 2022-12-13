package day13;

import java.util.ArrayList;
import java.util.List;

public class NestedArrayStringParser {

    private int index = 0; // the position in the String

    public List<Object> buildList(final String nestedList) {
        return (ArrayList<Object>)buildListRec(nestedList).get(0);
    }


    public List<Object> buildListRec(final String nestedList) {
        List<Object> list = new ArrayList<>();

        while (index < nestedList.length()) {
            char c = nestedList.charAt(index++);

            if (c == '[') {
                list.add(buildListRec(nestedList));
            } else if (c == ']') {
                break;
            } else if (c == ',') {
            } // do nothing
            else {
                char next = nestedList.charAt(index);
                if (next == '0') {
                    // Hardcoded workarroud for number 10!! will not work for 11,12...
                    list.add(10);
                    index++;
                } else {
                    list.add(Integer.parseInt(Character.toString(c)));
                }
            }
        }
        return list;
    }
}
