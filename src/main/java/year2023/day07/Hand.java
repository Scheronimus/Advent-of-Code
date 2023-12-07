package year2023.day07;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Hand {
    String cards;
    int bid;
    int handType;
    int handTypeV2;

    public Hand(final String cards, final int bid) {
        super();
        this.cards = cards;
        this.bid = bid;
        this.handType = calculateHandType(cards);
        this.handTypeV2 = calculateHandTypeV2(cards);
    }

    public int getHandTypeV2() {
        return handTypeV2;
    }

    @Override
    public String toString() {
        return "Hand [bid=" + bid + ", cards=" + cards + "]";
    }

    public int calculateHandType(final String cards) {
        Map<Character, Integer> map = cardsToMap(cards);

        return getTypeValue(map);
    }

    private Map<Character, Integer> cardsToMap(final String cards) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : cards.toCharArray()) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }

        map = sortByValue(map, false);
        return map;
    }

    public int calculateHandTypeV2(final String cards) {
        Map<Character, Integer> map = cardsToMap(cards);

        Integer jokerNb = map.get('J');
        if (jokerNb == null) {
            jokerNb = 0;
        }

        if (map.size() > 1) {
            Map.Entry<Character, Integer> firstValue =
                    map.entrySet().stream().filter(c -> c.getKey() != 'J').findFirst().get();
            map.put(firstValue.getKey(), firstValue.getValue() + jokerNb);
            map.remove('J');
        }

        return getTypeValue(map);
    }

    private int getTypeValue(final Map<Character, Integer> map) {
        List<Integer> val = new ArrayList<>(map.values());
        if (val.size() == 1) {
            return 7;
        }
        if (val.size() == 2 && val.get(0) == 4) {
            return 6;
        }
        if (val.size() == 2 && val.get(0) == 3) {
            return 5;
        }
        if (val.size() == 3 && val.get(0) == 3) {
            return 4;
        }
        if (val.size() == 3 && val.get(0) == 2) {
            return 3;
        }
        if (val.size() == 4 && val.get(0) == 2) {
            return 2;
        }
        return 1;
    }


    public int getHandType() {
        return handType;
    }

    private static Map<Character, Integer> sortByValue(final Map<Character, Integer> unsortMap, final boolean order) {
        List<Entry<Character, Integer>> list = new LinkedList<>(unsortMap.entrySet());
        list.sort((o1, o2) -> order
                ? o1.getValue().compareTo(o2.getValue()) == 0 ? o1.getKey().compareTo(o2.getKey())
                        : o1.getValue().compareTo(o2.getValue())
                : o2.getValue().compareTo(o1.getValue()) == 0 ? o2.getKey().compareTo(o1.getKey())
                        : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));
    }
}
