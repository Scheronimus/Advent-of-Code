package year2023.day11;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import helper.Puzzle;


public class CosmicExpansion extends Puzzle {
    SpaceImage spaceImage;

    public CosmicExpansion(final String input) throws IOException {
        super(input);
        List<String> image = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                image.add(line);

            }
            spaceImage = new SpaceImage(image);


        }
    }

    @Override
    public Object getAnswer1() {
        List<Galaxy> galaxies = spaceImage.getAllGalaxies(1);
        return getSumOfAllDistance(galaxies);
    }


    @Override
    public Object getAnswer2() {
        List<Galaxy> galaxies = spaceImage.getAllGalaxies(999999);
        return getSumOfAllDistance(galaxies);
    }

    private long getSumOfAllDistance(final List<Galaxy> galaxies) {
        long res = 0;
        int index = 0;
        for (Galaxy galaxy : galaxies) {

            for (int i = index + 1; i < galaxies.size(); i++) {
                res += galaxy.getDistanceTo(galaxies.get(i));
            }
            index++;
        }
        return res;
    }

    public static void main(final String[] args) throws IOException {
        CosmicExpansion puzzle = new CosmicExpansion("year2023/day11/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }


}
