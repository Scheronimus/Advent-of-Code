package day19;

import java.util.HashSet;
import java.util.Set;

public class State {
    Material robotCount;
    Material currentMaterial;
    Set<MaterialEnum> skipped = new HashSet<>();

    public State(Material robotCount, Material currentMaterial) {
        super();
        this.robotCount = robotCount;
        this.currentMaterial = currentMaterial;
    }

    @Override
    public String toString() {
        return "State [robotCount=" + robotCount + ", currentMaterial=" + currentMaterial + ", skipped=" + skipped
                + "]";
    }
}
