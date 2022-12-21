package day21;

import java.util.List;

import helper.Polynomial;

public class Monkey {
    String id;
    Polynomial value = null;
    Operation operation = null;

    public Monkey(final String id, final Polynomial value) {
        super();
        this.id = id;
        this.value = value;
    }

    public Monkey(final String id, final Operation operation) {
        super();
        this.id = id;
        this.operation = operation;
    }

    public Monkey(final Monkey m) {
        super();
        this.id = m.id;
        this.value = m.value;
        this.operation = m.operation;
    }

    @Override
    public String toString() {
        String toString = id + ": ";
        if (value != null) {
            toString += value;
        } else {
            toString += operation.toString();
        }

        return toString;
    }

    public Polynomial computeValue(final List<Monkey> monkeys) {
        if (value != null) {
            return value;
        } else {

            Polynomial calculated = operation.run(findMonkeyById(operation.idLeft, monkeys).computeValue(monkeys),
                    findMonkeyById(operation.idRight, monkeys).computeValue(monkeys));
            value = calculated;
            return value;
        }

    }

    public static Monkey findMonkeyById(final String id, final List<Monkey> monkeys) {
        Monkey find = null;
        for (Monkey monkey : monkeys) {
            if (monkey.id.equals(id)) {
                find = monkey;
                break;
            }
        }
        return find;
    }


}
