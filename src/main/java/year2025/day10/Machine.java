package year2025.day10;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Machine {
    private String lightsExpectationRaw; // Keep the original raw string
    private List<String> buttonsRaw;     // Keep the original raw strings
    private String joltage;

    // New fields for byte representations (using int for bitmasks)
    private int lightsExpectationMask;
    private List<Integer> buttonsMasks;

    // Constructor to initialize the Machine object
    public Machine(final String lightsExpectationRaw, final List<String> buttonsRaw, final String joltage) {
        this.lightsExpectationRaw = lightsExpectationRaw;
        this.buttonsRaw = new ArrayList<>(buttonsRaw);
        this.joltage = joltage;

        // Perform the conversion during construction
        int expectedSize = getExpectedSize(lightsExpectationRaw); // Get size once
        this.lightsExpectationMask = convertLightsExpectationToMask(lightsExpectationRaw, expectedSize);
        this.buttonsMasks = buttonsRaw.stream().map(buttonRaw -> convertButtonToMask(buttonRaw, expectedSize))
                .collect(Collectors.toList());
    }

    // --- Conversion Methods ---

    /**
     * Extracts the relevant length of the bitmask from the lights expectation string. e.g., "[.###.#]" -> length 6
     */
    private int getExpectedSize(final String lightsExpectationRaw) {
        // Remove '[' and ']'
        String cleaned = lightsExpectationRaw.substring(1, lightsExpectationRaw.length() - 1);
        return cleaned.length();
    }

    /**
     * Converts a lights expectation string (e.g., "[.##.]") into a bitmask (e.g., 0b0110). This uses MSB-first mapping:
     * first char (index 0) maps to MSB.
     */
    private int convertLightsExpectationToMask(final String rawExpectation, final int expectedSize) {
        // Remove '[' and ']'
        String cleaned = rawExpectation.substring(1, rawExpectation.length() - 1);
        int mask = 0;
        for (int i = 0; i < cleaned.length(); i++) {
            char c = cleaned.charAt(i);
            if (c == '#') {
                // Set the bit at position (expectedSize - 1 - i)
                // This means the first char in cleaned (index 0) maps to the MSB of the mask
                // and the last char maps to the LSB.
                mask |= 1 << expectedSize - 1 - i;
            } else if (c != '.') {
                System.err.println("WARNING: Unexpected character in lights expectation: " + c);
            }
        }
        return mask;
    }

    /**
     * Converts a button string (e.g., "(0,1,2)") into a bitmask. The size parameter is crucial as it defines the total
     * number of bits. IMPORTANT: This version assumes button indices are 0-based from the MSB side, consistent with how
     * 'lightsExpectation' is interpreted. e.g., for expectedSize=6, index 0 in the button refers to the 6th bit (from
     * LSB), index 1 to the 5th, etc.
     */
    private int convertButtonToMask(final String rawButton, final int expectedSize) {
        int mask = 0;
        // Remove '(' and ')'
        String cleaned = rawButton.substring(1, rawButton.length() - 1);

        if (cleaned.isEmpty()) { // Handle empty buttons like "()"
            return 0;
        }

        // Split by comma and trim whitespace
        String[] indicesStr = cleaned.split(",");

        for (String indexStr : indicesStr) {
            try {
                int index = Integer.parseInt(indexStr.trim());
                if (index >= 0 && index < expectedSize) {
                    // !!! MODIFIED HERE !!!
                    // Set the bit at position (expectedSize - 1 - index)
                    // This means index 0 maps to the MSB, index (expectedSize-1) maps to the LSB
                    mask |= 1 << expectedSize - 1 - index;
                } else {
                    System.err.println(
                            "WARNING: Button index " + index + " out of bounds for expected size " + expectedSize);
                }
            } catch (NumberFormatException e) {
                System.err.println("WARNING: Invalid number format in button string: " + indexStr);
            }
        }
        return mask;
    }

    // --- Getters ---
    public String getLightsExpectationRaw() {
        return lightsExpectationRaw;
    }

    public List<String> getButtonsRaw() {
        return new ArrayList<>(buttonsRaw);
    }

    public String getJoltage() {
        return joltage;
    }

    public int getLightsExpectationMask() {
        return lightsExpectationMask;
    }

    public List<Integer> getButtonsMasks() {
        return new ArrayList<>(buttonsMasks);
    }

    // Override toString() for easy printing and debugging
    @Override
    public String toString() {
        return "Machine{" + "lightsExpectationRaw='" + lightsExpectationRaw + '\'' + ", buttonsRaw=" + buttonsRaw
                + ", joltage='" + joltage + '\'' + ", lightsExpectationMask="
                + String.format("0b%s", Integer.toBinaryString(lightsExpectationMask)) + ", buttonsMasks="
                + buttonsMasks.stream().map(m -> String.format("0b%s", Integer.toBinaryString(m)))
                        .collect(Collectors.joining(", ", "[", "]"))
                + '}';
    }

    // Override equals() and hashCode() for proper comparison and use in collections
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Machine machine = (Machine)o;
        return lightsExpectationMask == machine.lightsExpectationMask
                && Objects.equals(lightsExpectationRaw, machine.lightsExpectationRaw)
                && Objects.equals(buttonsRaw, machine.buttonsRaw) && Objects.equals(joltage, machine.joltage)
                && Objects.equals(buttonsMasks, machine.buttonsMasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lightsExpectationRaw, buttonsRaw, joltage, lightsExpectationMask, buttonsMasks);
    }
}

