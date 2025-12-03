package template;

import java.io.IOException;
import java.util.List;

import helper.Puzzle;

public class Template extends Puzzle {

    // Parsed state here (lists, models, ints, whatever)
    // Example:
    // private List<Integer> numbers;

    public Template(final String input) throws IOException {
        super(input);
        parse(getLines());
    }

    private void parse(List<String> lines) {
        for (String line : lines) {
            // TODO parsing logic
        }
    }

    @Override
    public Object getAnswer1() {
        // TODO use parsed data
        return null;
    }

    @Override
    public Object getAnswer2() {
        // TODO
        return null;
    }

    public static void main(final String[] args) throws IOException {
        Template puzzle = new Template("year2025/dayXX/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }
}
