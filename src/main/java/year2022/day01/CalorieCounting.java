package year2022.day01;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import helper.Puzzle;

public class CalorieCounting extends Puzzle {

    String input;
    List<Elve> elves = new ArrayList<>();


    public CalorieCounting(final String input) throws IOException {
        super(input);

        Elve elve = new Elve();
        elves.add(elve);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    elve.addCalorie(Integer.parseInt(line));
                } else {
                    elve = new Elve();
                    elves.add(elve);
                }
            }
        }
        Collections.sort(elves, (left, right) -> right.Calories - left.Calories);

    }

    public int getMaxCalorie() {
        return elves.get(0).Calories;
    }

    public int getTop3MaxCalorie() {
        return elves.get(0).Calories + elves.get(1).Calories + elves.get(2).Calories;
    }

    public static void main(final String[] args) throws IOException {

        CalorieCounting calorieCounting = new CalorieCounting("year2022/day01/input");
        System.out.println("Max Calories for a Elve: " + calorieCounting.getAnswer1());
        System.out.println("Max Calories for a top 3 Elve: " + calorieCounting.getAnswer2());
    }

    @Override
    public Object getAnswer1() {
        return getMaxCalorie();
    }

    @Override
    public Object getAnswer2() {
        return getTop3MaxCalorie();
    }
}
