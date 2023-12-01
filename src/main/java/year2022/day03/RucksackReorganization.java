package year2022.day03;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import helper.Puzzle;

public class RucksackReorganization extends Puzzle {
    List<Rucksack> rucksacks = new ArrayList<>();

    public RucksackReorganization(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                rucksacks.add(new Rucksack(line));
            }
        }
    }

    public int calculateSumOfPriority() {
        int sum = 0;
        for (Rucksack rucksack : rucksacks) {
            List<Character> items = rucksack.findItemInBothCompartiment();

            for (Character item : items) {
                sum += calculatePriority(item);
            }
        }
        return sum;
    }

    public Character findBadge(final Rucksack sack1, final Rucksack sack2, final Rucksack sack3) {
        List<Character> s1 = sack1.getFullContent();
        List<Character> s2 = sack2.getFullContent();
        List<Character> s3 = sack3.getFullContent();

        s1.retainAll(s2);
        s1.retainAll(s3);

        return s1.get(0);
    }


    public int calculateBadgePriority() {
        int sum = 0;
        for (int i = 0; i < rucksacks.size(); i += 3) {
            Character val = findBadge(rucksacks.get(i), rucksacks.get(i + 1), rucksacks.get(i + 2));
            sum += calculatePriority(val);
        }
        return sum;
    }


    private int calculatePriority(final Character item) {
        int value = item;

        if (value >= 97) {
            value -= 96;
        } else {
            value -= 38;
        }
        return value;
    }


    @Override
    public Object getAnswer1() {
        return calculateSumOfPriority();
    }

    @Override
    public Object getAnswer2() {
        return calculateBadgePriority();
    }

    public static void main(final String[] args) throws IOException {

        RucksackReorganization rucksackReorganization = new RucksackReorganization("year2022/day03/input");
        System.out.println("Answer1: " + rucksackReorganization.getAnswer1());
        System.out.println("Answer2: " + rucksackReorganization.getAnswer2());
    }

}
