package day02;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import helper.Puzzle;


public class RockPaperScissors extends Puzzle {
    List<Round> roundsFirstRules = new ArrayList<>();
    List<Round> roundsSecondRules = new ArrayList<>();

    public RockPaperScissors(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ");
                roundsFirstRules.add(Round.setRoundWithFirstRule(split[0], split[1]));
                roundsSecondRules.add(Round.setRoundWithSecondRule(split[0], split[1]));
            }
        }
    }

    public int calculateTotalPoints(final List<Round> rounds) {
        int total = 0;
        for (Round round : rounds) {
            total += round.calculatePoint();
        }
        return total;
    }

    public static void main(final String[] args) throws IOException {

        RockPaperScissors rockPaperScissors = new RockPaperScissors("day02/input");
        System.out.println("Total point First Rule: " + rockPaperScissors.getAnswer1());
        System.out.println("Total point Second Rule: " + rockPaperScissors.getAnswer2());
    }

    @Override
    public Object getAnswer1() {
        return calculateTotalPoints(roundsFirstRules);
    }

    @Override
    public Object getAnswer2() {
        return calculateTotalPoints(roundsSecondRules);
    }
}
