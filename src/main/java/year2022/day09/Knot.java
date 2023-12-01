package year2022.day09;

import java.util.Objects;

import helper.Direction;

public class Knot {
    int x = 0;
    int y = 0;

    public Knot() {
        super();
    }

    public Knot(final int x, final int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public void move(final Direction d) {
        switch (d) {
            case UP: {
                y++;
                break;
            }
            case DOWN: {
                y--;
                break;
            }
            case RIGHT: {
                x++;
                break;
            }
            case LEFT: {
                x--;
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + d);
        }
    }

    public void follow(final Knot k) {
        if (distance(this, k) >= 2) {
            if (k.x - this.x > 0) {
                this.move(Direction.RIGHT);
            }
            if (k.x - this.x < 0) {
                this.move(Direction.LEFT);
            }
            if (k.y - this.y > 0) {
                this.move(Direction.UP);
            }
            if (k.y - this.y < 0) {
                this.move(Direction.DOWN);
            }
        }
    }

    private double distance(final Knot k1, final Knot k2) {
        return Math.sqrt(Math.pow(k1.x - k2.x, 2) + Math.pow(k1.y - k2.y, 2));

    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Knot other = (Knot)obj;
        return x == other.x && y == other.y;
    }
}
