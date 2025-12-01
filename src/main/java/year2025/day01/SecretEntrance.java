package year2025.day01;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import helper.Puzzle;


public class SecretEntrance extends Puzzle {
    Dial dial = new Dial();

    public SecretEntrance(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                dial.move(line);
                // System.out.println(line);
            }
        }
    }

    @Override
    public Object getAnswer1() {
        return dial.countZero();
    }


    @Override
    public Object getAnswer2() {
        return dial.crossZero();
    }

    public static void main(final String[] args) throws IOException {
        SecretEntrance puzzle = new SecretEntrance("year2025/day01/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }


}
