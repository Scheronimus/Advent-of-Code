package year2023.day08;

import java.util.Objects;

public class Snapshot {
    String position;
    int index;
    int step;

    public Snapshot(String position, int index, int step) {
        super();
        this.position = position;
        this.index = index;
        this.step = step;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, position);
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
        return index == other.index && Objects.equals(position, other.position);
    }

    @Override
    public String toString() {
        return "Snapshot [position=" + position + ", index=" + index + ", step=" + step + "]";
    }


}
