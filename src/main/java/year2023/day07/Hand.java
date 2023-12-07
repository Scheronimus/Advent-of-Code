package year2023.day07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
        List<Entry<Character, Integer>> map = cardsToMap(cards);

        return getTypeValue(map);
    }

    private List<Entry<Character, Integer>> cardsToMap(final String cards) {


        Map<Character, Integer> map = new HashMap<>();
        for (char c : cards.toCharArray()) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }

        List<Entry<Character, Integer>> list = new ArrayList<>(map.entrySet());

        Collections.sort(list, (left, right) -> {
            return right.getValue() - left.getValue();
        });

        return list;
    }

    public int calculateHandTypeV2(final String cards) {
        List<Entry<Character, Integer>> map = cardsToMap(cards);
        int indexJoker = findJokerIndex(map);

        if (map.size() > 1 && indexJoker >= 0) {
            int jokerNb = map.get(indexJoker).getValue();
            map.remove(indexJoker);
            map.get(0).setValue(map.get(0).getValue() + jokerNb);
        }

        return getTypeValue(map);
    }

    private int findJokerIndex(List<Entry<Character, Integer>> map) {
        int indexJoker = -1;
        int i = 0;
        for (Entry<Character, Integer> entry : map) {
            if (entry.getKey() == 'J') {
                indexJoker = i;
                break;
            }
            i++;
        }
        return indexJoker;
    }

    private int getTypeValue(final List<Entry<Character, Integer>> val) {
        if (val.size() == 1) {
            return 7;
        }
        if (val.size() == 2 && val.get(0).getValue() == 4) {
            return 6;
        }
        if (val.size() == 2 && val.get(0).getValue() == 3) {
            return 5;
        }
        if (val.size() == 3 && val.get(0).getValue() == 3) {
            return 4;
        }
        if (val.size() == 3 && val.get(0).getValue() == 2) {
            return 3;
        }
        if (val.size() == 4 && val.get(0).getValue() == 2) {
            return 2;
        }
        return 1;
    }


    public int getHandType() {
        return handType;
    }
}
