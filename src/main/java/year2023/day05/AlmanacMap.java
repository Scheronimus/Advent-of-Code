package year2023.day05;

import java.util.ArrayList;
import java.util.List;

public class AlmanacMap {
    List<AlmanacMapEntry> entries = new ArrayList<>();

    public void add(final AlmanacMapEntry entry) {
        entries.add(entry);
    }

    public AlmanacMapEntry getEntryIfInRange(final long source) {
        for (AlmanacMapEntry entry : entries) {
            if (entry.isInRange(source)) {
                return entry;
            }
        }
        return null;
    }

    public AlmanacMapEntry getNearestEntry(final long source) {
        long positionOfNearest = Long.MAX_VALUE;
        AlmanacMapEntry nearestEntry = null;
        for (AlmanacMapEntry entry : entries) {
            if (source < entry.sourceRangeStart && entry.sourceRangeStart < positionOfNearest) {
                positionOfNearest = entry.sourceRangeStart;
                nearestEntry = entry;
            }
        }
        return nearestEntry;
    }

    public long getDestination(final long source) {
        for (AlmanacMapEntry entry : entries) {
            if (entry.isInRange(source)) {
                return entry.calculateDestination(source);
            }
        }
        return source;
    }

    public List<Range> getDestination(final List<Range> ranges) {
        List<Range> temp = new ArrayList<>();
        for (Range range : ranges) {
            temp.addAll(getDestination(range));
        }
        return temp;
    }

    public List<Range> getDestination(final Range source) {

        List<Range> ranges = new ArrayList<>();
        long currentPosition = source.start;
        long currentLength = source.length;

        while (currentLength > 0) {
            AlmanacMapEntry entry = getEntryIfInRange(currentPosition);
            if (entry == null) {
                AlmanacMapEntry nextEntry = getNearestEntry(currentPosition);
                if (nextEntry != null && currentLength > nextEntry.sourceRangeStart - currentPosition) {
                    ranges.add(new Range(currentPosition, nextEntry.sourceRangeStart - currentPosition));
                    long consumedLength = nextEntry.sourceRangeStart - currentPosition;
                    currentPosition += consumedLength;
                    currentLength -= consumedLength;
                } else {
                    ranges.add(new Range(currentPosition, currentLength));
                    currentPosition += currentLength;
                    currentLength = 0;
                }
            } else {
                if (currentLength > entry.sourceRangeStart - currentPosition + entry.rangeLength - 1) {
                    ranges.add(new Range(entry.calculateDestination(currentPosition),
                            entry.sourceRangeStart - currentPosition + entry.rangeLength - 1));
                    long consumedLength = entry.sourceRangeStart - currentPosition + entry.rangeLength;
                    currentPosition += consumedLength;
                    currentLength -= consumedLength;
                } else {
                    ranges.add(new Range(entry.calculateDestination(currentPosition), currentLength));
                    currentPosition += currentLength;
                    currentLength = 0;
                }
            }
        }
        return ranges;
    }
}