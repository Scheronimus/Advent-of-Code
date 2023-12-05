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

    public static List<Integer> stringToList(final String stringList) {
        List<Integer> list = new ArrayList<>();

        String[] numbers = stringList.split(" +");

        for (String number : numbers) {
            list.add(Integer.parseInt(number));
        }
        return list;
    }

    public static List<Long> stringToLongList(final String stringList) {
        List<Long> list = new ArrayList<>();

        String[] numbers = stringList.split(" +");

        for (String number : numbers) {
            list.add(Long.parseLong(number));
        }
        return list;
    }
}
