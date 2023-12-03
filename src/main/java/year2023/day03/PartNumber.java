package year2023.day03;

import java.util.List;

public class PartNumber {
    int value;
    int line;
    int start;
    int end;

    public PartNumber(int value, int line, int start, int end) {
        super();
        this.value = value;
        this.line = line;
        this.start = start;
        this.end = end;
    }


    public boolean isValid(List<String> engineMap) {
        for (int l = line - 1; l <= line + 1; l++) {
            if (l >= 0 && l < engineMap.size()) {
                for (int x = start - 1; x <= end + 1; x++) {
                    if (x >= 0 && x < engineMap.get(0).length()) {
                        char c = engineMap.get(l).charAt(x);
                        if (!Character.isDigit(c) && c != '.') {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void isGear(List<String> engineMap, List<Gear> gearList) {

        for (int l = line - 1; l <= line + 1; l++) {
            if (l >= 0 && l < engineMap.size()) {
                for (int x = start - 1; x <= end + 1; x++) {
                    if (x >= 0 && x < engineMap.get(0).length()) {
                        char c = engineMap.get(l).charAt(x);
                        if (c == '*') {
                            Gear g = new Gear(l, x);
                            int in = gearList.indexOf(g);
                            if (in != -1) {
                                g = gearList.get(in);
                            } else {
                                gearList.add(g);
                            }
                            g.partNumber.add(this);
                            return;
                        }
                    }
                }
            }
        }
    }
}
