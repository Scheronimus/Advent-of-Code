package day19;

import java.util.HashSet;
import java.util.Set;

public class State2 {
    Material robotCount;
    Material currentMaterial;
    Set<MaterialEnum> skipped = new HashSet<>();
    int time;

    public State2(Material robotCount, Material currentMaterial, int time) {
        super();
        this.robotCount = robotCount;
        this.currentMaterial = currentMaterial;
        this.time = time;
    }

    @Override
    public String toString() {
        return "State2 [robotCount=" + robotCount + ", currentMaterial=" + currentMaterial + ", skipped=" + skipped
                + ", time=" + time + "]";
    }


}
