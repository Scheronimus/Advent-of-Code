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
import year2022.day17.LoopDetection;


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

        int index = 0;
        int step = 0;
        List<String> positionList = new ArrayList<>(starts);
        List<LoopDetection> loopDetectors = new ArrayList<>();
        for (int i = 0; i < positionList.size(); i++) {
            loopDetectors.add(new LoopDetection());
        }
        List<Boolean> detected = new ArrayList<>();
        for (int i = 0; i < positionList.size(); i++) {
            detected.add(false);
        }

        List<Integer> loop = new ArrayList<>();
        for (int i = 0; i < positionList.size(); i++) {
            loop.add(0);
        }

        while (!isEnd(detected)) {
            int i = 0;
            for (String p : positionList) {
                if (detected.get(i)) {
                    i++;
                    continue;
                }
                if (instruction.charAt(index) == 'L') {
                    positionList.set(i, left.get(p));
                } else {
                    positionList.set(i, right.get(p));
                }
                if (positionList.get(i).charAt(2) == 'Z') {
                    Object res = loopDetectors.get(i).detectLoop(new Snapshot(positionList.get(i), index, step + 1));
                    if (res != null) {
                        loop.set(i, (step + 1 - ((Snapshot)res).step));
                        detected.set(i, true);
                    } else {
                        loopDetectors.get(i).addItem(new Snapshot(positionList.get(i), index, step + 1));
                    }
                }
                i++;
            }


            index++;
            if (index >= instruction.length()) {
                index = 0;
            }

            step++;
        }


        Integer gcd = loop.get(0);
        for (int l : loop) {
            gcd = gcd(gcd, l);
        }


        long res = gcd;
        for (int l : loop) {
            res *= (l / gcd);
        }

        return res;
    }

    int gcd(int n1, int n2) {
        int gcd = 1;
        for (int i = 1; i <= n1 && i <= n2; i++) {
            if (n1 % i == 0 && n2 % i == 0) {
                gcd = i;
            }
        }
        return gcd;
    }

    boolean isEnd(final List<Boolean> detected) {
        for (Boolean d : detected) {
            if (!d) {
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
