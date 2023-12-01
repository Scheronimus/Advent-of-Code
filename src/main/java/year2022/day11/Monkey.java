package year2022.day11;

import java.math.BigInteger;
import java.util.List;

import year2022.day11.operation.IOperation;

public class Monkey {
    static long moduloOptimizer = 1;
    private int id;
    private List<Item> items;
    private IOperation operation;
    private Test test;
    private int monkeyIfTrue;
    private int monkeyIfFalse;
    long inspected = 0;
    private List<Monkey> monkeyList;

    protected void setItems(final List<Item> items) {
        this.items = items;
    }

    protected void setOperation(final IOperation operation) {
        this.operation = operation;
    }

    protected void setTest(final Test test) {
        this.test = test;
        Monkey.addDiviserToOptimizer(test.divisibleBy);

    }

    public synchronized static void addDiviserToOptimizer(int div) {
        moduloOptimizer *= div;
    }


    public Monkey(final int id, final List<Monkey> monkeyList) {
        super();
        this.id = id;
        this.monkeyList = monkeyList;
    }

    protected int getId() {
        return id;
    }

    private void inspect(final Item item, final boolean reduceWorryLevel) {

        operation.run(item);
        if (reduceWorryLevel) {
            item.setWorryLevel(item.getWorryLevel().divide(BigInteger.valueOf(3)));
        } else {
            item.setWorryLevel(item.getWorryLevel().remainder(BigInteger.valueOf(moduloOptimizer)));
        }
        int monkeyToThrowId;
        if (test.run(item)) {
            monkeyToThrowId = monkeyIfTrue;
        } else {
            monkeyToThrowId = monkeyIfFalse;
        }
        Monkey monkey = monkeyList.get(monkeyToThrowId);

        monkey.items.add(item);
    }

    protected void setMonkeyIfTrue(final int monkeyIfTrue) {
        this.monkeyIfTrue = monkeyIfTrue;
    }

    protected void setMonkeyIfFalse(final int monkeyIfFalse) {
        this.monkeyIfFalse = monkeyIfFalse;
    }

    public void inspectAllItems(final boolean reduceWorryLevel) {
        for (Item item : items) {
            inspect(item, reduceWorryLevel);
            inspected++;
        }
        items.clear();
    }

}
