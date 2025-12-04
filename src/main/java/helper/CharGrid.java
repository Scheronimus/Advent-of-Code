package helper;

import java.util.ArrayList;
import java.util.List;

public abstract class CharGrid {

    /** Internal map storage as list of immutable strings */
    protected final List<String> rows;

    /**
     * Construct a CharGrid from an existing list of strings.
     * A defensive copy is made.
     */
    protected CharGrid(List<String> rows) {
        this.rows = new ArrayList<>(rows);
    }

    /**
     * Create an empty CharGrid. Subclasses may fill rows later.
     */
    protected CharGrid() {
        this.rows = new ArrayList<>();
    }

    /** Width of the grid (number of columns). */
    public int width() {
        return rows.isEmpty() ? 0 : rows.get(0).length();
    }

    /** Height of the grid (number of lines). */
    public int height() {
        return rows.size();
    }

    /** True if the coordinate (x,y) is inside the grid. */
    public boolean inBounds(int x, int y) {
        return y >= 0 && y < height() && x >= 0 && x < width();
    }

    /** Read a character at (x,y). */
    public char get(int x, int y) {
        return rows.get(y).charAt(x);
    }

    /**
     * Mutate a character at (x,y).
     * Since String is immutable, this rebuilds the modified row.
     */
    public void set(int x, int y, char c) {
        StringBuilder sb = new StringBuilder(rows.get(y));
        sb.setCharAt(x, c);
        rows.set(y, sb.toString());
    }

    /** Returns the entire line at row y. */
    public String row(int y) {
        return rows.get(y);
    }

    /** Convenience: returns a char[] copy of row y. */
    public char[] rowChars(int y) {
        return rows.get(y).toCharArray();
    }

    /**
     * Returns all 8 neighbors of (x, y),
     * checking boundaries automatically.
     */
    public List<Character> neighbors8(int x, int y) {
        List<Character> result = new ArrayList<>();

        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1,  0,  1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (inBounds(nx, ny)) {
                result.add(get(nx, ny));
            }
        }

        return result;
    }

    /** 4-direction neighbors (up, down, left, right). */
    public List<Character> neighbors4(int x, int y) {
        List<Character> result = new ArrayList<>();

        int[] dx = {0, -1, 1, 0};
        int[] dy = {-1, 0, 0, 1};

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (inBounds(nx, ny)) {
                result.add(get(nx, ny));
            }
        }

        return result;
    }

    /** Prints the grid. Very handy for debugging AoC tasks. */
    public void print() {
        for (String row : rows) {
            System.out.println(row);
        }
    }
}