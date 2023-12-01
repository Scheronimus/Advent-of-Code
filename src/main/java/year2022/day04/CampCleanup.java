package year2022.day04;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import helper.Puzzle;

public class CampCleanup extends Puzzle {
    List<List<Elve>> listOfpairs = new ArrayList<>();

    public CampCleanup(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                List<Elve> pair = new ArrayList<>();
                String[] splitted = line.split(",");
                String[] subsplitted = splitted[0].split("-");
                Elve elve1 = new Elve(Integer.parseInt(subsplitted[0]), Integer.parseInt(subsplitted[1]));
                pair.add(elve1);
                subsplitted = splitted[1].split("-");
                Elve elve2 = new Elve(Integer.parseInt(subsplitted[0]), Integer.parseInt(subsplitted[1]));
                pair.add(elve2);
                listOfpairs.add(pair);
            }
        }
    }

    private int findPairInSameRange() {
        int result = 0;
        for (List<Elve> pair : listOfpairs) {
            if (pair.get(0).contains(pair.get(1)) || pair.get(1).contains(pair.get(0))) {
                result++;
            }
        }
        return result;
    }

    private int findOverlapping() {
        int result = 0;
        for (List<Elve> pair : listOfpairs) {
            if (pair.get(0).overlapWith(pair.get(1))) {
                result++;
            }
        }
        return result;
    }

    @Override
    public Object getAnswer1() {
        return findPairInSameRange();
    }

    @Override
    public Object getAnswer2() {
        return findOverlapping();
    }

    public static void main(final String[] args) throws IOException {

        CampCleanup campCleanup = new CampCleanup("year2022/day04/input");
        System.out.println("Answer1: " + campCleanup.getAnswer1());
        System.out.println("Answer2: " + campCleanup.getAnswer2());
    }

}
