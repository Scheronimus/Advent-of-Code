package year2025.day10;

// ButtonPressSolverBFS.java (BFS Version - Updated)
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Represents a state in the Breadth-First Search. Contains the current vector (state) and the number of presses to
 * reach it.
 */
class BFSState {
    final List<Integer> vector;
    final int presses;

    public BFSState(final List<Integer> vector, final int presses) {
        this.vector = vector;
        this.presses = presses;
    }

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
 * A class to solve the minimum button presses problem using a Breadth-First Search (BFS) approach. This method builds
 * up from an initial zero state to the target state. Button vector components are assumed to be non-negative.
 */
public class ButtonPressSolverBFS {

    // --- Helper Methods (Static and General Utility for BFS) ---

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

    // Heuristic: Checks if it's possible to reach the target from the current state
    // with *any* combination of remaining buttons (not specific remaining buttons for DFS context).
    // For BFS, we always have "all" buttons remaining.
    private static boolean canReachTargetWithAnyRemainingButtons(final List<Integer> currentState,
            final List<Integer> targetState, final List<List<Integer>> allButtonVectors) {
        // If current state already equals target, it's reachable
        if (currentState.equals(targetState)) {
            return true;
        }

        // If current state overshoots target, it's not reachable
        if (isGreaterThanTarget(currentState, targetState)) {
            return false;
        }

        // For each component 'j':
        for (int j = 0; j < targetState.size(); j++) {
            int neededValue = targetState.get(j) - currentState.get(j);

            if (neededValue > 0) { // If we still need to increase this component
                boolean canIncreaseThisComponent = false;
                for (List<Integer> buttonVector : allButtonVectors) {
                    if (buttonVector.get(j) > 0) {
                        canIncreaseThisComponent = true;
                        break;
                    }
                }
                if (!canIncreaseThisComponent) {
                    // No button can increase this component, so it's impossible to reach target
                    return false;
                }
            }
            // If neededValue <= 0, this component is already at or past target.
            // If current state already passed target, `isGreaterThanTarget` check should catch it.
        }
        return true; // Potentially possible
    }

    // Heuristic: Lower Bound Calculation (Manhattan Distance to target)
    private static int getLowerBoundToTarget(final List<Integer> currentState, final List<Integer> targetState) {
        int sumDifference = 0;
        for (int i = 0; i < currentState.size(); i++) {
            int diff = targetState.get(i) - currentState.get(i);
            if (diff < 0) {
                // If we've already overshot, it implies an invalid path for BFS
                return Integer.MAX_VALUE / 2;
            }
            sumDifference += diff;
        }
        return sumDifference;
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
        Map<List<Integer>, Integer> visited = new HashMap<>();

        BFSState startState = new BFSState(initialState, 0);
        queue.offer(startState);
        visited.put(initialState, 0);

        System.out.println("\n--- Starting BFS Search for Target: " + targetState + " ---");
        System.out.println("\n--- " + machine + " ---");
        int maxQueueSize = 0; // For debugging BFS memory
        int statesVisitedCount = 0;

        List<List<Integer>> allButtonVectors = machine.getButtonsVectors(); // All buttons are always "remaining" for
                                                                            // BFS

        while (!queue.isEmpty()) {
            maxQueueSize = Math.max(maxQueueSize, queue.size());
            BFSState current = queue.poll();
            statesVisitedCount++;

            // Debug print for current state being processed
            // System.out.println("Processing: State=" + current.vector + ", Presses=" + current.presses);

            // Solution Check
            if (current.vector.equals(targetState)) {
                System.out.println("  BFS found solution: " + current.presses + " presses. States visited: "
                        + statesVisitedCount + ". Max queue size: " + maxQueueSize);
                return current.presses;
            }

            // Heuristic Pruning (BFS specific): Check if it's even possible to reach the target from current state
            // This can prune paths early if no button can contribute to a needed component.
            if (!canReachTargetWithAnyRemainingButtons(current.vector, targetState, allButtonVectors)) {
                // System.out.println(
                // " -- BFS Pruned (Feasibility Check: Cannot reach target from " + current.vector + ")");
                continue; // Prune this path
            }

            // Heuristic Pruning (BFS specific): Lower Bound check
            // If current presses + estimated additional presses already exceeds a known solution (if using A* style
            // globalMin)
            // Or if it's simply too far to reach within reasonable bounds (e.g., if we had an upper limit for presses)
            // For pure BFS, this is less critical for correctness but can prune paths that would overshoot target in
            // later steps.
            // It mostly overlaps with `isGreaterThanTarget` and `canReachTargetWithAnyRemainingButtons` in pure BFS.
            int lowerBound = getLowerBoundToTarget(current.vector, targetState);
            // This specific lower bound isn't directly used to prune in pure BFS because it doesn't have a `globalMin`
            // to compare against directly.
            // In A*, it would determine priority. Here, we can use it to detect very "bad" states.
            // Example: If target is [10,10] and current is [0,0], lowerBound is 20. If current is [9,0], lowerBound is
            // 11.
            // If a state's lower bound implies it will take more presses than a known solution (if we had one from
            // another search)
            // or if the lower bound implies it's already over current.presses + 1, it's not useful in BFS.
            // However, it can be used for logging or more advanced A* type pruning.
            // For now, let's just log it.
            // System.out.println(" Lower Bound to Target: " + lowerBound);


            // For each available button
            for (List<Integer> buttonVector : allButtonVectors) {
                List<Integer> nextVector = addVector(current.vector, buttonVector);

                // Pruning (BFS specific): Check if we overshoot the target in any dimension
                if (isGreaterThanTarget(nextVector, targetState)) {
                    // System.out.println(" -- Pruned (Overshot Target: " + nextVector + " > " + targetState + ")");
                    continue; // This path won't lead to the target without overshooting
                }

                // Pruning (BFS specific): Check if this state has been visited.
                // In BFS, the first time we visit a state, it's the shortest path.
                if (!visited.containsKey(nextVector)) {
                    int nextPresses = current.presses + 1;
                    BFSState nextState = new BFSState(nextVector, nextPresses);
                    queue.offer(nextState);
                    visited.put(nextVector, nextPresses);
                    // System.out.println(" -- Enqueued: State=" + nextVector + ", Presses=" + nextPresses);
                } else {
                    // System.out.println(
                    // " -- Skipped (Visited): State=" + nextVector + ", Presses=" + (current.presses + 1)
                    // + " (already visited with " + visited.get(nextVector) + " presses)");
                }
            }
        }

        System.out.println("BFS could not reach target. States visited: " + statesVisitedCount + ". Max queue size: "
                + maxQueueSize);
        return -1; // Target state not reachable
    }
}