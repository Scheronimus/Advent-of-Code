package year2025.day03;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import helper.Puzzle;


public class Lobby extends Puzzle {

    List<String> banks = new ArrayList<>();
    long joltageSum = 0;
    long joltageSum12D = 0;

    public Lobby(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {

                banks.add(line);
                int value = findLargestJoltage(line);
                joltageSum += value;
                long value12D = findLargestJoltage12Digit(line);
                joltageSum12D += value12D;
            }
        }
    }

    int findLargestJoltage(final String bank) {

        char first = (char)bank.substring(0, bank.length() - 1).chars().max().getAsInt();
        int index = bank.indexOf(first);
        char second = (char)bank.substring(index + 1, bank.length()).chars().max().getAsInt();
        String number = "" + first + second;

        return Integer.parseInt(number);
    }

    long findLargestJoltage12Digit(final String bank) {

        String number = "";
        int indexStart = 0;
        for (int i = 0; i < 12; i++) {
            char max = (char)bank.substring(indexStart, bank.length() - 11 + i).chars().max().getAsInt();
            int index = bank.substring(indexStart, bank.length() - 11 + i).indexOf(max);
            indexStart = indexStart + index + 1;
            number += max;
        }

        return Long.parseLong(number);
    }

    @Override
    public Object getAnswer1() {
        return joltageSum;
    }


    @Override
    public Object getAnswer2() {
        return joltageSum12D;
    }

    public static void main(final String[] args) throws IOException {
        Lobby puzzle = new Lobby("year2025/day03/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }
}
