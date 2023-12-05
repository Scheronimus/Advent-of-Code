package year2023.day05;

import java.util.ArrayList;
import java.util.List;

public class AlmanacMap {
    List<AlmanacMapEntry> entries = new ArrayList<>();


    public void add(final AlmanacMapEntry entry) {
        entries.add(entry);
    }


    public long getDestination(final long source) {
        for (AlmanacMapEntry entry : entries) {
            if (entry.isInRange(source)) {
                // System.out.println(entry.calculateDestination(source));
                return entry.calculateDestination(source);
            }
        }
        // System.out.println(source);
        return source;
    }

    public List<Range> getDestination(final Range source) {
        long i = source.start;
        while (i < source.start + source.length) {


        }
        // for (AlmanacMapEntry entry : entries) {
        // if (entry.isInRange(source)) {
        // System.out.println(entry.calculateDestination(source));
        // return entry.calculateDestination(source);
        // }
        // }
        // System.out.println(source);
        return null;
    }


}
