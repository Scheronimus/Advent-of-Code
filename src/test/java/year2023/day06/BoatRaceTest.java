package year2023.day06;

import org.junit.jupiter.api.Test;

public class BoatRaceTest {


    @Test
    public void testGetDiscriminant() {
        BoatRace b = new BoatRace(7, 9);
        System.out.println(b.getDiscriminant());
        b.getRacines();
    }
}
