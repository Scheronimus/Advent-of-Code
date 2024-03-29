package year2022.day22;

import helper.Direction;

public class Position {
    private int x;
    private int y;
    private Direction direction;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Position(int x, int y, Direction direction) {
        super();
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Position [x=" + x + ", y=" + y + ", direction=" + direction + "]";
    }


}

