package day14;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import helper.Puzzle;

public class RegolithReservoir extends Puzzle {
    // List<DrawingInstruction> drawingInstructions = new ArrayList<>();
    Slice slice = new Slice();

    protected RegolithReservoir(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                // System.out.println(line);
                List<Point> pointOrder = new ArrayList<>();
                String[] split = line.split("->");
                for (String value : split) {
                    value = value.trim();
                    String[] split2 = value.split(",");
                    pointOrder.add(new Point(Integer.parseInt(split2[0]), Integer.parseInt(split2[1])));
                }
                // drawingInstructions.add(new DrawingInstruction(pointOrder));
                slice.addStones(new DrawingInstruction(pointOrder));
            }
            System.out.println(slice);
        }
    }

    @Override
    public Object getAnswer1() {
        int count = 0;

        while (slice.dropSand(slice.source)) {
            count++;
        }

        System.out.println(slice);
        return count;
    }

    @Override
    public Object getAnswer2() {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(final String[] args) throws IOException {
        RegolithReservoir regolithReservoir = new RegolithReservoir("day14/input");
        System.out.println("Answer1: " + regolithReservoir.getAnswer1());
        System.out.println("Answer2: " + regolithReservoir.getAnswer2());
    }

}
