package year2023.day11;

import java.util.ArrayList;
import java.util.List;

public class SpaceImage {
    List<String> image;

    public SpaceImage(final List<String> image) {
        super();
        this.image = image;
    }

    public List<Integer> getEmptyRow() {
        List<Integer> emptyrows = new ArrayList<>();
        int i = 0;
        for (String line : image) {
            if (line.matches("\\.+")) {
                emptyrows.add(i);
            }
            i++;
        }
        return emptyrows;
    }


    public List<Integer> getEmptyColumn() {
        List<Integer> emptycolumns = new ArrayList<>();

        for (int i = 0; i < image.get(0).length(); i++) {
            boolean isEmpty = true;
            for (String line : image) {
                if (line.charAt(i) == '#') {
                    isEmpty = false;
                    break;
                }
            }
            if (isEmpty) {
                emptycolumns.add(i);
            }

        }
        return emptycolumns;
    }

    public List<Galaxy> getAllGalaxies(final int expensionFactor) {
        List<Integer> emptyrows = getEmptyRow();
        List<Integer> emptycolumns = getEmptyColumn();


        List<Galaxy> galaxies = new ArrayList<>();
        int y = 0;
        for (String line : image) {
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c == '#') {
                    galaxies.add(new Galaxy(x + getNbOfBlankBefore(x, emptycolumns) * expensionFactor,
                            y + getNbOfBlankBefore(y, emptyrows) * expensionFactor));
                }
                x++;
            }
            y++;
        }
        return galaxies;
    }

    public int getNbOfBlankBefore(final int val, final List<Integer> list) {
        int nb = 0;

        for (int i : list) {
            if (val > i) {
                nb++;
            } else {
                return nb;
            }
        }
        return nb;
    }
}
