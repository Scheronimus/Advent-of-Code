package year2025.day10;

// (Your Machine class and Parser class would go here, unchanged from the last version)

// --- New classes for the button press logic ---

/**
 * Represents a state in the button-pressing search.
 */
public class SearchState {
    int currentMask;          // The current state of the lights
    int nextButtonIndex;      // The index of the NEXT button to consider
    int togglesSoFar;         // The number of buttons ACTUALLY toggled to reach this mask

    public SearchState(final int currentMask, final int nextButtonIndex, final int togglesSoFar) {
        this.currentMask = currentMask;
        this.nextButtonIndex = nextButtonIndex;
        this.togglesSoFar = togglesSoFar;
    }

    @Override
    public String toString() {
        return "State{" + "mask=" + String.format("0b%s", Integer.toBinaryString(currentMask)) + ", nextBtnIdx="
                + nextButtonIndex + ", toggles=" + togglesSoFar + '}';
    }

    // `equals` and `hashCode` are not strictly needed for this specific `SearchState`
    // because we'll use a `Map<Integer, Integer>` to track `minToggles` for `currentMask`
    // at a given `nextButtonIndex` level, or a `Set<VisitedKey>`.
    // Let's create a custom visited key that is more robust.
}