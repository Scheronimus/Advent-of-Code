package day19;

public class State2 {
    Material robotCount;
    Material currentMaterial;
    int time;

    public State2(Material robotCount, Material currentMaterial, int time) {
        super();
        this.robotCount = robotCount;
        this.currentMaterial = currentMaterial;
        this.time = time;
    }

    @Override
    public String toString() {
        return "State2 [robotCount=" + robotCount + ", currentMaterial=" + currentMaterial + ", time=" + time + "]";
    }

    public State2(State2 state) {
        super();
        this.robotCount = new Material(state.robotCount);
        this.currentMaterial = new Material(state.currentMaterial);
        time = state.time;

    }
}
