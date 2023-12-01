package year2022.day11;

import java.math.BigInteger;

public class Test {
    int divisibleBy;

    public Test(final int divisibleBy) {
        super();
        this.divisibleBy = divisibleBy;
    }

    public boolean run(final Item item) {

        return item.getWorryLevel().remainder(BigInteger.valueOf(divisibleBy)).equals(BigInteger.valueOf(0));
    }
}

