package year2023.day02;

import java.util.ArrayList;
import java.util.List;

public class Game {
    int id;
    List<CubeSet> cubeSets = new ArrayList<>();

    public Game(int id, List<CubeSet> cubeSets) {
        super();
        this.id = id;
        this.cubeSets = cubeSets;
    }

    public boolean isValid(int blueThresold, int greenThresold, int redThresold) {
        for (CubeSet cubeSet : cubeSets) {
            if (!cubeSet.isValid(blueThresold, greenThresold, redThresold)) {
                return false;
            }
        }
        return true;
    }

    public CubeSet getMinimumSet() {

        int blueMin = 0;
        int greenMin = 0;
        int redMin = 0;
        for (CubeSet cubeSet : cubeSets) {
            if (cubeSet.blue > blueMin) {
                blueMin = cubeSet.blue;
            }
            if (cubeSet.green > greenMin) {
                greenMin = cubeSet.green;
            }
            if (cubeSet.red > redMin) {
                redMin = cubeSet.red;
            }
        }

        return new CubeSet(blueMin, greenMin, redMin);
    }


}
