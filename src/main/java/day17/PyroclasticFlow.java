package day17;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import helper.Puzzle;

public class PyroclasticFlow extends Puzzle {
    String jetPattern;

    protected PyroclasticFlow(String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                jetPattern = line;
            }
        }
        System.out.println(jetPattern);
    }

    @Override
    public Object getAnswer1() {

        Board b = new Board();

        List<RockShape> rockPatterns = new ArrayList<>();
        rockPatterns.add(RockShape.MINUS);
        rockPatterns.add(RockShape.PLUS);
        rockPatterns.add(RockShape.L);
        rockPatterns.add(RockShape.LINE);
        rockPatterns.add(RockShape.SQUARE);

        int maxNumberOfStone = 2022;

        runSimulation(rockPatterns, maxNumberOfStone, b);

        return b.height;
    }

    private void runSimulation(List<RockShape> rockPatterns, long maxNumberOfStone, Board b) {
        int time = 0;
        for (long numberOfStone = 0; numberOfStone < maxNumberOfStone; numberOfStone++) {
            Rock rock = Rock.createRock(rockPatterns.get((int)(numberOfStone % rockPatterns.size())), (int)b.height);
            boolean falling = true;
            while (falling) {

                char direction = jetPattern.charAt(time % jetPattern.length());
                time++;
                time = time % jetPattern.length();

                if (direction == '<') {
                    if (rock.canMoveLeft(b)) {
                        rock.moveLeft();
                    }
                } else {
                    if (rock.canMoveRight(b)) {
                        rock.moveRight();
                    }
                }
                if (rock.canFall(b)) {
                    rock.fall();
                } else {
                    falling = false;
                }
            }

            b.addRock(rock);

        }
    }

    @Override
    public Object getAnswer2() {
        Board b = new Board();

        List<RockShape> rockPatterns = new ArrayList<>();
        rockPatterns.add(RockShape.MINUS);
        rockPatterns.add(RockShape.PLUS);
        rockPatterns.add(RockShape.L);
        rockPatterns.add(RockShape.LINE);
        rockPatterns.add(RockShape.SQUARE);

        long maxNumberOfStone = 1000000000000L;

        runSimulation(rockPatterns, maxNumberOfStone, b);

        return b.height;
    }


    public static void main(final String[] args) throws IOException {
        PyroclasticFlow pyroclasticFlow = new PyroclasticFlow("day17/input");
        System.out.println("Answer1: " + pyroclasticFlow.getAnswer1());
        System.out.println("Answer2: " + pyroclasticFlow.getAnswer2());

    }

}
