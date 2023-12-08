package year2023.day08;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helper.Puzzle;


public class HauntedWasteland extends Puzzle {

    String instruction;
    Pattern pattern = Pattern.compile("(\\w+) = \\((\\w+), (\\w+)\\)");
    Map<String, String> left = new HashMap<>();
    Map<String, String> right = new HashMap<>();

    public HauntedWasteland(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {

                    if (line.contains("=")) {
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            left.put(matcher.group(1), matcher.group(2));
                            right.put(matcher.group(1), matcher.group(3));
                        }

                    } else {
                        instruction = line;
                    }
                }
            }
        }

    }

    @Override
    public Object getAnswer1() {
        int index = 0;
        int step = 0;
        // boolean reach;

        String start = "AAA";
        String end = "ZZZ";

        String position = start;

        while (!position.equals(end)) {
            if (instruction.charAt(index) == 'L') {
                position = left.get(position);
            } else {
                position = right.get(position);
            }


            index++;
            if (index >= instruction.length()) {
                index = 0;
            }

            step++;
        }
        return step;
    }


    @Override
    public Object getAnswer2() {
        List<String> starts = left.keySet().stream().filter(key -> key.charAt(2) == 'A').toList();
        System.out.println(starts);

        int index = 0;
        int step = 0;
        List<String> positionList = new ArrayList<>(starts);
        while (!isEnd(positionList)) {
            int i = 0;
            for (String p : positionList) {
                if (instruction.charAt(index) == 'L') {
                    positionList.set(i, left.get(p));
                } else {
                    positionList.set(i, right.get(p));
                }
                i++;
            }


            index++;
            if (index >= instruction.length()) {
                index = 0;
            }

            step++;
        }
        return step;
    }

    boolean isEnd(final List<String> positionList) {
        for (String p : positionList) {
            if (p.charAt(2) != 'Z') {
                return false;
            }
        }
        return true;
    }

    public static void main(final String[] args) throws IOException {
        HauntedWasteland puzzle = new HauntedWasteland("year2023/day08/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }


}
