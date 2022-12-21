package day21;

public class Monkey {
    String id;
    Long value = null;
    Operation operation = null;

    public Monkey(final String id, final Long value) {
        super();
        this.id = id;
        this.value = value;
    }

    public Monkey(final String id, final Operation operation) {
        super();
        this.id = id;
        this.operation = operation;
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


}
