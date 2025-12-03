package template;

import java.io.IOException;
import java.util.List;

import helper.Puzzle;


public class Template extends Puzzle {

    public Template(final String input) throws IOException {
        super(input);
        List<String> lines = getLines();

        for (String line : lines) {
            System.out.println(line);
            // TODO parse
        }
    }

    @Override
    public Object getAnswer1() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Object getAnswer2() {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(final String[] args) throws IOException {
        Template puzzle = new Template("year2025/dayXX/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }


}
