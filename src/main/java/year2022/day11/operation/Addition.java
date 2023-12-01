package year2022.day11.operation;

import java.math.BigInteger;

import year2022.day11.Item;

public class Addition implements IOperation {

    int toAdd;


    public Addition(final int toAdd) {
        super();
        this.toAdd = toAdd;
    }


    @Override
    public void run(final Item item) {
        item.setWorryLevel(item.getWorryLevel().add(BigInteger.valueOf(toAdd)));
    }

}
