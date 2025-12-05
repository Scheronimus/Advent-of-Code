package year2025.day05;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import helper.Puzzle;

public class Cafeteria extends Puzzle {

    // Parsed state here (lists, models, ints, whatever)
    // Example:
    // private List<Integer> numbers;

    private List<Long> ingredientID = new ArrayList<>();
    private List<Range> freshIDRanges = new ArrayList<>();

    public Cafeteria(final String input) throws IOException {
        super(input);
        parse(getLines());
    }

    private void parse(final List<String> lines) {
        for (String line : lines) {

            if (line.contains("-")) {
                String[] values = line.split("-");
                freshIDRanges.add(new Range(Long.parseLong(values[0]), Long.parseLong(values[1])));

            } else if (!line.isEmpty()) {

                ingredientID.add(Long.parseLong(line));
            }
        }
    }

    @Override
    public Object getAnswer1() {
        long count = 0;
        for (Long ingredient : ingredientID) {
            for (Range range : freshIDRanges) {
                if (range.isInRange(ingredient)) {
                    count++;
                    break;
                }
            }

        }
        return count;
    }

    public void sortFreshIDRanges() {
        System.out.println("Original freshIDRanges: " + freshIDRanges);
        Comparator<Range> rangeComparator = Comparator.comparingLong(Range::getMin).thenComparingLong(Range::getMax);
        freshIDRanges.sort(rangeComparator);
        System.out.println("Sorted freshIDRanges: " + freshIDRanges);
    }

    public List<Range> mergeSortedFreshIDRanges(final List<Range> sortedFreshIDRanges) {
        List<Range> mergedFreshIDRanges = new ArrayList<>();
        Range previous = null;

        for (Range range : sortedFreshIDRanges) {
            if (previous == null || !previous.isInRange(range.min)) {
                previous = range;
                mergedFreshIDRanges.add(range);
            } else {
                if (range.max > previous.max) {
                    previous.setMax(range.max);
                }
            }
        }
        return mergedFreshIDRanges;
    }

    @Override
    public Object getAnswer2() {
        sortFreshIDRanges();
        List<Range> mergedFreshIDRanges = mergeSortedFreshIDRanges(freshIDRanges);
        System.out.println("Merged freshIDRanges: " + mergedFreshIDRanges);

        long result = 0;
        for (Range range : mergedFreshIDRanges) {
            result += range.size();
        }
        return result;
    }

    public static void main(final String[] args) throws IOException {
        Cafeteria puzzle = new Cafeteria("year2025/day05/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }
}
