package year2025.day10;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IntegerLinearSolver Solves A * x = b over non-negative integers minimizing sum(x_i), where A is a (m x n) matrix with
 * small integer entries (here 0/1), provided as List<List<Integer>> "vectors" (each inner list is a column vector) and
 * b is List<Integer> "result" (length m). Usage: Map<Integer,Integer> sol = IntegerLinearSolver.solve(vectors, result);
 * // sol maps column index -> count (only positive entries included)
 */
public class IntegerLinearSolver {

    // --- Public API -----------------------------------------------------------------
    public static Map<Integer, Integer> solve(final List<List<Integer>> vectors, final List<Integer> result) {
        if (result == null || vectors == null)
            throw new IllegalArgumentException("null inputs");
        int m = result.size();
        int n = vectors.size();
        if (m == 0)
            return new HashMap<>();

        Rational[][] A = new Rational[m][n];
        for (int j = 0; j < n; j++) {
            List<Integer> col = vectors.get(j);
            if (col == null || col.size() != m)
                throw new IllegalArgumentException("vector size mismatch");
            for (int i = 0; i < m; i++)
                A[i][j] = Rational.of(col.get(i));
        }
        Rational[] b = new Rational[m];
        for (int i = 0; i < m; i++)
            b[i] = Rational.of(result.get(i));

        Rational[][] aug = new Rational[m][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++)
                aug[i][j] = A[i][j];
            aug[i][n] = b[i];
        }

        RREF.Result rrefRes = RREF.rref(aug);
        Rational[][] rref = rrefRes.rref;
        int rank = rrefRes.rank;
        int rankA = rrefRes.rankA;
        if (rank != rankA)
            return new HashMap<>();

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
            if (isPivotCol[j])
                pivotCols.add(j);
            else
                freeCols.add(j);
        }
        int nullDim = n - pivotCols.size();

        Rational[] xPart = new Rational[n];
        for (int j = 0; j < n; j++)
            xPart[j] = Rational.ZERO;
        for (int j : pivotCols) {
            int i = pivotRowForCol[j];
            xPart[j] = rref[i][n];
        }

        if (nullDim == 0) {
            return rationalVectorToNonnegIntegerMap(xPart);
        }

        Rational[][] nullBasisRational = new Rational[nullDim][n];
        for (int k = 0; k < nullDim; k++) {
            int f = freeCols.get(k);
            for (int j = 0; j < n; j++)
                nullBasisRational[k][j] = Rational.ZERO;
            nullBasisRational[k][f] = Rational.ONE;
            for (int j : pivotCols) {
                int i = pivotRowForCol[j];
                nullBasisRational[k][j] = rref[i][f].negate();
            }
        }

        int[][] nullBasisInteger = new int[nullDim][n];
        BigInteger[] scaleBig = new BigInteger[nullDim];
        for (int k = 0; k < nullDim; k++) {
            BigInteger lcm = BigInteger.ONE;
            for (int j = 0; j < n; j++)
                lcm = lcm.divide(lcm.gcd(nullBasisRational[k][j].denominator))
                        .multiply(nullBasisRational[k][j].denominator);
            scaleBig[k] = lcm;
            for (int j = 0; j < n; j++)
                nullBasisInteger[k][j] = nullBasisRational[k][j].numerator.multiply(lcm)
                        .divide(nullBasisRational[k][j].denominator).intValueExact();
        }

        double[] xPartDouble = new double[n];
        for (int j = 0; j < n; j++)
            xPartDouble[j] = xPart[j].toDouble();

        double[][] nullBasisDouble = new double[nullDim][n];
        for (int k = 0; k < nullDim; k++) {
            double sD = scaleBig[k].doubleValue();
            for (int j = 0; j < n; j++)
                nullBasisDouble[k][j] = (nullBasisInteger[k][j]) / sD;
        }

        int[] minTk = new int[nullDim];
        int[] maxTk = new int[nullDim];
        for (int k = 0; k < nullDim; k++) {
            int lo = Integer.MIN_VALUE / 4, hi = Integer.MAX_VALUE / 4;
            for (int j = 0; j < n; j++) {
                double coeff = nullBasisDouble[k][j];
                if (Math.abs(coeff) < 1e-12)
                    continue;
                if (coeff > 0)
                    lo = Math.max(lo, (int)Math.ceil(-xPartDouble[j] / coeff - 1e-12));
                else
                    hi = Math.min(hi, (int)Math.floor(-xPartDouble[j] / coeff + 1e-12));
            }
            minTk[k] = Math.max(lo, -2000);
            maxTk[k] = Math.min(hi, 2000);
            if (minTk[k] > maxTk[k]) {
                minTk[k] = 1;
                maxTk[k] = 0;
            }
        }

        long[] bestSumRef = new long[] { Long.MAX_VALUE };
        int[] bestTRef = new int[nullDim];
        Arrays.fill(bestTRef, -1);

        enumerateNullspace(0, nullDim, new int[nullDim], minTk, maxTk, xPartDouble, nullBasisDouble, n, bestSumRef,
                bestTRef);

        if (bestTRef[0] == -1)
            return new HashMap<>();

        double[] xFinalDouble = Arrays.copyOf(xPartDouble, n);
        for (int k = 0; k < nullDim; k++)
            for (int j = 0; j < n; j++)
                xFinalDouble[j] += bestTRef[k] * nullBasisDouble[k][j];

        Map<Integer, Integer> out = new HashMap<>();
        for (int j = 0; j < n; j++) {
            long val = Math.round(xFinalDouble[j]);
            if (val < 0)
                return new HashMap<>();
            if (val > 0)
                out.put(j, (int)val);
        }
        return out;
    }

    private static void enumerateNullspace(int idx, int K, int[] curT, int[] minTk, int[] maxTk, double[] xPartDouble,
            double[][] nullBasisDouble, int n, long[] bestSumRef, int[] bestTRef) {
        if (idx == K) {
            double sum = 0.0;
            double[] x = Arrays.copyOf(xPartDouble, n);
            for (int k = 0; k < K; k++)
                for (int j = 0; j < n; j++)
                    x[j] += curT[k] * nullBasisDouble[k][j];
            for (int j = 0; j < n; j++)
                if (x[j] < -1e-9)
                    return;
            for (int j = 0; j < n; j++)
                sum += Math.round(x[j]);
            long sumL = Math.round(sum);
            if (sumL < bestSumRef[0]) {
                bestSumRef[0] = sumL;
                System.arraycopy(curT, 0, bestTRef, 0, K);
            }
            return;
        }
        for (int t = minTk[idx]; t <= maxTk[idx]; t++) {
            curT[idx] = t;
            enumerateNullspace(idx + 1, K, curT, minTk, maxTk, xPartDouble, nullBasisDouble, n, bestSumRef, bestTRef);
        }
    }

    private static Map<Integer, Integer> rationalVectorToNonnegIntegerMap(Rational[] x) {
        Map<Integer, Integer> out = new HashMap<>();
        for (int i = 0; i < x.length; i++) {
            if (!x[i].isInteger())
                return new HashMap<>();
            BigInteger bi = x[i].numerator.divide(x[i].denominator);
            if (bi.signum() < 0)
                return new HashMap<>();
            if (!bi.equals(BigInteger.ZERO))
                out.put(i, bi.intValueExact());
        }
        return out;
    }

    // ---------------- Rational helper ----------------
    private static final class Rational {
        final BigInteger numerator;
        final BigInteger denominator;
        static final Rational ZERO = new Rational(BigInteger.ZERO, BigInteger.ONE);
        static final Rational ONE = new Rational(BigInteger.ONE, BigInteger.ONE);

        private Rational(BigInteger n, BigInteger d) {
            if (d.signum() == 0)
                throw new ArithmeticException("zero denom");
            if (d.signum() < 0) {
                n = n.negate();
                d = d.negate();
            }
            BigInteger g = n.gcd(d);
            if (!g.equals(BigInteger.ONE)) {
                n = n.divide(g);
                d = d.divide(g);
            }
            this.numerator = n;
            this.denominator = d;
        }

        static Rational of(long v) {
            return new Rational(BigInteger.valueOf(v), BigInteger.ONE);
        }

        boolean isZero() {
            return numerator.signum() == 0;
        }

        Rational negate() {
            return new Rational(numerator.negate(), denominator);
        }

        boolean isInteger() {
            return denominator.equals(BigInteger.ONE);
        }

        double toDouble() {
            return numerator.doubleValue() / denominator.doubleValue();
        }

        @Override
        public String toString() {
            return numerator + "/" + denominator;
        }
    }

    // ---------------- RREF helper ----------------
    private static final class RREF {
        static class Result {
            Rational[][] rref;
            int rank;
            int rankA;
        }

        static Result rref(Rational[][] mat) {
            int m = mat.length;
            int cols = mat[0].length;
            Rational[][] a = new Rational[m][cols];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < cols; j++)
                    a[i][j] = mat[i][j];

            int r = 0, c = 0, ncolsA = cols - 1, rankA = 0;
            while (r < m && c < cols) {
                int pivot = -1;
                for (int i = r; i < m; i++)
                    if (!a[i][c].isZero()) {
                        pivot = i;
                        break;
                    }
                if (pivot == -1) {
                    c++;
                    continue;
                }
                if (pivot != r) {
                    Rational[] tmp = a[pivot];
                    a[pivot] = a[r];
                    a[r] = tmp;
                }
                Rational pivVal = a[r][c];
                for (int j = c; j < cols; j++)
                    a[r][j] = new Rational(a[r][j].numerator.multiply(pivVal.denominator),
                            a[r][j].denominator.multiply(pivVal.numerator));
                for (int i = 0; i < m; i++)
                    if (i != r && !a[i][c].isZero()) {
                        Rational factor = a[i][c];
                        for (int j = c; j < cols; j++)
                            a[i][j] = new Rational(
                                    a[i][j].numerator.multiply(factor.denominator).multiply(pivVal.denominator)
                                            .subtract(factor.numerator.multiply(pivVal.numerator)
                                                    .multiply(a[r][j].denominator)),
                                    a[i][j].denominator.multiply(factor.denominator).multiply(pivVal.denominator));
                    }
                if (c < ncolsA)
                    rankA++;
                r++;
                c++;
            }
            Result res = new Result();
            res.rref = a;
            res.rank = r;
            res.rankA = rankA;
            return res;
        }
    }
}

