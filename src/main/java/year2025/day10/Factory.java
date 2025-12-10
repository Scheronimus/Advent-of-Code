package year2025.day10;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import helper.Puzzle;
import year2025.day10.part1.ButtonResolver;

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

    /**
     * Performs element-wise subtraction of two vectors. Assumes vectors are of the same size.
     * 
     * @param  v1                       The first vector (minuend).
     * @param  v2                       The second vector (subtrahend).
     * @return                          A new list representing v1 - v2.
     * @throws IllegalArgumentException if vectors are not of the same size.
     */
    public static List<Integer> subtractVector(final List<Integer> v1, final List<Integer> v2) {
        if (v1.size() != v2.size()) {
            throw new IllegalArgumentException("Vectors must be of the same size for subtraction.");
        }
        List<Integer> result = new ArrayList<>(v1.size());
        for (int i = 0; i < v1.size(); i++) {
            result.add(v1.get(i) - v2.get(i));
        }
        return result;
    }

    /**
     * Multiplies a vector by a scalar (integer).
     * 
     * @param  vector The vector to multiply.
     * @param  scalar The integer scalar.
     * @return        A new list representing vector * scalar.
     */
    public static List<Integer> multiplyVector(final List<Integer> vector, final int scalar) {
        List<Integer> result = new ArrayList<>(vector.size());
        for (int i = 0; i < vector.size(); i++) {
            result.add(vector.get(i) * scalar);
        }
        return result;
    }

    /**
     * Checks if at least one value in the vector is below zero.
     * 
     * @param  vector The vector to check.
     * @return        true if any element is less than 0, false otherwise.
     */
    public static boolean isOneValueBelowZero(final List<Integer> vector) {
        for (int value : vector) {
            if (value < 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if all values in the vector are exactly zero.
     * 
     * @param  vector The vector to check.
     * @return        true if all elements are 0, false otherwise.
     */
    public static boolean isAllValueExactZero(final List<Integer> vector) {
        for (int value : vector) {
            if (value != 0) {
                return false;
            }
        }
        return true;
    }

    // New helper method for the pruning condition
    /**
     * Checks if it's possible to reduce the expectedState to all zeros using the remaining buttons. Assumes button
     * vectors only contain non-negative integers.
     *
     * @param  expectedState    The state that needs to be reduced to zero.
     * @param  remainingButtons A list of button vectors that can still be used.
     * @return                  true if it's potentially possible, false if it's definitely impossible.
     */
    private static boolean canReachZeroWithRemainingButtons(final List<Integer> expectedState,
            final List<List<Integer>> remainingButtons) {
        // If the expectedState is already all zeros, it's reachable.
        if (isAllValueExactZero(expectedState)) {
            return true;
        }

        // If there are no remaining buttons, and expectedState is not zero, it's not reachable.
        if (remainingButtons.isEmpty()) {
            return false;
        }

        // For each component 'j' in the expectedState:
        for (int j = 0; j < expectedState.size(); j++) {
            int targetComponentValue = expectedState.get(j);

            if (targetComponentValue > 0) {
                // If a component is positive, we NEED at least one remaining button
                // to have a positive value in that component to reduce it to zero.
                boolean canReduceThisComponent = false;
                for (List<Integer> buttonVector : remainingButtons) {
                    if (buttonVector.get(j) > 0) {
                        canReduceThisComponent = true;
                        break;
                    }
                }
                if (!canReduceThisComponent) {
                    // No remaining button can reduce this positive component, so it's impossible.
                    return false;
                }
            }
            // If targetComponentValue is 0, no action is needed for this component.
            // If targetComponentValue is < 0, it should have been caught by isOneValueBelowZero earlier.
            // This check assumes we only call it when expectedState has no negative values.
        }

        return true; // It's potentially possible (no obvious impossibility found)
    }

    int globalMin = Integer.MAX_VALUE;

    public void searchMin(final Machine machine, final List<Integer> expectedState, final int buttonIndex,
            final int currentMin) {

        // System.out.println(expectedState);
        // System.out.println(buttonIndex);
        // System.out.println(currentMin);

        if (buttonIndex >= machine.getButtonsVectors().size()) {
            // no more Button --> expectedState not reached
            return;
        }
        if (currentMin >= globalMin) {
            // purnBranche as no better min can be found
            return;
        }
        List<List<Integer>> remainingButtons =
                machine.getButtonsVectors().subList(buttonIndex, machine.getButtonsVectors().size());
        if (!canReachZeroWithRemainingButtons(expectedState, remainingButtons)) {
            // System.out.println("Pruned: Cannot reach zero with remaining buttons for expectedState: " +
            // expectedState);
            return;
        }

        if (buttonIndex == machine.getButtonsVectors().size() - 1) { // This is the last button
            List<Integer> lastButtonVector = machine.getButtonsVectors().get(buttonIndex);
            int requiredPresses = 0;
            boolean possible = true;
            boolean firstComponentToDeterminePresses = true; // To track if we've set requiredPresses yet

            for (int j = 0; j < expectedState.size(); j++) {
                int targetVal = expectedState.get(j);
                int buttonVal = lastButtonVector.get(j); // Will be 0 or 1

                if (targetVal < 0) {
                    // This state should have been pruned earlier by isOneValueBelowZero
                    // but as a final check.
                    possible = false;
                    break;
                }

                if (buttonVal == 0) {
                    if (targetVal > 0) {
                        // Button cannot reduce this positive target component to 0. Impossible.
                        possible = false;
                        break;
                    }
                    // If targetVal == 0 and buttonVal == 0, this component is satisfied.
                } else { // buttonVal must be 1 (due to 0/1 constraint)
                    if (targetVal == 0) {
                        // This component is already 0, and buttonVal is 1.
                        // This means this button is *not needed* for this component.
                        // If requiredPresses was already set by another component,
                        // this creates a conflict if requiredPresses > 0,
                        // meaning we can't simultaneously press 'requiredPresses' and also 0 for this component.
                        // This is the tricky part. If one component needs 5 presses, and another needs 0,
                        // this single button cannot satisfy both unless 0 presses are needed overall.
                        // We will allow this to pass, but `firstComponentToDeterminePresses` logic handles it.
                    } else { // targetVal > 0 and buttonVal == 1
                        // This component needs 'targetVal' presses.
                        if (firstComponentToDeterminePresses) {
                            requiredPresses = targetVal;
                            firstComponentToDeterminePresses = false;
                        } else if (requiredPresses != targetVal) {
                            // Another component demanded a different number of presses. Impossible.
                            possible = false;
                            break;
                        }
                    }
                }
            }

            // After checking all components, if still possible and if some presses were required:
            if (possible) {
                // If all components were 0 OR all components with buttonVal=1 required 0 presses,
                // then requiredPresses would still be 0.
                // This means expectedState was already (0,0,...,0), or could be reached with 0 presses
                // from this button.
                // If expectedState was already all zeros, `isAllValueExactZero` should have caught it.
                // So `requiredPresses` here will be the actual presses if a solution was found.
                if (currentMin + requiredPresses < globalMin) {
                    globalMin = currentMin + requiredPresses;
                    System.out.println("Opti. Bingo " + globalMin);
                }
            }
            // In either case (possible or not), we've fully evaluated this branch with the last button.
            return;
        }

        List<Integer> buttonVector = machine.getButtonsVectors().get(buttonIndex);
        int i = 0;
        searchMin(machine, expectedState, buttonIndex + 1, currentMin);
        i++;
        while (true) {
            if (currentMin + i >= globalMin) {
                return;
            }

            List<Integer> newExpected = subtractVector(expectedState, multiplyVector(buttonVector, i));
            if (isOneValueBelowZero(newExpected)) {
                // Busted
                return;
            }
            if (isAllValueExactZero(newExpected)) {
                // Bingo BUT could go over globalMin
                if (currentMin + i < globalMin) {

                    System.out.println("Bingo " + (currentMin + i));
                    globalMin = currentMin + i;
                    return;
                } else {
                    // busted
                    return;
                }
            }

            searchMin(machine, newExpected, buttonIndex + 1, currentMin + i);
            i++;
        }

    }

    @Override
    public Object getAnswer2() {


        long solution = 0;
        // ButtonResolver resolver = new ButtonResolver();
        ButtonPressSolverBFS solverDFS = new ButtonPressSolverBFS();
        for (Machine machine : machines) {
            System.out.println("Machine: " + machine);


            // searchMin(machine, machine.getJoltageVector(), 0, 0);

            int minPresses = solverDFS.findMinButtonPressesBFS(machine, machine.getJoltageVector());

            solution += minPresses;
        }


        return solution;

    }

    public static void main(final String[] args) throws IOException {
        Factory puzzle = new Factory("year2025/day10/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }
}
