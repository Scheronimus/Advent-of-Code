package year2022.day11.operation;

import java.math.BigInteger;

import year2022.day11.Item;

public class Multiplication implements IOperation {

    int toMultiply;


    public Multiplication(final int toMultiply) {
        super();
        this.toMultiply = toMultiply;
    }


    @Override
    public void run(final Item item) {
        item.setWorryLevel(item.getWorryLevel().multiply(BigInteger.valueOf(toMultiply)));
    }
}
