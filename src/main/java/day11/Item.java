package day11;

import java.math.BigInteger;

public class Item {
    // private long worryLevel;
    BigInteger worryLevel;

    public Item(final int worryLevel) {
        super();
        this.worryLevel = new BigInteger(Integer.toString(worryLevel));
    }

    public BigInteger getWorryLevel() {
        return worryLevel;
    }

    public void setWorryLevel(final BigInteger worryLevel) {
        this.worryLevel = worryLevel;
    }
}
