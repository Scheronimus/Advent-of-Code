package year2025.day10;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import helper.Puzzle;

public class Factory extends Puzzle {

    public Factory(final String input) throws IOException {
        super(input);
        parse(getLines());
    }

    // This list will store all the parsed Machine objects
    private List<Machine> machines = new ArrayList<>();


    private void parse(final List<String> lines) {
        for (String line : lines) {
            String[] splitted = line.split(" ");

            String lightsExpectation = "";
            List<String> buttons = new ArrayList<>();
            String joltage = "";
            for (String value : splitted) {
                if (value.contains("[")) {
                    lightsExpectation = value;
                } else if (value.contains("(")) {
                    buttons.add(value);
                } else if (value.contains("{")) {
                    joltage = value;
                } else {
                    System.err.println("SOMETHING WRONG!");
                }


            }
            // Create a new Machine object with the parsed values
            Machine newMachine = new Machine(lightsExpectation, buttons, joltage);
            machines.add(newMachine); // Add the machine to your list

            // You can still print it here if you want to see the parsed object immediately
            // System.out.println("Parsed Machine: " + newMachine);
            // System.out.println("---"); // Separator for clarity
        }
    }

    @Override
    public Object getAnswer1() {
        long solution = 0;
        ButtonResolver resolver = new ButtonResolver();

        for (Machine machine : machines) {
            System.out.println("Machine: " + machine);
            int minPresses =
                    resolver.findMinButtonPresses(machine.getLightsExpectationMask(), machine.getButtonsMasks());

            System.out.println("MinPress: " + minPresses);
            solution += minPresses;
        }


        return solution;
    }

    @Override
    public Object getAnswer2() {
        // TODO
        return null;
    }

    public static void main(final String[] args) throws IOException {
        Factory puzzle = new Factory("year2025/day10/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }
}
