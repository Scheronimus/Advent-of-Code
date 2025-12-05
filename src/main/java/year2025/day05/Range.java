package year2025.day05;

public class Range {

    long min;
    long max;

    public Range(final long min, final long max) {
        super();
        this.min = min;
        this.max = max;
    }

    public boolean isInRange(final long value) {
        return value >= min && value <= max;

    }

    public long getMin() {
        return min;
    }

    public void setMin(final long min) {
        this.min = min;
    }

    public long getMax() {
        return max;
    }

    public void setMax(final long max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "Range [min=" + min + ", max=" + max + ", size=" + size() + "]";
    }

    public long size() {
        return max - min + 1;
    }


}
