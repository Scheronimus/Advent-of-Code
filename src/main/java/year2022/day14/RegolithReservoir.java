package year2022.day14;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import helper.Puzzle;

public class RegolithReservoir extends Puzzle {

    Slice slice = new Slice();

    protected RegolithReservoir(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {

                List<Point> pointOrder = new ArrayList<>();
                String[] split = line.split("->");
                for (String value : split) {
                    value = value.trim();
                    String[] split2 = value.split(",");
                    pointOrder.add(new Point(Integer.parseInt(split2[0]), Integer.parseInt(split2[1])));
                }
                slice.addStones(new DrawingInstruction(pointOrder));
            }
        }
    }

    @Override
    public Object getAnswer1() {
        int count = 0;

        while (slice.dropSand(slice.source)) {
            count++;
        }

        // System.out.println(slice);
        return count;
    }

    @Override
    public Object getAnswer2() {
        int count = 0;
        List<Point> points = new ArrayList<>();
        points.add(new Point(500 - (slice.bottomRight.getY() + 2), slice.bottomRight.getY() + 2));
        points.add(new Point(500 + (slice.bottomRight.getY() + 2), slice.bottomRight.getY() + 2));

        slice.addStones(new DrawingInstruction(points));
        slice.source.setY(slice.source.getY() - 1);
        while (slice.dropSand(slice.source)) {
            count++;
        }

        // System.out.println(slice);
        return count;
    }

    public static void main(final String[] args) throws IOException {
        RegolithReservoir regolithReservoir = new RegolithReservoir("year2022/day14/input");
        System.out.println("Answer1: " + regolithReservoir.getAnswer1());
        regolithReservoir = new RegolithReservoir("year2022/day14/input");
        System.out.println("Answer2: " + regolithReservoir.getAnswer2());
    }

}
