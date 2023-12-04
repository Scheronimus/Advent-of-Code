package helper;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    public static List<Integer> initializeIntegerList(final int n, final int initialValue) {
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            integerList.add(initialValue);
        }
        return integerList;
    }
}
