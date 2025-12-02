package year2025.day02;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import helper.Puzzle;


public class GiftShop extends Puzzle {

    List<String> rangeList;

    public GiftShop(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                // System.out.println("--> " + line);
                rangeList = new ArrayList<>(Arrays.asList(line.split(",")));

            }
            // System.out.println(rangeList);

        }
    }

    @Override
    public Object getAnswer1() {
        long count = 0;
        for (String range : rangeList) {
            String[] val = range.split("-");
            long start = Long.parseLong(val[0]);
            long end = Long.parseLong(val[1]);
            System.out.println(start + "--> " + end);

            for (long i = start; i <= end; i++) {

                String s = String.valueOf(i);
                if (s.length() % 2 == 0) {
                    String part1 = s.substring(0, s.length() / 2);
                    String part2 = s.substring(s.length() / 2, s.length());
                    System.out.println(part1 + "--> " + part2);
                    if (part1.equals(part2)) {
                        count += i;
                    }
                }
            }
        }


        return count;
    }

    public static Set<String> splitStringIntoXParts(final String s, final int x) {

        int partLength = s.length() / x;
        Set<String> parts = new HashSet<>();

        for (int i = 0; i < x; i++) {
            int startIndex = i * partLength;
            int endIndex = startIndex + partLength;
            parts.add(s.substring(startIndex, endIndex));
        }

        return parts;
    }

    @Override
    public Object getAnswer2() {
        List<Long> memory = new ArrayList<>();
        long count = 0;
        for (String range : rangeList) {
            String[] val = range.split("-");
            long start = Long.parseLong(val[0]);
            long end = Long.parseLong(val[1]);
            System.out.println(start + "--> " + end);

            for (long i = start; i <= end; i++) {

                String s = String.valueOf(i);
                int len = s.length();

                for (int k = 2; k <= len; k++) {
                    if (s.length() % k == 0) {

                        Set<String> parts = splitStringIntoXParts(s, k);
                        if (!memory.contains(i) && parts.size() == 1) {
                            count += i;
                            memory.add(i);

                        }
                    }
                }
            }
        }
        return count;
    }

    public static void main(final String[] args) throws IOException {
        GiftShop puzzle = new GiftShop("year2025/day02/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }


}
