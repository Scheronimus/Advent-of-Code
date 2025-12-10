package year2025.day10.part1;

import java.util.Objects;

public class VisitedKey {
    final int mask;
    final int buttonIndex; // The index of the button *just processed*

    public VisitedKey(final int mask, final int buttonIndex) {
        this.mask = mask;
        this.buttonIndex = buttonIndex;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VisitedKey that = (VisitedKey)o;
        return mask == that.mask && buttonIndex == that.buttonIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mask, buttonIndex);
    }
}