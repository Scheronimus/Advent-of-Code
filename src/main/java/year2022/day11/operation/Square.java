package year2022.day11.operation;

import year2022.day11.Item;

public class Square implements IOperation {

    @Override
    public void run(final Item item) {
        item.setWorryLevel(item.getWorryLevel().multiply(item.getWorryLevel()));
    }

}
