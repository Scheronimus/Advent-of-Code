package year2022.day13;

import java.util.ArrayList;
import java.util.List;

public class NestedArrayStringParser {

    private int index = 0; // the position in the String

    @SuppressWarnings("unchecked")
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
                if (Character.isDigit(next)) {
                    try {
                        if (Character.isDigit(nestedList.charAt(index + 1))) {
                            // number with more than 2 digit is not supported
                            throw new RuntimeException("Number with more than 2 digit is not supported");
                        }
                    } catch (IndexOutOfBoundsException e) {
                        // Ignore
                    }

                    list.add(Integer.parseInt("" + c + next));
                    index++;
                } else {
                    list.add(Integer.parseInt(Character.toString(c)));
                }
            }
        }
        return list;
    }
}
