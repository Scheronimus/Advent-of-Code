package year2023.day03;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Gear {
    int line;
    int col;

    List<PartNumber> partNumber = new ArrayList<>();

    public Gear(int line, int col) {
        super();
        this.line = line;
        this.col = col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, line);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Gear other = (Gear)obj;
        return col == other.col && line == other.line;
    }

    @Override
    public String toString() {
        return "Gear [line=" + line + ", col=" + col + ", partNumber=" + partNumber + "]";
    }
}
