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

        // int numberOfStone = 0;
        int time = 0;

        for (int numberOfStone = 0; numberOfStone < 2022; numberOfStone++) {

            Rock rock = Rock.createRock(rockPatterns.get(numberOfStone % rockPatterns.size()), b.height);
            boolean falling = true;
            while (falling) {
                char direction = jetPattern.charAt(time % jetPattern.length());
                time++;

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

        return b.height;
    }

    @Override
    public Object getAnswer2() {
        // TODO Auto-generated method stub
        return null;
    }


    public static void main(final String[] args) throws IOException {
        PyroclasticFlow pyroclasticFlow = new PyroclasticFlow("day17/input");
        System.out.println("Answer1: " + pyroclasticFlow.getAnswer1());
        System.out.println("Answer2: " + pyroclasticFlow.getAnswer2());

    }

}
