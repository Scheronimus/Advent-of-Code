package year2023.day01;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import helper.Puzzle;


public class Trebuchet extends Puzzle {

    protected static String[] numberName = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
    List<String> calibrationStrings = new ArrayList<>();


    public Trebuchet(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                calibrationStrings.add(line);
            }
        }
    }

    static int getCalibrationValue(final String s) {
        Stream<Character> charStream = new String(s.toCharArray()).chars().mapToObj(i -> (char)i);
        List<Character> digitList = charStream.filter(Character::isDigit).toList();

        if (!digitList.isEmpty()) {
            String temp = "" + digitList.get(0) + digitList.get(digitList.size() - 1);
            return Integer.valueOf(temp);
        }
        return -1;
    }

    static int getCalibrationValue2(final String s) {

        List<Integer> digitList = new ArrayList<>();
        int index = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                digitList.add(Integer.valueOf("" + c));
            } else {
                int in = 1;
                for (String num : numberName) {
                    if (s.startsWith(num, index)) {
                        digitList.add(in);
                        break;
                    }
                    in++;
                }
            }
            index++;
        }

        if (!digitList.isEmpty()) {
            return 10 * digitList.get(0) + digitList.get(digitList.size() - 1);
        }

        return -1;
    }


    @Override
    public Object getAnswer1() {
        int sum = 0;
        for (String s : calibrationStrings) {
            sum += getCalibrationValue(s);
        }
        return sum;
    }

    @Override
    public Object getAnswer2() {
        int sum = 0;
        for (String s : calibrationStrings) {
            sum += getCalibrationValue2(s);
        }
        return sum;
    }

    public static void main(final String[] args) throws IOException {
        Trebuchet trebuchet = new Trebuchet("year2023/day01/input");
        System.out.println("sum of calibration values: " + trebuchet.getAnswer1());
        System.out.println("sum of calibration values: " + trebuchet.getAnswer2());
    }
}
