package year2025.day10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class MinimalVectorSum {

    private int n; // Number of distinct input vectors
    private int s; // Size of each vector
    private List<List<Integer>> vectors; // Original input vectors (V_0, ..., V_{n-1})
    private List<Integer> resultVector; // The target result vector

    /**
     * Represents a state in the BFS search.
     */
    static class State {
        List<Integer> currentSum; // The current vector sum achieved
        int count;               // The number of individual vectors used to reach this sum
        Map<Integer, Integer> xCounts; // How many times each V_i (index) was used

        public State(final List<Integer> currentSum, final int count, final Map<Integer, Integer> xCounts) {
            this.currentSum = currentSum;
            this.count = count;
            this.xCounts = xCounts; // Make a defensive copy if modifying it directly
        }

        // Need good equals and hashCode for List<Integer> in HashMap
        // This is simplified, in a real scenario you might convert List<Integer> to a String or a custom object
        // for optimal map key performance, especially with deeply nested lists.
        // For 's' up to 10, default List.equals/hashCode should be acceptable.
    }

    /**
     * Constructs a MinimalVectorSum solver.
     *
     * @param  inputVectors             A list of lists of integers, where each inner list is a binary vector (0s and
     *                                  1s).
     * @param  inputResult              The target result vector, containing non-negative integers.
     * @throws IllegalArgumentException if inputs are invalid.
     */
    public MinimalVectorSum(final List<List<Integer>> inputVectors, final List<Integer> inputResult) {
        // --- Input Validation (similar to previous version) ---
        if (inputVectors == null || inputResult == null) {
            throw new IllegalArgumentException("Input vectors list or result vector cannot be null.");
        }
        if (inputResult.isEmpty()) {
            throw new IllegalArgumentException("Result vector cannot be empty.");
        }

        this.s = inputResult.size();
        this.n = inputVectors.size();

        if (!inputVectors.isEmpty()) {
            for (List<Integer> vec : inputVectors) {
                if (vec == null || vec.size() != s) {
                    throw new IllegalArgumentException(
                            "All input vectors must be non-null and have the same size 's' as the result vector.");
                }
                for (int val : vec) {
                    if (val != 0 && val != 1) {
                        throw new IllegalArgumentException("Input vectors (V_i) can only contain 0 or 1 values.");
                    }
                }
            }
        }

        for (int val : inputResult) {
            if (val < 0) {
                throw new IllegalArgumentException("Result vector elements must be non-negative.");
            }
        }

        this.vectors = inputVectors;
        this.resultVector = inputResult;
    }

    public Map<Integer, Integer> findMinimalSet() {
        // quick trivial checks
        if (resultVector.stream().allMatch(i -> i == 0)) {
            return new HashMap<>();
        }
        if (n == 0) {
            return new HashMap<>();
        }

        // Convert vectors/result to primitive arrays for performance
        int s = this.s;
        int n = this.n;
        int[][] vec = new int[n][s];
        for (int i = 0; i < n; i++) {
            List<Integer> v = vectors.get(i);
            for (int j = 0; j < s; j++)
                vec[i][j] = v.get(j);
        }
        int[] target = new int[s];
        for (int j = 0; j < s; j++)
            target[j] = resultVector.get(j);

        // Early impossibility check: any position j with target[j] > 0 but all vectors have 0 there
        for (int j = 0; j < s; j++) {
            boolean any = false;
            for (int i = 0; i < n; i++)
                if (vec[i][j] != 0) {
                    any = true;
                    break;
                }
            if (!any && target[j] > 0) {
                return new HashMap<>(); // impossible
            }
        }

        // SPECIAL-CASE: if there are exactly 2 vectors, try algebraic 2x2 solves
        if (n == 2) {
            Map<Integer, Integer> twoSol = trySolveTwoVectors(vec[0], vec[1], target);
            if (!twoSol.isEmpty())
                return twoSol;
            // continue to general solver if algebraic approach didn't produce a valid non-negative integer solution
        }

        // Precompute usefulness info for heuristic: max ones per vector (used for admissible heuristic)
        int maxOnesInVector = 1;
        for (int i = 0; i < n; i++) {
            int ones = 0;
            for (int j = 0; j < s; j++)
                ones += vec[i][j];
            maxOnesInVector = Math.max(maxOnesInVector, Math.max(1, ones));
        }

        // Priority queue node: (estimate = g + h, g=count so far, sum[], counts[])
        class Node {
            int g;
            int est; // g + heuristic
            int[] sum;   // current sum
            int[] counts; // counts per vector

            Node(int g, int est, int[] sum, int[] counts) {
                this.g = g;
                this.est = est;
                this.sum = sum;
                this.counts = counts;
            }
        }

        Comparator<Node> cmp = Comparator.comparingInt(a -> a.est);
        PriorityQueue<Node> pq = new PriorityQueue<>(cmp);

        int[] zeroSum = new int[s];
        int[] zeroCounts = new int[n];
        int zeroH = heuristicRemaining(target, zeroSum, maxOnesInVector);
        pq.offer(new Node(0, zeroH, zeroSum, zeroCounts));

        // visited: map canonical sum key -> minimal g (count) found so far
        Map<String, Integer> bestG = new HashMap<>();
        // store bestCounts for target when found
        int[] bestCountsForTarget = null;
        int bestTotalCount = Integer.MAX_VALUE;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            String key = Arrays.toString(cur.sum);

            // If we've already seen a better g for this sum, skip
            Integer known = bestG.get(key);
            if (known != null && known <= cur.g)
                continue;
            bestG.put(key, cur.g);

            // If cur.g already >= bestTotalCount, skip
            if (cur.g >= bestTotalCount)
                continue;

            // Check goal
            if (Arrays.equals(cur.sum, target)) {
                bestCountsForTarget = cur.counts;
                bestTotalCount = cur.g;
                // We can continue to search for strictly smaller solution but heuristic + PQ usually finds minimal
                // quickly.
                continue;
            }

            // Expand neighbors: try adding each vector
            for (int i = 0; i < n; i++) {
                // Build next sum (inlined and check exceed)
                int[] nextSum = new int[s];
                boolean exceeds = false;
                for (int j = 0; j < s; j++) {
                    int nv = cur.sum[j] + vec[i][j];
                    if (nv > target[j]) {
                        exceeds = true;
                        break;
                    }
                    nextSum[j] = nv;
                }
                if (exceeds)
                    continue;

                int nextG = cur.g + 1;
                // Prune if nextG >= known best for target or other bounds
                if (nextG >= bestTotalCount)
                    continue;

                // heuristic
                int h = heuristicRemaining(target, nextSum, maxOnesInVector);
                int est = nextG + h;
                if (est >= bestTotalCount)
                    continue; // even optimistic estimate no better than best found

                // key check: if we've seen better g to reach nextSum, skip
                String nextKey = Arrays.toString(nextSum);
                Integer knownNextG = bestG.get(nextKey);
                if (knownNextG != null && knownNextG <= nextG)
                    continue;

                // build next counts (copy and increment)
                int[] nextCounts = Arrays.copyOf(cur.counts, n);
                nextCounts[i]++;

                pq.offer(new Node(nextG, est, nextSum, nextCounts));
            }
        }

        if (bestCountsForTarget == null)
            return new HashMap<>();

        // convert counts array to map
        Map<Integer, Integer> out = new HashMap<>();
        for (int i = 0; i < n; i++)
            if (bestCountsForTarget[i] > 0)
                out.put(i, bestCountsForTarget[i]);
        return out;
    }

    /** simple admissible heuristic: remaining total ones divided by max ones per vector (ceil) */
    private int heuristicRemaining(final int[] target, final int[] sum, final int maxOnesPerVector) {
        int rem = 0;
        for (int j = 0; j < target.length; j++)
            rem += (target[j] - sum[j]);
        // each vector adds at most maxOnesPerVector ones total, so need at least ceil(rem / maxOnesPerVector) more
        // vectors
        return (rem + maxOnesPerVector - 1) / maxOnesPerVector;
    }

    /**
     * Attempt to solve when there are exactly two vectors. Returns empty map if no valid non-negative integer solution
     * found. We search for two indices a,b (components) where the 2x2 determinant is non-zero, solve using Cramer's
     * rule, and validate.
     */
    private Map<Integer, Integer> trySolveTwoVectors(int[] v0, int[] v1, int[] target) {
        int s = target.length;
        // Find two rows (components) a,b where the 2x2 matrix [[v0[a], v1[a]], [v0[b], v1[b]]] has nonzero det
        for (int a = 0; a < s; a++) {
            for (int b = a + 1; b < s; b++) {
                int A = v0[a], B = v1[a], C = v0[b], D = v1[b];
                int det = A * D - B * C;
                if (det == 0)
                    continue;
                long tx = target[a], ty = target[b];
                long xNum = tx * D - B * ty;
                long yNum = A * ty - tx * C;
                if (xNum % det != 0 || yNum % det != 0)
                    continue; // need integer solutions
                long x = xNum / det;
                long y = yNum / det;
                if (x < 0 || y < 0)
                    continue;
                // validate across all components
                boolean ok = true;
                for (int j = 0; j < s; j++) {
                    long check = x * v0[j] + y * v1[j];
                    if (check != target[j]) {
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    Map<Integer, Integer> sol = new HashMap<>();
                    if (x > 0)
                        sol.put(0, (int)x);
                    if (y > 0)
                        sol.put(1, (int)y);
                    return sol;
                }
            }
        }
        return new HashMap<>();
    }

    /**
     * Helper to calculate total count from an xCounts map. Used for comparison in BFS.
     */
    private int calculateTotalCount(final Map<Integer, Integer> xCounts) {
        return xCounts.values().stream().mapToInt(Integer::intValue).sum();
    }


    /**
     * Performs component-wise standard integer addition of two vectors.
     *
     * @param  vec1 The first vector (List of Integers).
     * @param  vec2 The second vector (List of Integers, assumed to be binary 0s/1s from input).
     * @return      A new List<Integer> representing the sum of the two input vectors.
     */
    private List<Integer> addVectors(final List<Integer> vec1, final List<Integer> vec2) {
        List<Integer> sum = new ArrayList<>(s);
        for (int i = 0; i < s; i++) {
            sum.add(vec1.get(i) + vec2.get(i));
        }
        return sum;
    }

    public static void main(final String[] args) {
        class TestPrinter {
            void printTest(final String name, final List<List<Integer>> vectors, final List<Integer> result) {
                System.out.println("--- " + name + " ---");
                System.out.println("Input Vectors: " + vectors);
                System.out.println("Target Result: " + result);
                try {
                    MinimalVectorSum solver = new MinimalVectorSum(vectors, result);
                    Map<Integer, Integer> solution = solver.findMinimalSet();

                    int totalCount = solution.values().stream().mapToInt(Integer::intValue).sum();

                    if (solution.isEmpty() && !result.stream().allMatch(i -> i == 0)) {
                        System.out
                                .println("No combination of vectors (with multiple uses) can form the result vector.");
                    } else if (solution.isEmpty() && result.stream().allMatch(i -> i == 0)) {
                        System.out.println("Result vector is all zeros. No vectors are needed. Total count: 0");
                    } else {
                        System.out.println("Minimal vector counts (x_i): " + solution);
                        System.out.println("Total minimal count: " + totalCount);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                System.out.println();
            }
        }
        TestPrinter printer = new TestPrinter();

        // Your specific example that was failing to print correctly and was stated to be solvable
        List<List<Integer>> yourVectors = Arrays.asList(Arrays.asList(0, 1, 0, 1), // V0
                Arrays.asList(0, 0, 1, 1), // V1
                Arrays.asList(1, 0, 1, 0), // V2
                Arrays.asList(1, 1, 0, 0), // V3
                Arrays.asList(0, 0, 0, 1), // V4
                Arrays.asList(0, 0, 1, 0)  // V5
        );
        List<Integer> yourResult = Arrays.asList(3, 5, 4, 7);
        // Let's re-calculate this by hand with the new rule (multiple uses allowed)
        // V0: (0,1,0,1)
        // V1: (0,0,1,1)
        // V2: (1,0,1,0)
        // V3: (1,1,0,0)
        // V4: (0,0,0,1)
        // V5: (0,0,1,0)

        // Try to construct [3, 5, 4, 7]
        // Need 3 at pos 0: V2 (1,0,1,0) and V3 (1,1,0,0) are the only ones with 1 at pos 0.
        // Let's say we use V2 once, V3 twice:
        // V2: (1,0,1,0)
        // V3: (1,1,0,0)
        // V3: (1,1,0,0)
        // Sum so far: (3,2,1,0) -- requires 3 vectors
        // Remaining target: [0,3,3,7]

        // Need 5 at pos 1: V0 (0,1,0,1) and V3 (0,1,0,0) are only ones with 1 at pos 1.
        // We already used V3 twice (contributing 2 to pos 1). Need 3 more at pos 1.
        // Use V0 three times:
        // V0: (0,1,0,1)
        // V0: (0,1,0,1)
        // V0: (0,1,0,1)
        // Sum from V0s: (0,3,0,3)
        // Total sum: (3,2,1,0) + (0,3,0,3) = (3,5,1,3) -- requires 3+3=6 vectors
        // Remaining target: [0,0,3,4]

        // Need 4 at pos 2: V1 (0,0,1,1), V2 (1,0,1,0), V5 (0,0,1,0) are only ones with 1 at pos 2.
        // We already used V2 once (contributing 1 to pos 2). Need 3 more at pos 2.
        // Use V1 three times:
        // V1: (0,0,1,1)
        // V1: (0,0,1,1)
        // V1: (0,0,1,1)
        // Sum from V1s: (0,0,3,3)
        // Total sum: (3,5,1,3) + (0,0,3,3) = (3,5,4,6) -- requires 6+3=9 vectors
        // Remaining target: [0,0,0,1]

        // Need 7 at pos 3: V0 (0,1,0,1), V1 (0,0,1,1), V4 (0,0,0,1) are only ones with 1 at pos 3.
        // We already have 6 at pos 3 (3 from V0s, 3 from V1s). Need 1 more.
        // Use V4 once:
        // V4: (0,0,0,1)
        // Sum from V4s: (0,0,0,1)
        // Total sum: (3,5,4,6) + (0,0,0,1) = (3,5,4,7) -- requires 9+1=10 vectors

        // Solution: V0:3, V1:3, V2:1, V3:2, V4:1, V5:0. Total count: 3+3+1+2+1+0 = 10.
        // This confirms it's solvable, and the previous assertion was wrong due to the changed problem definition.

        printer.printTest("Your Specific Example", yourVectors, yourResult); // Expected: Total minimal count: 10


        // Other examples (adjusting expectations as needed for multiple uses)
        List<List<Integer>> v_ex_multiple = Arrays.asList(Arrays.asList(1, 0), // V0
                Arrays.asList(1, 0), // V1 (same as V0)
                Arrays.asList(0, 1)  // V2
        );
        List<Integer> r_ex_multiple = Arrays.asList(2, 1); // Needs two (1,0) and one (0,1)
        printer.printTest("Example Multiple Vectors", v_ex_multiple, r_ex_multiple); // Expected: {0=1, 1=1, 2=1} or
                                                                                     // {0=2, 2=1} (if V0 and V1 are
                                                                                     // considered distinct but
                                                                                     // identical)


        // Example from before, now with correct interpretation (still minimal)
        List<List<Integer>> v_ex4 = Arrays.asList(Arrays.asList(1, 0, 0), // V0
                Arrays.asList(0, 1, 0), // V1
                Arrays.asList(0, 0, 1), // V2
                Arrays.asList(1, 1, 1)  // V3
        );
        List<Integer> r_ex4 = Arrays.asList(1, 1, 1);
        printer.printTest("Example 4 (Redundant)", v_ex4, r_ex4); // Expected: {3=1} Total count: 1


        // Example: Result requiring sum > 1 at a position (needs multiple V0's)
        List<List<Integer>> v_ex6 = Arrays.asList(Arrays.asList(1, 0, 0), // V0
                Arrays.asList(1, 0, 0), // V1 (another identical vector)
                Arrays.asList(0, 1, 0), // V2
                Arrays.asList(0, 0, 1)  // V3
        );
        List<Integer> r_ex6 = Arrays.asList(2, 1, 1);
        printer.printTest("Example 6 (Multiples)", v_ex6, r_ex6); // Expected: {0=1, 1=1, 2=1, 3=1} Total count: 4

        // Other tests remain similar to previous version as their base logic was fine.
        List<List<Integer>> v_ex1 =
                Arrays.asList(Arrays.asList(1, 0, 1), Arrays.asList(0, 1, 1), Arrays.asList(1, 1, 0));
        List<Integer> r_ex1 = Arrays.asList(1, 1, 2);
        printer.printTest("Example 1", v_ex1, r_ex1); // Expected: {0=1, 1=1} or similar (Total 2)

        List<List<Integer>> v_ex2 = Arrays.asList(Arrays.asList(1, 0), Arrays.asList(0, 1));
        List<Integer> r_ex2 = Arrays.asList(1, 1);
        printer.printTest("Example 2", v_ex2, r_ex2); // Expected: {0=1, 1=1} (Total 2)

        List<List<Integer>> v_ex3 = Arrays.asList(Arrays.asList(0, 0), Arrays.asList(0, 0));
        List<Integer> r_ex3 = Arrays.asList(1, 0);
        printer.printTest("Example 3 (No solution)", v_ex3, r_ex3); // Expected: No combination...

        List<List<Integer>> v_ex5 = Arrays.asList(Arrays.asList(1, 0), Arrays.asList(0, 1));
        List<Integer> r_ex5 = Arrays.asList(0, 0);
        printer.printTest("Example 5 (Trivial)", v_ex5, r_ex5); // Expected: Result is all zeros...

        List<List<Integer>> v_ex7 = Arrays.asList(Arrays.asList(1, 0), Arrays.asList(0, 1));
        List<Integer> r_ex7 = Arrays.asList(2, 2);
        printer.printTest("Example 7 (Unreachable)", v_ex7, r_ex7); // Expected: No combination...

        List<List<Integer>> v_ex8 = new ArrayList<>();
        List<Integer> r_ex8 = Arrays.asList(1, 0);
        printer.printTest("Example 8 (No input vectors)", v_ex8, r_ex8); // Expected: No combination...
    }
}