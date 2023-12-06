package year2023.day06;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import helper.ListUtils;
import helper.Puzzle;


public class WaitForIt extends Puzzle {

    List<Integer> time;
    List<Integer> distance;

    public WaitForIt(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains("Time")) {
                    time = ListUtils.stringToList(line.replace("Time:", "").trim());
                } else if (line.contains("Distance")) {
                    distance = ListUtils.stringToList(line.replace("Distance:", "").trim());
                }
            }
        }
    }

    @Override
    public Object getAnswer1() {
        int res = 1;
        for (int i = 0; i < time.size(); i++) {
            BoatRace b = new BoatRace(time.get(i), distance.get(i));
            res *= b.getNumberOfWaysToBeatRecord();
        }
        return res;
    }


    @Override
    public Object getAnswer2() {
        String newTimeString = "";
        String newDistanceString = "";
        for (int i = 0; i < time.size(); i++) {
            newTimeString = newTimeString.concat(time.get(i).toString());
            newDistanceString = newDistanceString.concat(distance.get(i).toString());
        }

        BoatRace b = new BoatRace(Long.parseLong(newTimeString), Long.parseLong(newDistanceString));
        return b.getNumberOfWaysToBeatRecord();
    }

    public static void main(final String[] args) throws IOException {
        WaitForIt puzzle = new WaitForIt("year2023/day06/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }
}
