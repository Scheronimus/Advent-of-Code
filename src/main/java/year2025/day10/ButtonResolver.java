package year2025.day10;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ButtonResolver {


    public int findMinButtonPresses(final int targetExpectation, final List<Integer> buttonMasks) {
        if (targetExpectation == 0) { // If target is 0, and we start at 0, 0 presses needed.
            return 0;
        }
        if (buttonMasks == null || buttonMasks.isEmpty()) {
            return -1; // Cannot reach target if no buttons.
        }

        Queue<SearchState> queue = new LinkedList<>();
        // A Map to store the minimum toggles to reach a specific (mask, nextButtonIndex) combination.
        // This is key for optimizing and guaranteeing the shortest path in terms of toggles.
        Map<VisitedKey, Integer> minTogglesToReach = new HashMap<>();

        // Initial state: currentMask = 0 (all lights off), nextButtonIndex = 0, togglesSoFar = 0
        // We start with mask 0 and are about to consider the button at index 0.
        SearchState initialState = new SearchState(0, 0, 0);
        queue.offer(initialState);
        minTogglesToReach.put(new VisitedKey(0, 0), 0);

        int overallMinToggles = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            SearchState currentState = queue.poll();

            // If we've considered all buttons (nextButtonIndex is beyond bounds)
            // for this path, and the mask matches the expectation, update min.
            if (currentState.nextButtonIndex == buttonMasks.size()) {
                if (currentState.currentMask == targetExpectation) {
                    overallMinToggles = Math.min(overallMinToggles, currentState.togglesSoFar);
                }
                continue; // This path has considered all buttons, no further branching
            }

            // Get the button mask for the current decision
            int currentButtonMask = buttonMasks.get(currentState.nextButtonIndex);

            // --- Option 1: Toggle the current button ---
            int maskAfterToggle = currentState.currentMask ^ currentButtonMask;
            int togglesAfterToggle = currentState.togglesSoFar + 1;
            VisitedKey keyToggle = new VisitedKey(maskAfterToggle, currentState.nextButtonIndex + 1);

            // If we've found a path to this (mask, nextButtonIndex) with fewer toggles, explore it
            if (togglesAfterToggle < minTogglesToReach.getOrDefault(keyToggle, Integer.MAX_VALUE)) {
                queue.offer(new SearchState(maskAfterToggle, currentState.nextButtonIndex + 1, togglesAfterToggle));
                minTogglesToReach.put(keyToggle, togglesAfterToggle);
            }

            // --- Option 2: Do NOT toggle the current button ---
            // The mask remains the same, togglesSoFar remains the same
            VisitedKey keyNoToggle = new VisitedKey(currentState.currentMask, currentState.nextButtonIndex + 1);

            // If we've found a path to this (mask, nextButtonIndex) with fewer toggles, explore it
            if (currentState.togglesSoFar < minTogglesToReach.getOrDefault(keyNoToggle, Integer.MAX_VALUE)) {
                queue.offer(new SearchState(currentState.currentMask, currentState.nextButtonIndex + 1,
                        currentState.togglesSoFar));
                minTogglesToReach.put(keyNoToggle, currentState.togglesSoFar);
            }
        }

        return overallMinToggles == Integer.MAX_VALUE ? -1 : overallMinToggles;
    }
}