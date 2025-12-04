package year2025.day04;

import java.util.ArrayList;
import java.util.List;

public class RollPaperMap {
    List<String> rollPaperMap = new ArrayList<>();

    public RollPaperMap(final List<String> rollPaperMap) {
        super();
        this.rollPaperMap.addAll(rollPaperMap);
    }

    public int getMapWidth() {
        if (rollPaperMap.isEmpty()) {
            return 0;
        }
        return rollPaperMap.get(0).length();
    }

    public int getMapHeight() {
        return rollPaperMap.size();
    }

    public char getContentAt(final int x, final int y) {
        return rollPaperMap.get(y).charAt(x);
    }

    public void setContentAt(final int x, final int y, final char c) {
        String originalRow = rollPaperMap.get(y);
        StringBuilder newRowBuilder = new StringBuilder(originalRow);
        newRowBuilder.setCharAt(x, c);
        String newRow = newRowBuilder.toString();
        rollPaperMap.set(y, newRow);
    }

    public List<Character> getAdjacentOf(final int x, final int y) {
        List<Character> adjacentCharacters = new ArrayList<>();

        // Define the 8 possible directions for neighbors (including diagonals)
        int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

        int mapHeight = rollPaperMap.size();
        if (mapHeight == 0) {
            return adjacentCharacters;
        }
        int mapWidth = rollPaperMap.get(0).length();

        for (int i = 0; i < 8; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            // Check if the new coordinates are within the map boundaries
            if (newX >= 0 && newX < mapWidth && newY >= 0 && newY < mapHeight) {
                adjacentCharacters.add(getContentAt(newX, newY));
            }
        }

        return adjacentCharacters;
    }

}