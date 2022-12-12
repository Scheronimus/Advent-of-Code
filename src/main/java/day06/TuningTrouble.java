package day06;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import helper.Puzzle;

public class TuningTrouble extends Puzzle {
    String signal;

    public TuningTrouble(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                signal = line;
            }
        }
    }

    boolean uniqueCharacters(final String str) {
        // If at any time we encounter 2 same
        // characters, return false
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j < str.length(); j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    return false;
                }
            }
        }
        // If no duplicate characters encountered,
        // return true
        return true;
    }

    int findMarker(final String s, final int packageLength) {
        for (int i = 0; i < s.length() - packageLength; i++) {
            if (uniqueCharacters(s.substring(i, i + packageLength))) {
                return i + packageLength;
            }
        }
        return -1;
    }

    @Override
    public Object getAnswer1() {
        return findMarker(signal, 4);
    }

    @Override
    public Object getAnswer2() {
        return findMarker(signal, 14);
    }

    public static void main(final String[] args) throws IOException {
        TuningTrouble tuningTrouble = new TuningTrouble("day06/input");
        System.out.println("Answer1: " + tuningTrouble.getAnswer1());
        System.out.println("Answer2: " + tuningTrouble.getAnswer2());
    }

}
