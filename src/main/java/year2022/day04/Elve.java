package year2022.day04;

public class Elve {
    int min;
    int max;

    public Elve(final int min, final int max) {
        super();
        this.min = min;
        this.max = max;
    }

    public boolean contains(final Elve e) {
        return this.min <= e.min && this.max >= e.max;
    }

    public boolean overlapWith(final Elve e) {
        if (this.min <= e.min) {
            return this.max >= e.min;
        } else {
            return e.max >= this.min;
        }
    }
}
