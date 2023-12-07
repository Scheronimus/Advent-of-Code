package year2023.day07;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import helper.Puzzle;


public class CamelCards extends Puzzle {

    List<Hand> hands = new ArrayList<>();

    public CamelCards(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" ");

                hands.add(new Hand(splitted[0].trim(), Integer.parseInt(splitted[1].trim())));
            }
        }

        // System.out.println(hands);
        // System.out.println(hands.get(0).getHandType());
    }

    int charToValue(final Character c) {

        if (Character.isDigit(c)) {
            return Integer.parseInt(c.toString());
        } else if (c == 'T') {
            return 10;
        } else if (c == 'J') {
            return 11;
        } else if (c == 'Q') {
            return 12;
        } else if (c == 'K') {
            return 13;
        } else if (c == 'A') {
            return 14;
        }

        throw new UnsupportedOperationException();
    }

    int charToValueV2(final Character c) {

        if (Character.isDigit(c)) {
            return Integer.parseInt(c.toString());
        } else if (c == 'T') {
            return 10;
        } else if (c == 'J') {
            return 1;
        } else if (c == 'Q') {
            return 12;
        } else if (c == 'K') {
            return 13;
        } else if (c == 'A') {
            return 14;
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public Object getAnswer1() {
        Collections.sort(hands, (left, right) -> {
            if (left.getHandType() != right.getHandType()) {
                return left.getHandType() - right.getHandType();
            } else {
                for (int i = 0; i < 5; i++) {
                    if (charToValue(left.cards.charAt(i)) != charToValue(right.cards.charAt(i))) {
                        return charToValue(left.cards.charAt(i)) - charToValue(right.cards.charAt(i));
                    }
                }
            }
            return 0;
        });

        // System.out.println(hands);
        return getTotalWinnings(hands);
    }


    @Override
    public Object getAnswer2() {
        Collections.sort(hands, (left, right) -> {
            if (left.getHandTypeV2() != right.getHandTypeV2()) {
                return left.getHandTypeV2() - right.getHandTypeV2();
            } else {
                for (int i = 0; i < 5; i++) {
                    if (charToValueV2(left.cards.charAt(i)) != charToValueV2(right.cards.charAt(i))) {
                        return charToValueV2(left.cards.charAt(i)) - charToValueV2(right.cards.charAt(i));
                    }
                }
            }
            return 0;
        });

        // System.out.println(hands);
        return getTotalWinnings(hands);
    }

    private int getTotalWinnings(final List<Hand> hands) {
        int res = 0;
        int i = 1;
        for (Hand hand : hands) {
            res += i * hand.bid;
            i++;
        }
        return res;
    }

    public static void main(final String[] args) throws IOException {
        CamelCards puzzle = new CamelCards("year2023/day07/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }
}
