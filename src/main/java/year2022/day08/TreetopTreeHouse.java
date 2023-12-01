package year2022.day08;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import helper.Puzzle;

public class TreetopTreeHouse extends Puzzle {

    ArrayList<ArrayList<Tree>> treesGrid = new ArrayList<>();

    protected TreetopTreeHouse(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                ArrayList<Tree> treeLine = new ArrayList<>();

                for (int i = 0; i < line.length(); i++) {
                    treeLine.add(new Tree(Integer.parseInt(line.substring(i, i + 1))));
                }
                treesGrid.add(treeLine);
            }
        }
    }

    private void markVisibleTree(final ArrayList<ArrayList<Tree>> treesGrid) {

        int xMax = treesGrid.get(0).size();
        int yMax = treesGrid.size();

        // NORD
        for (int x = 0; x < xMax; x++) {
            int previous = -1;
            for (int y = 0; y < yMax && previous < 9; y++) {
                if (treesGrid.get(y).get(x).heigth > previous) {
                    treesGrid.get(y).get(x).visible = true;
                    previous = treesGrid.get(y).get(x).heigth;
                }

            }
        }

        // SUD
        for (int x = 0; x < xMax; x++) {
            int previous = -1;
            for (int y = yMax - 1; y >= 0 && previous < 9; y--) {
                if (treesGrid.get(y).get(x).heigth > previous) {
                    treesGrid.get(y).get(x).visible = true;
                    previous = treesGrid.get(y).get(x).heigth;
                }
            }
        }

        // WEST
        for (int y = 0; y < yMax; y++) {
            int previous = -1;
            for (int x = 0; x < xMax && previous < 9; x++) {
                if (treesGrid.get(y).get(x).heigth > previous) {
                    treesGrid.get(y).get(x).visible = true;
                    previous = treesGrid.get(y).get(x).heigth;
                }
            }
        }

        // EAST
        for (int y = 0; y < yMax; y++) {
            int previous = -1;
            for (int x = xMax - 1; x >= 0 && previous < 9; x--) {
                if (treesGrid.get(y).get(x).heigth > previous) {
                    treesGrid.get(y).get(x).visible = true;
                    previous = treesGrid.get(y).get(x).heigth;
                }
            }
        }
    }

    private int calculateVisible(final ArrayList<ArrayList<Tree>> treesGrid) {
        int result = 0;
        for (ArrayList<Tree> treeLine : treesGrid) {
            for (Tree tree : treeLine) {
                if (tree.visible) {
                    result++;
                }
            }
        }
        return result;
    }

    private int calculateScenicScore(final int x, final int y, final int height,
            final ArrayList<ArrayList<Tree>> treesGrid) {
        int xMax = treesGrid.get(0).size();
        int yMax = treesGrid.size();

        // EAST
        int viewEast = 0;
        for (int i = x + 1; i < xMax; i++) {
            viewEast++;
            if (treesGrid.get(y).get(i).heigth >= height) {
                break;
            }
        }

        // WEST
        int viewWest = 0;
        for (int i = x - 1; i >= 0; i--) {
            viewWest++;
            if (treesGrid.get(y).get(i).heigth >= height) {
                break;
            }
        }

        // SOUTH
        int viewSouth = 0;
        for (int i = y + 1; i < yMax; i++) {
            viewSouth++;
            if (treesGrid.get(i).get(x).heigth >= height) {
                break;
            }
        }

        // NORTH
        int viewNorth = 0;
        for (int i = y - 1; i >= 0; i--) {
            viewNorth++;
            if (treesGrid.get(i).get(x).heigth >= height) {
                break;
            }
        }

        return viewEast * viewWest * viewSouth * viewNorth;
    }

    private int findBestSpot(final ArrayList<ArrayList<Tree>> treesGrid) {
        int bestScore = 0;
        int xMax = treesGrid.get(0).size();
        int yMax = treesGrid.size();

        for (int x = 0; x < xMax; x++) {
            for (int y = 0; y < yMax; y++) {
                int score = calculateScenicScore(x, y, treesGrid.get(y).get(x).heigth, treesGrid);
                if (score > bestScore) {
                    bestScore = score;
                }
            }
        }
        return bestScore;
    }

    @Override
    public Object getAnswer1() {
        markVisibleTree(treesGrid);
        return calculateVisible(treesGrid);
    }

    @Override
    public Object getAnswer2() {
        return findBestSpot(treesGrid);
    }

    public static void main(final String[] args) throws IOException {
        TreetopTreeHouse treetopTreeHouse = new TreetopTreeHouse("year2022/day08/input");
        System.out.println("Answer1: " + treetopTreeHouse.getAnswer1());
        System.out.println("Answer2: " + treetopTreeHouse.getAnswer2());
    }

}
