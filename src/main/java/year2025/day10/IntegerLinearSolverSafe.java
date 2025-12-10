package year2025.day10;

import java.math.BigInteger;
import java.util.*;

/**
 * IntegerLinearSolverSafe
 *
 * Solves A * x = b over non-negative integers minimizing sum(x_i), where
 * A is given as a list of column vectors (List<List<Integer>>),
 * b is the target vector (List<Integer>).
 *
 * This version handles large counts safely using BigInteger.
 */
public class IntegerLinearSolverSafe {

    public static Map<Integer, BigInteger> solve(List<List<Integer>> vectors, List<Integer> result) {
        if (vectors == null || result == null) throw new IllegalArgumentException("null input");
        int m = result.size();
        int n = vectors.size();
        if (m == 0) return new HashMap<>();

        // Build augmented matrix of rationals
        Rational[][] aug = new Rational[m][n + 1];
        for (int i = 0; i < m; i++) {
            aug[i][n] = Rational.of(result.get(i));
        }
        for (int j = 0; j < n; j++) {
            List<Integer> col = vectors.get(j);
            if (col.size() != m) throw new IllegalArgumentException("vector size mismatch");
            for (int i = 0; i < m; i++) aug[i][j] = Rational.of(col.get(i));
        }

        // RREF
        RREF.Result rrefRes = RREF.rref(aug);
        Rational[][] rref = rrefRes.rref;
        int rankA = rrefRes.rankA;
        int rank = rrefRes.rank;

        if (rank != rankA) return new HashMap<>(); // no solution

        // Identify pivot and free columns
        boolean[] isPivotCol = new boolean[n];
        int[] pivotRowForCol = new int[n];
        Arrays.fill(pivotRowForCol, -1);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!rref[i][j].isZero()) {
                    isPivotCol[j] = true;
                    pivotRowForCol[j] = i;
                    break;
                }
            }
        }
        List<Integer> pivotCols = new ArrayList<>();
        List<Integer> freeCols = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            if (isPivotCol[j]) pivotCols.add(j);
            else freeCols.add(j);
        }
        int nullDim = n - pivotCols.size();

        // Particular solution: free vars = 0
        Rational[] xPart = new Rational[n];
        for (int j = 0; j < n; j++) xPart[j] = Rational.ZERO;
        for (int j : pivotCols) {
            int i = pivotRowForCol[j];
            xPart[j] = rref[i][n];
        }

        // Nullspace rational basis
        Rational[][] nullBasisRational = new Rational[nullDim][n];
        for (int k = 0; k < nullDim; k++) {
            int f = freeCols.get(k);
            for (int j = 0; j < n; j++) nullBasisRational[k][j] = Rational.ZERO;
            nullBasisRational[k][f] = Rational.ONE;
            for (int j : pivotCols) {
                int i = pivotRowForCol[j];
                nullBasisRational[k][j] = rref[i][f].negate();
            }
        }

        // Convert to BigInteger matrix
        BigInteger[][] nullBasisInt = new BigInteger[nullDim][n];
        for (int k = 0; k < nullDim; k++) {
            // LCM of denominators
            BigInteger lcm = BigInteger.ONE;
            for (int j = 0; j < n; j++) lcm = lcm.multiply(nullBasisRational[k][j].denominator).divide(lcm.gcd(nullBasisRational[k][j].denominator));
            for (int j = 0; j < n; j++)
                nullBasisInt[k][j] = nullBasisRational[k][j].numerator.multiply(lcm).divide(nullBasisRational[k][j].denominator);
        }

        // Particular solution as BigInteger
        BigInteger lcmPart = BigInteger.ONE;
        for (int j = 0; j < n; j++) lcmPart = lcmPart.multiply(xPart[j].denominator).divide(lcmPart.gcd(xPart[j].denominator));
        BigInteger[] xPartInt = new BigInteger[n];
        for (int j = 0; j < n; j++) xPartInt[j] = xPart[j].numerator.multiply(lcmPart).divide(xPart[j].denominator);

        // Enumerate small nullspace (simple DFS)
        BigInteger bestSum = null;
        int[] bestT = null;
        if (nullDim == 0) {
            // Unique solution
            bestSum = BigInteger.ZERO;
            for (BigInteger v : xPartInt) {
                if (v.signum() < 0) return new HashMap<>();
                bestSum = bestSum.add(v);
            }
        } else {
            // Simple DFS over small nullspace dimension (assume small nullDim)
            bestSum = null;
            int bound = 20; // adjust if necessary
            int[] t = new int[nullDim];
            List<int[]> allTs = new ArrayList<>();
            generateAllT(0, t, nullDim, bound, allTs);
            for (int[] tTry : allTs) {
                BigInteger[] xTry = Arrays.copyOf(xPartInt, n);
                boolean feasible = true;
                for (int k = 0; k < nullDim; k++) {
                    for (int j = 0; j < n; j++) {
                        xTry[j] = xTry[j].add(nullBasisInt[k][j].multiply(BigInteger.valueOf(tTry[k])));
                        if (xTry[j].signum() < 0) feasible = false;
                    }
                }
                if (!feasible) continue;
                BigInteger sum = BigInteger.ZERO;
                for (int j = 0; j < n; j++) sum = sum.add(xTry[j]);
                if (bestSum == null || sum.compareTo(bestSum) < 0) {
                    bestSum = sum;
                    bestT = tTry.clone();
                }
            }
        }

        // Build final solution
        Map<Integer, BigInteger> out = new HashMap<>();
        if (nullDim == 0) {
            for (int j = 0; j < n; j++)
                if (xPartInt[j].signum() > 0) out.put(j, xPartInt[j]);
        } else if (bestT != null) {
            BigInteger[] xFinal = Arrays.copyOf(xPartInt, n);
            for (int k = 0; k < nullDim; k++)
                for (int j = 0; j < n; j++)
                    xFinal[j] = xFinal[j].add(nullBasisInt[k][j].multiply(BigInteger.valueOf(bestT[k])));
            for (int j = 0; j < n; j++)
                if (xFinal[j].signum() > 0) out.put(j, xFinal[j]);
        }

        return out;
    }

    // Generate all integer vectors t in [-bound,bound]^nullDim
    private static void generateAllT(int idx, int[] t, int nullDim, int bound, List<int[]> allTs) {
        if (idx == nullDim) {
            allTs.add(t.clone());
            return;
        }
        for (int i = 0; i <= bound; i++) { // only non-negative multipliers
            t[idx] = i;
            generateAllT(idx + 1, t, nullDim, bound, allTs);
        }
    }

    // --------------------------- Rational helper -------------------------------
    private static final class Rational {
        final BigInteger numerator;
        final BigInteger denominator;
        static final Rational ZERO = new Rational(BigInteger.ZERO, BigInteger.ONE);
        static final Rational ONE = new Rational(BigInteger.ONE, BigInteger.ONE);

        private Rational(BigInteger n, BigInteger d) {
            if (d.signum() == 0) throw new ArithmeticException("zero denominator");
            if (d.signum() < 0) { n = n.negate(); d = d.negate(); }
            BigInteger g = n.gcd(d);
            this.numerator = n.divide(g);
            this.denominator = d.divide(g);
        }

        static Rational of(long v) { return new Rational(BigInteger.valueOf(v), BigInteger.ONE); }
        boolean isZero() { return numerator.signum() == 0; }
        Rational negate() { return new Rational(numerator.negate(), denominator); }
    }

    // --------------------------- RREF ------------------------------------------
    private static final class RREF {
        static class Result { Rational[][] rref; int rank; int rankA; }

        static Result rref(Rational[][] mat) {
            int m = mat.length;
            int cols = mat[0].length;
            Rational[][] a = new Rational[m][cols];
            for (int i = 0; i < m; i++) for (int j = 0; j < cols; j++) a[i][j] = mat[i][j];

            int r = 0, c = 0, rankA = 0;
            int ncolsA = cols - 1;
            while (r < m && c < cols) {
                int pivot = -1;
                for (int i = r; i < m; i++) if (!a[i][c].isZero()) { pivot = i; break; }
                if (pivot == -1) { c++; continue; }
                if (pivot != r) { Rational[] tmp = a[pivot]; a[pivot] = a[r]; a[r] = tmp; }
                Rational pivVal = a[r][c];
                for (int j = c; j < cols; j++) a[r][j] = new Rational(a[r][j].numerator.multiply(pivVal.denominator),
                        a[r][j].denominator.multiply(pivVal.numerator));
                for (int i = 0; i < m; i++) if (i != r && !a[i][c].isZero()) {
                    Rational factor = a[i][c];
                    for (int j = c; j < cols; j++)
                        a[i][j] = new Rational(a[i][j].numerator.multiply(factor.denominator).multiply(pivVal.numerator)
                                .subtract(factor.numerator.multiply(a[r][j].numerator).multiply(a[i][j].denominator)),
                                a[i][j].denominator.multiply(factor.denominator).multiply(pivVal.denominator));
                }
                if (c < ncolsA) rankA++;
                r++; c++;
            }
            Result res = new Result();
            res.rref = a; res.rank = r; res.rankA = rankA;
            return res;
        }
    }

    // --------------------------- Testing ---------------------------------------
    public static void main(String[] args) {
        List<List<Integer>> vectors = Arrays.asList(
                Arrays.asList(0, 1, 0, 1),
                Arrays.asList(0, 0, 1, 1),
                Arrays.asList(1, 0, 1, 0),
                Arrays.asList(1, 1, 0, 0),
                Arrays.asList(0, 0, 0, 1),
                Arrays.asList(0, 0, 1, 0)
        );
        List<Integer> result = Arrays.asList(3, 5, 4, 7);

        Map<Integer, BigInteger> sol = solve(vectors, result);
        BigInteger total = BigInteger.ZERO;
        for (BigInteger v : sol.values()) total = total.add(v);
        System.out.println("Fewest total presses: " + total);
    }
}
