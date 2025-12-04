package year2025.day04;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import helper.Puzzle;

public class PrintingDepartment extends Puzzle {

    RollPaperMap rollPaperMap;

    public PrintingDepartment(final String input) throws IOException {
        super(input);
        parse(getLines());
    }

    private void parse(final List<String> lines) {
        rollPaperMap = new RollPaperMap(lines);
    }

    private List<AbstractMap.SimpleEntry<Integer, Integer>> findRollToRemove() {
        int width = rollPaperMap.getMapWidth();
        int height = rollPaperMap.getMapHeight();

        List<AbstractMap.SimpleEntry<Integer, Integer>> toRemove = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (rollPaperMap.getContentAt(x, y) == '@') {
                    List<Character> adjacentChar = rollPaperMap.getAdjacentOf(x, y);
                    long rollSymbolCountInAdjacent = adjacentChar.stream().filter(c -> c == '@').count();
                    if (rollSymbolCountInAdjacent < 4) {
                        toRemove.add(new AbstractMap.SimpleEntry<>(x, y));
                    }
                }

            }
        }
        return toRemove;
    }

    private void updateRollMap(final List<AbstractMap.SimpleEntry<Integer, Integer>> rollsToRemove) {
        for (SimpleEntry<Integer, Integer> roll : rollsToRemove) {
            rollPaperMap.setContentAt(roll.getKey(), roll.getValue(), '.');
        }
    }

    @Override
    public Object getAnswer1() {
        long result = 0;
        int width = rollPaperMap.getMapWidth();
        int height = rollPaperMap.getMapHeight();


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (rollPaperMap.getContentAt(x, y) == '@') {
                    List<Character> adjacentChar = rollPaperMap.getAdjacentOf(x, y);
                    long rollSymbolCountInAdjacent = adjacentChar.stream().filter(c -> c == '@').count();
                    if (rollSymbolCountInAdjacent < 4) {
                        result++;
                    }
                }

            }
        }
        return result;
    }

    @Override
    public Object getAnswer2() {
        long result = 0;
        boolean workInProgress = true;

        while (workInProgress) {

            List<AbstractMap.SimpleEntry<Integer, Integer>> rollsToRemove = findRollToRemove();

            if (rollsToRemove.size() != 0) {
                result += rollsToRemove.size();
                updateRollMap(rollsToRemove);
            } else {
                workInProgress = false;
            }
        }
        return result;
    }

    public static void main(final String[] args) throws IOException {
        PrintingDepartment puzzle = new PrintingDepartment("year2025/day04/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }
}
