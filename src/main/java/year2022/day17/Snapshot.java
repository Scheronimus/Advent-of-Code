package year2022.day17;

import java.util.List;
import java.util.Objects;

public class Snapshot {
    private int shapeIndex;
    private int jetPatternIndex;
    double height;
    double time;
    double stoneNumber;
    private List<Integer> topNivel;

    public Snapshot(int shapeIndex, int stoneIndex, double height, double time, double stoneNumber,
            List<Integer> topNivel) {
        super();
        this.shapeIndex = shapeIndex;
        this.jetPatternIndex = stoneIndex;
        this.height = height;
        this.time = time;
        this.stoneNumber = stoneNumber;
        this.topNivel = topNivel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shapeIndex, jetPatternIndex, topNivel);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Snapshot other = (Snapshot)obj;
        return shapeIndex == other.shapeIndex && jetPatternIndex == other.jetPatternIndex
                && Objects.equals(topNivel, other.topNivel);
    }

    @Override
    public String toString() {
        return "Snapshot [shapeIndex=" + shapeIndex + ", stoneIndex=" + jetPatternIndex + ", height=" + height
                + ", time=" + time + ", stoneNumber=" + stoneNumber + ", topNivel=" + topNivel + "]";
    }


}
