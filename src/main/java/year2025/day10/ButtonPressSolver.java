package year2025.day10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ButtonPressSolver {

    // --- Helper Methods (Static and General Utility) ---

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
     * Performs element-wise addition of two vectors. Assumes vectors are of the same size. Used primarily for BFS where
     * we add button vectors to build up to the target.
     *
     * @param  v1                       The first vector.
     * @param  v2                       The second vector.
     * @return                          A new list representing v1 + v2.
     * @throws IllegalArgumentException if vectors are not of the same size.
     */
    public static List<Integer> addVector(final List<Integer> v1, final List<Integer> v2) {
        if (v1.size() != v2.size()) {
            throw new IllegalArgumentException("Vectors must be of the same size for addition.");
        }
        List<Integer> result = new ArrayList<>(v1.size());
        for (int i = 0; i < v1.size(); i++) {
            result.add(v1.get(i) + v2.get(i));
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

    /**
     * Pruning Heuristic: Checks if it's possible to reduce the expectedState to all zeros using the remaining buttons.
     * Assumes button vectors only contain non-negative integers.
     *
     * @param  expectedState    The state that needs to be reduced to zero.
     * @param  remainingButtons A list of button vectors that can still be used.
     * @return                  true if it's potentially possible, false if it's definitely impossible.
     */
    private static boolean canReachZeroWithRemainingButtons(final List<Integer> expectedState,
            final List<List<Integer>> remainingButtons) {
        if (isAllValueExactZero(expectedState)) {
            return true;
        }
        if (remainingButtons.isEmpty()) {
            return false;
        }
        for (int j = 0; j < expectedState.size(); j++) {
            int targetComponentValue = expectedState.get(j);

            if (targetComponentValue < 0) {
                return false; // Already invalid
            }

            if (targetComponentValue > 0) {
                boolean canReduceThisComponent = false;
                for (List<Integer> buttonVector : remainingButtons) {
                    if (buttonVector.get(j) > 0) { // If button can reduce this component
                        canReduceThisComponent = true;
                        break;
                    }
                }
                if (!canReduceThisComponent) {
                    return false; // No remaining button can reduce this positive component
                }
            }
        }
        return true; // Potentially possible
    }

    /**
     * Pruning Heuristic: Calculates a lower bound for the number of additional presses needed to bring the given state
     * to all zeros. (Manhattan distance, sum of components). Assumes button components are 0 or 1. If button components
     * can be larger, this is a valid but weaker lower bound.
     *
     * @param  state The current state for which to estimate remaining presses.
     * @return       A lower bound for the presses, or a large number if the state is invalid.
     */
    private static int getLowerBound(final List<Integer> state) {
        int sum = 0;
        for (int val : state) {
            if (val < 0) {
                return Integer.MAX_VALUE / 2; // Indicate impossibility
            }
            sum += val;
        }
        return sum;
    }

    // --- Instance Members for DFS ---
    private int globalMin = Integer.MAX_VALUE; // Stores the minimum presses found so far for DFS.

    /**
     * Public entry point for the DFS-based search. Resets globalMin and starts the recursive search.
     *
     * @param  machine     The machine with button definitions.
     * @param  targetState The desired state to reach (which is reduced to zero).
     * @return             The minimum number of presses, or Integer.MAX_VALUE if not reachable.
     */
    public int findMinButtonPressesDFS(final Machine machine, final List<Integer> targetState) {
        this.globalMin = Integer.MAX_VALUE; // Reset globalMin for a new search
        searchMinRecursive(machine, targetState, 0, 0);
        return this.globalMin;
    }

    /**
     * Performs a recursive Depth-First Search to find the minimum number of button presses. This method uses several
     * pruning heuristics to optimize performance.
     *
     * @param machine              The machine containing button vectors.
     * @param currentExpectedState The state that needs to be reduced to zero by subsequent buttons.
     * @param buttonIndex          The index of the current button being considered.
     * @param currentPresses       The total number of button presses made so far in the current path.
     */
    private void searchMinRecursive(final Machine machine, final List<Integer> currentExpectedState,
            final int buttonIndex, final int currentPresses) {

        // Pruning 0: If current path already exceeds or equals the best found solution.
        if (currentPresses >= globalMin) {
            return;
        }

        // Base case: All buttons considered. If currentExpectedState is not zero, this path failed.
        if (buttonIndex >= machine.getButtonsVectors().size()) {
            return;
        }

        // Pruning 1: Feasibility Check - Can remaining buttons even bring currentExpectedState to zero?
        List<List<Integer>> remainingButtons =
                machine.getButtonsVectors().subList(buttonIndex, machine.getButtonsVectors().size());
        if (!canReachZeroWithRemainingButtons(currentExpectedState, remainingButtons)) {
            return;
        }

        // Pruning 2: Lower Bound Check - If current path + estimated future presses already exceeds globalMin.
        // This is applied before even trying to press the current button.
        int estimatedAdditionalPresses = getLowerBound(currentExpectedState);
        if (currentPresses + estimatedAdditionalPresses >= globalMin) {
            return;
        }

        // --- Single Button Left Optimization (specific for 0/1 button components, but generalizable) ---
        if (buttonIndex == machine.getButtonsVectors().size() - 1) { // This is the last button
            List<Integer> lastButtonVector = machine.getButtonsVectors().get(buttonIndex);
            int requiredPressesForLastButton = 0;
            boolean possibleForLastButton = true;
            boolean pressesDetermined = false; // Flag to check if requiredPressesForLastButton is set

            for (int j = 0; j < currentExpectedState.size(); j++) {
                int targetVal = currentExpectedState.get(j);
                int buttonVal = lastButtonVector.get(j);

                if (targetVal < 0) {
                    possibleForLastButton = false; // Invalid state
                    break;
                }

                if (buttonVal == 0) {
                    if (targetVal > 0) {
                        possibleForLastButton = false; // Cannot reduce positive target with zero-effect button
                        break;
                    }
                } else { // buttonVal > 0 (assuming non-negative components)
                    // If buttonVal can be > 1, then: if (targetVal % buttonVal != 0) -> impossible
                    // Also: pressesNeeded = targetVal / buttonVal;
                    // For 0/1 constraint: buttonVal is 1.
                    if (targetVal > 0) {
                        if (!pressesDetermined) {
                            requiredPressesForLastButton = targetVal / buttonVal; // Generalized division
                            pressesDetermined = true;
                        } else if (requiredPressesForLastButton != targetVal / buttonVal) {
                            possibleForLastButton = false; // Conflicting press counts required
                            break;
                        }
                    }
                }
            }

            if (possibleForLastButton) {
                if (currentPresses + requiredPressesForLastButton < globalMin) {
                    globalMin = currentPresses + requiredPressesForLastButton;
                }
            }
            return; // Evaluated this last button scenario, no further recursion needed for this branch.
        }
        // --- END Single Button Left Optimization ---


        List<Integer> buttonVector = machine.getButtonsVectors().get(buttonIndex);

        // Case 1: The current button is not pressed at all (i = 0).
        // Explore this path first.
        searchMinRecursive(machine, currentExpectedState, buttonIndex + 1, currentPresses);

        // Case 2: The current button is pressed 'i' times (i = 1, 2, 3, ...)
        int i = 1;
        while (true) {
            List<Integer> newExpected = subtractVector(currentExpectedState, multiplyVector(buttonVector, i));

            // Pruning 3: If any component goes below zero, this path is invalid.
            if (isOneValueBelowZero(newExpected)) {
                return; // Further increments of 'i' will also be invalid
            }

            // Pruning 4: Lower Bound check for the *new* state after 'i' presses.
            int lowerBoundForNewExpected = getLowerBound(newExpected);
            if (currentPresses + i + lowerBoundForNewExpected >= globalMin) {
                return; // This path is unlikely to be better
            }

            // Check if the target state is reached (all components are zero)
            if (isAllValueExactZero(newExpected)) {
                if (currentPresses + i < globalMin) {
                    globalMin = currentPresses + i;
                }
                // Once a solution is found here, we can stop trying more presses for *this* button
                // as that would only lead to a higher (or same) number of presses.
                return;
            }

            // Recursive call: Explore the next button with the new expected state and updated press count
            searchMinRecursive(machine, newExpected, buttonIndex + 1, currentPresses + i);

            i++; // Try one more press of the current button
        }
    }


    // --- BFS Implementation (Alternative) ---

    // Inner class for BFS state (needs to handle List as key in HashMap, so equals/hashCode)
    private static class BFSState {
        final List<Integer> vector;
        final int presses;

        public BFSState(final List<Integer> vector, final int presses) {
            this.vector = vector;
            this.presses = presses;
        }

        // Necessary for using List<Integer> as a key in HashMap/HashSet
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            BFSState bfsState = (BFSState)o;
            return vector.equals(bfsState.vector);
        }

        @Override
        public int hashCode() {
            return vector.hashCode();
        }
    }

    /**
     * Finds the minimum button presses using a Breadth-First Search (BFS) approach. This method builds up from an
     * initial zero state to the target state.
     *
     * @param  machine     The machine with button definitions.
     * @param  targetState The desired target state.
     * @return             The minimum number of presses, or -1 if the target is not reachable.
     */
    public int findMinButtonPressesBFS(final Machine machine, final List<Integer> targetState) {
        int dimensions = targetState.size();
        List<Integer> initialState = new ArrayList<>(Collections.nCopies(dimensions, 0)); // Start from (0,0,...)

        Queue<BFSState> queue = new LinkedList<>();
        // Use a map to store the minimum presses to reach a vector,
        // which implicitly serves as the visited set.
        Map<List<Integer>, Integer> visited = new HashMap<>();

        BFSState startState = new BFSState(initialState, 0);
        queue.offer(startState);
        visited.put(initialState, 0);

        while (!queue.isEmpty()) {
            BFSState current = queue.poll();

            // CORRECTED LINE: Use List.equals() directly
            if (current.vector.equals(targetState)) {
                return current.presses; // Found the shortest path!
            }

            // For each available button
            for (List<Integer> buttonVector : machine.getButtonsVectors()) {
                List<Integer> nextVector = addVector(current.vector, buttonVector);

                // Pruning 1 (BFS specific): Check if we overshoot the target in any dimension.
                if (isGreaterThanTarget(nextVector, targetState)) {
                    continue; // This path won't lead to the target without overshooting
                }

                // Pruning 2 (BFS specific): Check if this state has been visited.
                // In BFS, the first time we visit a state, it's the shortest path.
                // The List.equals() and List.hashCode() methods are implicitly used by HashMap here.
                if (!visited.containsKey(nextVector)) {
                    int nextPresses = current.presses + 1;
                    BFSState nextState = new BFSState(nextVector, nextPresses);
                    queue.offer(nextState);
                    visited.put(nextVector, nextPresses);
                }
            }
        }

        return -1; // Target state not reachable
    }

    /**
     * Helper for BFS: Checks if any component of 'current' vector exceeds 'target' vector.
     *
     * @param  current The current vector state.
     * @param  target  The target vector state.
     * @return         true if any component in current is greater than its counterpart in target, false otherwise.
     */
    private static boolean isGreaterThanTarget(final List<Integer> current, final List<Integer> target) {
        if (current.size() != target.size()) {
            throw new IllegalArgumentException("Vectors must be of the same size for comparison.");
        }
        for (int i = 0; i < current.size(); i++) {
            if (current.get(i) > target.get(i)) {
                return true;
            }
        }
        return false;
    }
}