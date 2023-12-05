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

    public AlmanacMapEntry getNextEntry(final long source) {
        long l = Long.MAX_VALUE;
        AlmanacMapEntry nearestEntry = null;
        for (AlmanacMapEntry entry : entries) {
            if (source < entry.sourceRangeStart && entry.sourceRangeStart < l) {
                l = entry.sourceRangeStart;
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
        long i = source.start;
        long tempL = source.length;

        while (tempL > 0) {
            AlmanacMapEntry entry = getEntryIfInRange(i);
            if (entry == null) {
                AlmanacMapEntry nextEntry = getNextEntry(i);
                if (nextEntry != null && tempL > nextEntry.sourceRangeStart - i) {
                    ranges.add(new Range(i, nextEntry.sourceRangeStart - i));
                    long tL = nextEntry.sourceRangeStart - i;
                    i += tL;
                    tempL -= tL;
                } else {
                    ranges.add(new Range(i, tempL));
                    i += tempL;
                    tempL = 0;
                }
            } else {
                if (tempL > entry.sourceRangeStart - i + entry.rangeLength - 1) {
                    ranges.add(new Range(entry.calculateDestination(i),
                            entry.sourceRangeStart - i + entry.rangeLength - 1));
                    long tL = entry.sourceRangeStart - i + entry.rangeLength;
                    i += tL;
                    tempL -= tL;
                } else {
                    ranges.add(new Range(entry.calculateDestination(i), tempL));
                    i += tempL;
                    tempL = 0;
                }
            }
        }
        return ranges;
    }
}