package year2023.day05;

public class AlmanacMapEntry {
    long destinationRangeStart;
    long sourceRangeStart;
    long rangeLength;

    public AlmanacMapEntry(final long destinationRangeStart, final long sourceRangeStart, final long rangeLength) {
        super();
        this.destinationRangeStart = destinationRangeStart;
        this.sourceRangeStart = sourceRangeStart;
        this.rangeLength = rangeLength;
    }

    public boolean isInRange(final long source) {
        return source >= sourceRangeStart && source < sourceRangeStart + rangeLength;
    }

    public long calculateDestination(final long source) {

        return source - sourceRangeStart + destinationRangeStart;
    }


}
