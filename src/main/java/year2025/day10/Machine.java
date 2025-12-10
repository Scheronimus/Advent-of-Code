package year2025.day10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Machine {
    private String lightsExpectationRaw; // Keep the original raw string
    private List<String> buttonsRaw;     // Keep the original raw strings
    private String joltageRaw;           // Changed name to joltageRaw for clarity

    // Existing fields for bitmask representations
    private int lightsExpectationMask;
    private List<Integer> buttonsBitmasks; // Changed name to buttonsBitmasks for clarity

    // NEW fields for integer list representations
    private List<Integer> joltageVector;    // Represents joltage as a vector of integers
    private List<List<Integer>> buttonsVectors; // Represents buttons as vectors of integers

    // Constructor to initialize the Machine object
    public Machine(final String lightsExpectationRaw, final List<String> buttonsRaw, final String joltageRaw) {
        this.lightsExpectationRaw = lightsExpectationRaw;
        this.buttonsRaw = new ArrayList<>(buttonsRaw);
        this.joltageRaw = joltageRaw;

        // Perform the conversion during construction
        int expectedSize = getExpectedSize(lightsExpectationRaw); // Get size once

        // Existing bitmask conversions
        this.lightsExpectationMask = convertLightsExpectationToBitmask(lightsExpectationRaw, expectedSize);
        this.buttonsBitmasks = buttonsRaw.stream().map(buttonRaw -> convertButtonToBitmask(buttonRaw, expectedSize))
                .collect(Collectors.toList());

        // NEW vector conversions
        this.joltageVector = convertJoltageToVector(joltageRaw, expectedSize);
        this.buttonsVectors = buttonsRaw.stream().map(buttonRaw -> convertButtonToVector(buttonRaw, expectedSize))
                .collect(Collectors.toList());
        this.buttonsVectors = buttonsVectors.stream().sorted((b1, b2) -> {
            int sum1 = b1.stream().mapToInt(Integer::intValue).sum();
            int sum2 = b2.stream().mapToInt(Integer::intValue).sum();
            return Integer.compare(sum2, sum1); // Descending sum
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    // --- Existing Conversion Methods (renamed for clarity) ---

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
    private int convertLightsExpectationToBitmask(final String rawExpectation, final int expectedSize) {
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
    private int convertButtonToBitmask(final String rawButton, final int expectedSize) {
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

    // --- NEW Conversion Methods for Integer Lists (Vectors) ---

    /**
     * Converts a joltage string (e.g., "[1,0,1]") into a List<Integer>. The size parameter is used for validation and
     * to ensure a consistent vector length.
     */
    private List<Integer> convertJoltageToVector(final String rawJoltage, final int expectedSize) {
        // Remove '[' and ']'
        String cleaned = rawJoltage.substring(1, rawJoltage.length() - 1);

        if (cleaned.isEmpty()) { // Handle empty joltage like "[]"
            return Collections.nCopies(expectedSize, 0); // Return a vector of zeros
        }

        String[] valuesStr = cleaned.split(",");
        List<Integer> vector = new ArrayList<>();

        for (String valueStr : valuesStr) {
            try {
                vector.add(Integer.parseInt(valueStr.trim()));
            } catch (NumberFormatException e) {
                System.err.println("WARNING: Invalid number format in joltage string: " + valueStr);
                // Decide how to handle this: skip, default to 0, or throw exception.
                // For now, we'll add 0 and continue.
                vector.add(0);
            }
        }

        // Validate size
        if (vector.size() != expectedSize) {
            System.err.println("WARNING: Joltage vector size (" + vector.size() + ") does not match expected size ("
                    + expectedSize + "). Padding or truncating.");
            // Pad with zeros or truncate to match expectedSize
            return IntStream.range(0, expectedSize).mapToObj(i -> i < vector.size() ? vector.get(i) : 0)
                    .collect(Collectors.toList());
        }
        return vector;
    }

    /**
     * Converts a button string (e.g., "(0,1,2)") into a List<Integer> vector. Positions indicated in the button string
     * become '1', others '0'. The size parameter ensures the vector has the correct length.
     */
    private List<Integer> convertButtonToVector(final String rawButton, final int expectedSize) {
        List<Integer> vector = new ArrayList<>(Collections.nCopies(expectedSize, 0)); // Initialize with zeros

        // Remove '(' and ')'
        String cleaned = rawButton.substring(1, rawButton.length() - 1);

        if (cleaned.isEmpty()) { // Handle empty buttons like "()"
            return vector; // Return all zeros
        }

        // Split by comma and trim whitespace
        String[] indicesStr = cleaned.split(",");

        for (String indexStr : indicesStr) {
            try {
                int index = Integer.parseInt(indexStr.trim());
                if (index >= 0 && index < expectedSize) {
                    // Set the value at the corresponding position to 1
                    // Assuming index 0 corresponds to the first element in the list (MSB side logic)
                    vector.set(index, 1);
                } else {
                    System.err.println(
                            "WARNING: Button index " + index + " out of bounds for expected size " + expectedSize);
                }
            } catch (NumberFormatException e) {
                System.err.println("WARNING: Invalid number format in button string: " + indexStr);
            }
        }
        return vector;
    }

    // --- Getters ---
    public String getLightsExpectationRaw() {
        return lightsExpectationRaw;
    }

    public List<String> getButtonsRaw() {
        return new ArrayList<>(buttonsRaw);
    }

    public String getJoltageRaw() { // Changed getter name
        return joltageRaw;
    }

    public int getLightsExpectationMask() {
        return lightsExpectationMask;
    }

    public List<Integer> getButtonsMasks() { // Changed getter name
        return new ArrayList<>(buttonsBitmasks);
    }

    public List<Integer> getJoltageVector() {
        return new ArrayList<>(joltageVector); // Defensive copy
    }

    public List<List<Integer>> getButtonsVectors() {
        // Deep copy of the list of lists
        return buttonsVectors.stream().map(ArrayList::new).collect(Collectors.toList());
    }

    // Override toString() for easy printing and debugging
    @Override
    public String toString() {
        return "Machine{" + "lightsExpectationRaw='" + lightsExpectationRaw + '\'' + ", buttonsRaw=" + buttonsRaw
                + ", joltageRaw='" + joltageRaw + '\'' + ", lightsExpectationMask="
                + String.format("0b%s", Integer.toBinaryString(lightsExpectationMask)) + ", buttonsBitmasks="
                + buttonsBitmasks.stream().map(m -> String.format("0b%s", Integer.toBinaryString(m)))
                        .collect(Collectors.joining(", ", "[", "]"))
                + ", joltageVector=" + joltageVector + ", buttonsVectors=" + buttonsVectors + '}';
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
                && Objects.equals(buttonsRaw, machine.buttonsRaw) && Objects.equals(joltageRaw, machine.joltageRaw)
                && Objects.equals(buttonsBitmasks, machine.buttonsBitmasks)
                && Objects.equals(joltageVector, machine.joltageVector)
                && Objects.equals(buttonsVectors, machine.buttonsVectors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lightsExpectationRaw, buttonsRaw, joltageRaw, lightsExpectationMask, buttonsBitmasks,
                joltageVector, buttonsVectors);
    }
}

