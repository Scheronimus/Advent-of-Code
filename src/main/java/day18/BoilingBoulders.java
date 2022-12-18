package day18;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import helper.Puzzle;

public class BoilingBoulders extends Puzzle {
    List<Cube> cubes = new ArrayList<>();

    protected BoilingBoulders(String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                cubes.add(new Cube(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
            }
        }

        System.out.println(cubes);
    }

    @Override
    public Object getAnswer1() {
        Shape shape = new Shape();

        for (Cube cube : cubes) {
            shape.addCube(cube);
        }
        return shape.side;
    }

    @Override
    public Object getAnswer2() {
        Shape shape = new Shape();

        for (Cube cube : cubes) {
            shape.addCube(cube);
        }
        DrawMax max = shape.getXMax();

        Coloring coloring = new Coloring(cubes, max);
        coloring.color();
        List<Cube> notColored = coloring.getNotColored();

        // Fill the bubble with cube;
        for (Cube cube : notColored) {
            shape.addCube(cube);
        }
        return shape.side;
    }

    public static void main(final String[] args) throws IOException {
        BoilingBoulders boilingBoulders = new BoilingBoulders("day18/input");
        System.out.println("Answer1: " + boilingBoulders.getAnswer1());
        System.out.println("Answer2: " + boilingBoulders.getAnswer2());
    }
}
