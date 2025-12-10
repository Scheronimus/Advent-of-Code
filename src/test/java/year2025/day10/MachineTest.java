package year2025.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MachineTest {

    // Helper method to create a Machine object for testing
    private Machine createMachine(final String lightsExpectationRaw, final List<String> buttonsRaw,
            final String joltage) {
        return new Machine(lightsExpectationRaw, buttonsRaw, joltage);
    }

    // Helper to construct lightsExpectation for a given size for testing purposes
    private String getLightsExpectationForSize(final int size) {
        if (size <= 0) {
            return "[]";
        }
        char[] chars = new char[size];
        Arrays.fill(chars, '#'); // Use '#' for simplicity to get a size.
        return "[" + new String(chars) + "]";
    }

    @Nested
    @DisplayName("Constructor and Raw Data Getters")
    class ConstructorAndRawDataTests {

        @Test
        @DisplayName("Should store raw lights expectation correctly")
        void shouldStoreRawLightsExpectation() {
            String lights = "[.###.#]";
            Machine machine = createMachine(lights, Collections.emptyList(), "{10}");
            assertEquals(lights, machine.getLightsExpectationRaw());
        }

        @Test
        @DisplayName("Should store raw buttons correctly")
        void shouldStoreRawButtons() {
            List<String> buttons = Arrays.asList("(0,1,2)", "(3,4)");
            Machine machine = createMachine("[.###.#]", buttons, "{10}");
            assertEquals(buttons, machine.getButtonsRaw());
        }

        @Test
        @DisplayName("Should store joltage correctly")
        void shouldStoreJoltage() {
            String joltage = "{10,11,5}";
            Machine machine = createMachine("[.###.#]", Collections.emptyList(), joltage);
            assertEquals(joltage, machine.getJoltage());
        }

        @Test
        @DisplayName("Should handle empty buttons list")
        void shouldHandleEmptyButtonsList() {
            Machine machine = createMachine("[.##]", Collections.emptyList(), "{1}");
            assertTrue(machine.getButtonsRaw().isEmpty());
            assertTrue(machine.getButtonsMasks().isEmpty());
        }
    }

    @Nested
    @DisplayName("Lights Expectation Mask Conversion (MSB-first mapping)")
    class LightsExpectationMaskTests {

        // The expected masks for lights expectation remain the same as before
        // because its conversion already used MSB-first mapping.

        @Test
        @DisplayName("Empty expectation [] should convert to 0")
        void testEmptyExpectation() {
            String rawExpectation = "[]";
            int expectedMask = 0;
            Machine machine = createMachine(rawExpectation, Collections.emptyList(), "{0}");
            assertEquals(expectedMask, machine.getLightsExpectationMask(),
                    () -> "For raw: " + rawExpectation + ", expected mask: " + Integer.toBinaryString(expectedMask)
                            + ", actual: " + Integer.toBinaryString(machine.getLightsExpectationMask()));
        }

        @Test
        @DisplayName("Single # expectation [#] (size 1) should convert to 1 (0b1)")
        void testSingleHashExpectation() {
            String rawExpectation = "[#]";
            int expectedMask = 1; // 0b1
            Machine machine = createMachine(rawExpectation, Collections.emptyList(), "{0}");
            assertEquals(expectedMask, machine.getLightsExpectationMask(),
                    () -> "For raw: " + rawExpectation + ", expected mask: " + Integer.toBinaryString(expectedMask)
                            + ", actual: " + Integer.toBinaryString(machine.getLightsExpectationMask()));
        }

        @Test
        @DisplayName("Single . expectation [.] (size 1) should convert to 0 (0b0)")
        void testSingleDotExpectation() {
            String rawExpectation = "[.]";
            int expectedMask = 0; // 0b0
            Machine machine = createMachine(rawExpectation, Collections.emptyList(), "{0}");
            assertEquals(expectedMask, machine.getLightsExpectationMask(),
                    () -> "For raw: " + rawExpectation + ", expected mask: " + Integer.toBinaryString(expectedMask)
                            + ", actual: " + Integer.toBinaryString(machine.getLightsExpectationMask()));
        }

        @Test
        @DisplayName("[##] (size 2) should convert to 3 (0b11)")
        void testTwoHashExpectation() {
            String rawExpectation = "[##]";
            int expectedMask = 3; // 0b11
            Machine machine = createMachine(rawExpectation, Collections.emptyList(), "{0}");
            assertEquals(expectedMask, machine.getLightsExpectationMask(),
                    () -> "For raw: " + rawExpectation + ", expected mask: " + Integer.toBinaryString(expectedMask)
                            + ", actual: " + Integer.toBinaryString(machine.getLightsExpectationMask()));
        }

        @Test
        @DisplayName("[.#] (size 2) should convert to 1 (0b01)")
        void testExpectationDotHash() {
            String rawExpectation = "[.#]";
            int expectedMask = 1; // 0b01
            Machine machine = createMachine(rawExpectation, Collections.emptyList(), "{0}");
            assertEquals(expectedMask, machine.getLightsExpectationMask(),
                    () -> "For raw: " + rawExpectation + ", expected mask: " + Integer.toBinaryString(expectedMask)
                            + ", actual: " + Integer.toBinaryString(machine.getLightsExpectationMask()));
        }

        @Test
        @DisplayName("[#.] (size 2) should convert to 2 (0b10)")
        void testExpectationHashDot() {
            String rawExpectation = "[#.]";
            int expectedMask = 2; // 0b10
            Machine machine = createMachine(rawExpectation, Collections.emptyList(), "{0}");
            assertEquals(expectedMask, machine.getLightsExpectationMask(),
                    () -> "For raw: " + rawExpectation + ", expected mask: " + Integer.toBinaryString(expectedMask)
                            + ", actual: " + Integer.toBinaryString(machine.getLightsExpectationMask()));
        }

        @Test
        @DisplayName("[.##.] (size 4) should convert to 6 (0b0110)")
        void testExpectationDotHashHashDot() {
            String rawExpectation = "[.##.]";
            int expectedMask = 6; // 0b0110
            Machine machine = createMachine(rawExpectation, Collections.emptyList(), "{0}");
            assertEquals(expectedMask, machine.getLightsExpectationMask(),
                    () -> "For raw: " + rawExpectation + ", expected mask: " + Integer.toBinaryString(expectedMask)
                            + ", actual: " + Integer.toBinaryString(machine.getLightsExpectationMask()));
        }

        @Test
        @DisplayName("[.###.#] (size 6) should convert to 29 (0b011101)")
        void testExpectationDotHashHashHashDotHash() {
            String rawExpectation = "[.###.#]";
            int expectedMask = 29; // 0b011101 (MSB-first mapping for 6 bits)
            Machine machine = createMachine(rawExpectation, Collections.emptyList(), "{0}");
            assertEquals(expectedMask, machine.getLightsExpectationMask(),
                    () -> "For raw: " + rawExpectation + ", expected mask: " + Integer.toBinaryString(expectedMask)
                            + ", actual: " + Integer.toBinaryString(machine.getLightsExpectationMask()));
        }

        @Test
        @DisplayName("[#####] (size 5) should convert to 31 (0b11111)")
        void testAllHashesExpectation() {
            String rawExpectation = "[#####]";
            int expectedMask = 31; // 0b11111 (size 5)
            Machine machine = createMachine(rawExpectation, Collections.emptyList(), "{0}");
            assertEquals(expectedMask, machine.getLightsExpectationMask(),
                    () -> "For raw: " + rawExpectation + ", expected mask: " + Integer.toBinaryString(expectedMask)
                            + ", actual: " + Integer.toBinaryString(machine.getLightsExpectationMask()));
        }

        @Test
        @DisplayName("[.....] (size 5) should convert to 0")
        void testAllDotsExpectation() {
            String rawExpectation = "[.....]";
            int expectedMask = 0;
            Machine machine = createMachine(rawExpectation, Collections.emptyList(), "{0}");
            assertEquals(expectedMask, machine.getLightsExpectationMask(),
                    () -> "For raw: " + rawExpectation + ", expected mask: " + Integer.toBinaryString(expectedMask)
                            + ", actual: " + Integer.toBinaryString(machine.getLightsExpectationMask()));
        }

        @Test
        @DisplayName("[#.#.#] (size 5) should convert to 21 (0b10101)")
        void testAlternatingHashDotExpectation() {
            String rawExpectation = "[#.#.#]";
            int expectedMask = 21; // 0b10101 (size 5)
            Machine machine = createMachine(rawExpectation, Collections.emptyList(), "{0}");
            assertEquals(expectedMask, machine.getLightsExpectationMask(),
                    () -> "For raw: " + rawExpectation + ", expected mask: " + Integer.toBinaryString(expectedMask)
                            + ", actual: " + Integer.toBinaryString(machine.getLightsExpectationMask()));
        }
    }

    @Nested
    @DisplayName("Buttons Mask Conversion (MSB-first mapping for index)")
    class ButtonsMaskTests {
        // All test cases in this nested class have their expectedMask values
        // updated to reflect the MSB-first indexing of button indices.

        @Test
        @DisplayName("Empty button () with any size should convert to 0")
        void testEmptyButton() {
            String rawButton = "()";
            int expectedSize = 5;
            int expectedMask = 0;
            Machine machine = createMachine(getLightsExpectationForSize(expectedSize),
                    Collections.singletonList(rawButton), "{0}");
            assertEquals(1, machine.getButtonsMasks().size());
            assertEquals(expectedMask, machine.getButtonsMasks().get(0),
                    () -> "For raw: " + rawButton + ", size: " + expectedSize + ", expected mask: "
                            + Integer.toBinaryString(expectedMask) + ", actual: "
                            + Integer.toBinaryString(machine.getButtonsMasks().get(0)));
        }

        @Test
        @DisplayName("Button (0) with size 1 should convert to 1 (0b1)")
        void testButton0Size1() {
            String rawButton = "(0)";
            int expectedSize = 1;
            int expectedMask = 1; // 0b1 (index 0 is MSB, which is also LSB for size 1)
            Machine machine = createMachine(getLightsExpectationForSize(expectedSize),
                    Collections.singletonList(rawButton), "{0}");
            assertEquals(1, machine.getButtonsMasks().size());
            assertEquals(expectedMask, machine.getButtonsMasks().get(0),
                    () -> "For raw: " + rawButton + ", size: " + expectedSize + ", expected mask: "
                            + Integer.toBinaryString(expectedMask) + ", actual: "
                            + Integer.toBinaryString(machine.getButtonsMasks().get(0)));
        }

        @Test
        @DisplayName("Button (0) with size 5 should convert to 16 (0b10000)")
        void testButton0Size5() {
            String rawButton = "(0)";
            int expectedSize = 5;
            int expectedMask = 16; // 0b10000 (index 0 is MSB, which is bit 4)
            Machine machine = createMachine(getLightsExpectationForSize(expectedSize),
                    Collections.singletonList(rawButton), "{0}");
            assertEquals(1, machine.getButtonsMasks().size());
            assertEquals(expectedMask, machine.getButtonsMasks().get(0),
                    () -> "For raw: " + rawButton + ", size: " + expectedSize + ", expected mask: "
                            + Integer.toBinaryString(expectedMask) + ", actual: "
                            + Integer.toBinaryString(machine.getButtonsMasks().get(0)));
        }

        @Test
        @DisplayName("Button (1) with size 2 should convert to 1 (0b01)")
        void testButton1Size2() {
            String rawButton = "(1)";
            int expectedSize = 2;
            int expectedMask = 1; // 0b01 (index 1 is LSB, which is bit 0)
            Machine machine = createMachine(getLightsExpectationForSize(expectedSize),
                    Collections.singletonList(rawButton), "{0}");
            assertEquals(1, machine.getButtonsMasks().size());
            assertEquals(expectedMask, machine.getButtonsMasks().get(0),
                    () -> "For raw: " + rawButton + ", size: " + expectedSize + ", expected mask: "
                            + Integer.toBinaryString(expectedMask) + ", actual: "
                            + Integer.toBinaryString(machine.getButtonsMasks().get(0)));
        }

        @Test
        @DisplayName("Button (0,1) with size 2 should convert to 3 (0b11)")
        void testButton01Size2() {
            String rawButton = "(0,1)";
            int expectedSize = 2;
            int expectedMask = 3; // 0b11 (index 0 is MSB, index 1 is LSB)
            Machine machine = createMachine(getLightsExpectationForSize(expectedSize),
                    Collections.singletonList(rawButton), "{0}");
            assertEquals(1, machine.getButtonsMasks().size());
            assertEquals(expectedMask, machine.getButtonsMasks().get(0),
                    () -> "For raw: " + rawButton + ", size: " + expectedSize + ", expected mask: "
                            + Integer.toBinaryString(expectedMask) + ", actual: "
                            + Integer.toBinaryString(machine.getButtonsMasks().get(0)));
        }

        @Test
        @DisplayName("Button (0,1,2) with size 3 should convert to 7 (0b111)")
        void testButton012Size3() {
            String rawButton = "(0,1,2)";
            int expectedSize = 3;
            int expectedMask = 7; // 0b111 (index 0=MSB, index 1=mid, index 2=LSB)
            Machine machine = createMachine(getLightsExpectationForSize(expectedSize),
                    Collections.singletonList(rawButton), "{0}");
            assertEquals(1, machine.getButtonsMasks().size());
            assertEquals(expectedMask, machine.getButtonsMasks().get(0),
                    () -> "For raw: " + rawButton + ", size: " + expectedSize + ", expected mask: "
                            + Integer.toBinaryString(expectedMask) + ", actual: "
                            + Integer.toBinaryString(machine.getButtonsMasks().get(0)));
        }

        @Test
        @DisplayName("Button (0,1,2) with size 4 should convert to 14 (0b1110)")
        void testButton012Size4() {
            String rawButton = "(0,1,2)";
            int expectedSize = 4;
            int expectedMask = 14; // 0b1110 (indices 0,1,2 means bits 3,2,1 set from MSB)
            Machine machine = createMachine(getLightsExpectationForSize(expectedSize),
                    Collections.singletonList(rawButton), "{0}");
            assertEquals(1, machine.getButtonsMasks().size());
            assertEquals(expectedMask, machine.getButtonsMasks().get(0),
                    () -> "For raw: " + rawButton + ", size: " + expectedSize + ", expected mask: "
                            + Integer.toBinaryString(expectedMask) + ", actual: "
                            + Integer.toBinaryString(machine.getButtonsMasks().get(0)));
        }

        @Test
        @DisplayName("Button (0,3) with size 4 should convert to 9 (0b1001)")
        void testButton03Size4() {
            String rawButton = "(0,3)";
            int expectedSize = 4;
            int expectedMask = 9; // 0b1001 (indices 0,3 means bits 3,0 set from MSB)
            Machine machine = createMachine(getLightsExpectationForSize(expectedSize),
                    Collections.singletonList(rawButton), "{0}");
            assertEquals(1, machine.getButtonsMasks().size());
            assertEquals(expectedMask, machine.getButtonsMasks().get(0),
                    () -> "For raw: " + rawButton + ", size: " + expectedSize + ", expected mask: "
                            + Integer.toBinaryString(expectedMask) + ", actual: "
                            + Integer.toBinaryString(machine.getButtonsMasks().get(0)));
        }

        @Test
        @DisplayName("Button (0,1,2,3,4) with size 5 should convert to 31 (0b11111)")
        void testButton01234Size5() {
            String rawButton = "(0,1,2,3,4)";
            int expectedSize = 5;
            int expectedMask = 31; // 0b11111 (all bits set)
            Machine machine = createMachine(getLightsExpectationForSize(expectedSize),
                    Collections.singletonList(rawButton), "{0}");
            assertEquals(1, machine.getButtonsMasks().size());
            assertEquals(expectedMask, machine.getButtonsMasks().get(0),
                    () -> "For raw: " + rawButton + ", size: " + expectedSize + ", expected mask: "
                            + Integer.toBinaryString(expectedMask) + ", actual: "
                            + Integer.toBinaryString(machine.getButtonsMasks().get(0)));
        }

        @Test
        @DisplayName("Button (1,2) with size 5 should convert to 12 (0b01100)")
        void testButton12Size5() {
            String rawButton = "(1,2)";
            int expectedSize = 5;
            int expectedMask = 12; // 0b01100 (indices 1,2 means bits 3,2 set from MSB for size 5)
            Machine machine = createMachine(getLightsExpectationForSize(expectedSize),
                    Collections.singletonList(rawButton), "{0}");
            assertEquals(1, machine.getButtonsMasks().size());
            assertEquals(expectedMask, machine.getButtonsMasks().get(0),
                    () -> "For raw: " + rawButton + ", size: " + expectedSize + ", expected mask: "
                            + Integer.toBinaryString(expectedMask) + ", actual: "
                            + Integer.toBinaryString(machine.getButtonsMasks().get(0)));
        }

        @Test
        @DisplayName("Button (1,2) with size 6 should convert to 24 (0b011000)") // This is your specified expectation
        void testButton12Size6() {
            String rawButton = "(1,2)";
            int expectedSize = 6;
            int expectedMask = 24; // 0b011000 (indices 1,2 means bits 4,3 set from MSB for size 6)
            Machine machine = createMachine(getLightsExpectationForSize(expectedSize),
                    Collections.singletonList(rawButton), "{0}");
            assertEquals(1, machine.getButtonsMasks().size());
            assertEquals(expectedMask, machine.getButtonsMasks().get(0),
                    () -> "For raw: " + rawButton + ", size: " + expectedSize + ", expected mask: "
                            + Integer.toBinaryString(expectedMask) + ", actual: "
                            + Integer.toBinaryString(machine.getButtonsMasks().get(0)));
        }

        @Test
        @DisplayName("Should convert multiple buttons correctly for size 5")
        void shouldConvertMultipleButtons() {
            String lights = getLightsExpectationForSize(5); // Size 5
            List<String> buttonsRaw = Arrays.asList("(0,1)", "(2,3)", "()");
            Machine machine = createMachine(lights, buttonsRaw, "{0}");

            // (0,1) size 5 -> 0b11000 (24)
            // (2,3) size 5 -> 0b00110 (6)
            // () size 5 -> 0b00000 (0)
            List<Integer> expectedMasks = Arrays.asList(24, 6, 0);

            assertEquals(3, machine.getButtonsMasks().size());
            assertEquals(expectedMasks, machine.getButtonsMasks());
        }
    }

    @Nested
    @DisplayName("Integration Tests: Lights Expectation and Buttons Matching Size")
    class IntegrationTests {

        @Test
        @DisplayName("Should correctly convert a full machine entry")
        void shouldConvertFullMachineEntry() {
            String lightsRaw = "[.###.#]"; // Size 6 -> .###.# -> 011101 -> 29
            List<String> buttonsRaw = Arrays.asList("(0,1,2,3,4)", "(0,3,4)");
            String joltage = "{10,11,11,5,10,5}";

            Machine machine = new Machine(lightsRaw, buttonsRaw, joltage);

            assertEquals(lightsRaw, machine.getLightsExpectationRaw());
            assertEquals(buttonsRaw, machine.getButtonsRaw());
            assertEquals(joltage, machine.getJoltage());

            // Lights expectation mask (MSB-first mapping)
            // .###.# (length 6)
            // i=0 (.): bit 5 (0)
            // i=1 (#): bit 4 (16)
            // i=2 (#): bit 3 (8)
            // i=3 (#): bit 2 (4)
            // i=4 (.): bit 1 (0)
            // i=5 (#): bit 0 (1)
            // Total: 16+8+4+1 = 29
            assertEquals(29, machine.getLightsExpectationMask());

            // Button 1: (0,1,2,3,4) with size 6 (MSB-first mapping for indices)
            // index 0 -> bit 5 (32)
            // index 1 -> bit 4 (16)
            // index 2 -> bit 3 (8)
            // index 3 -> bit 2 (4)
            // index 4 -> bit 1 (2)
            // Total: 32+16+8+4+2 = 62
            assertEquals(62, machine.getButtonsMasks().get(0)); // 0b111110

            // Button 2: (0,3,4) with size 6 (MSB-first mapping for indices)
            // index 0 -> bit 5 (32)
            // index 3 -> bit 2 (4)
            // index 4 -> bit 1 (2)
            // Total: 32+4+2 = 38
            assertEquals(38, machine.getButtonsMasks().get(1)); // 0b100110
        }

        @Test
        @DisplayName("Should handle different sizes correctly")
        void shouldHandleDifferentSizes() {
            String lightsRaw = "[##.#]"; // Size 4 -> ##.# -> 1101 -> 13
            List<String> buttonsRaw = Arrays.asList("(1,2,3)", "(0)");
            String joltage = "{5,6,7}";

            Machine machine = new Machine(lightsRaw, buttonsRaw, joltage);

            assertEquals(13, machine.getLightsExpectationMask()); // 0b1101 (size 4)

            // Button 1: (1,2,3) with size 4 (MSB-first mapping for indices)
            // index 1 -> bit 2 (4)
            // index 2 -> bit 1 (2)
            // index 3 -> bit 0 (1)
            // Total: 4+2+1 = 7
            assertEquals(7, machine.getButtonsMasks().get(0)); // 0b0111

            // Button 2: (0) with size 4 (MSB-first mapping for indices)
            // index 0 -> bit 3 (8)
            // Total: 8
            assertEquals(8, machine.getButtonsMasks().get(1)); // 0b1000
        }

        @Test
        @DisplayName("Should allow XORing of masks (with new button interpretation)")
        void shouldAllowXORingOfMasks() {
            String lightsRaw = "[.###.#]"; // Size 6 -> 29 (0b011101)
            List<String> buttonsRaw = Collections.singletonList("(0,1,2,3,4)"); // Size 6 -> 62 (0b111110)
            String joltage = "{0}";

            Machine machine = new Machine(lightsRaw, buttonsRaw, joltage);

            int lightsMask = machine.getLightsExpectationMask();        // 29 (0b011101)
            int firstButtonMask = machine.getButtonsMasks().get(0);     // 62 (0b111110)

            int xorResult = lightsMask ^ firstButtonMask; // 0b011101 ^ 0b111110 = 0b100011 = 35

            assertEquals(35, xorResult);
        }
    }
}