package day17;

import java.util.List;
import java.util.Objects;

public class Snapshot {
    int shapeIndex;
    int stoneIndex;
    double height;
    double time;
    double stoneNumber;
    List<Integer> topNivel;

    public Snapshot(int shapeIndex, int stoneIndex, double height, double time, double stoneNumber,
            List<Integer> topNivel) {
        super();
        this.shapeIndex = shapeIndex;
        this.stoneIndex = stoneIndex;
        this.height = height;
        this.time = time;
        this.stoneNumber = stoneNumber;
        this.topNivel = topNivel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shapeIndex, stoneIndex, topNivel);
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
        return shapeIndex == other.shapeIndex && stoneIndex == other.stoneIndex
                && Objects.equals(topNivel, other.topNivel);
    }

    @Override
    public String toString() {
        return "Snapshot [shapeIndex=" + shapeIndex + ", stoneIndex=" + stoneIndex + ", height=" + height + ", time="
                + time + ", stoneNumber=" + stoneNumber + ", topNivel=" + topNivel + "]";
    }


}
