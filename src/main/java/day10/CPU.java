package day10;

import java.util.ArrayList;
import java.util.List;

public class CPU {
    int X = 1;
    int currentCycle = 0;
    List<Integer> historyOfX = new ArrayList<>();


    public CPU() {
        super();
        historyOfX.add(X);
    }


    public void increaseCycle() {
        currentCycle++;
        historyOfX.add(X);
    }
}
