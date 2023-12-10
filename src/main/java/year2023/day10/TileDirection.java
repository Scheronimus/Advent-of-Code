package year2023.day10;

import helper.Direction;

public class TileDirection {
    int x;
    int y;
    Direction direction;


    public TileDirection(int x, int y, Direction direction) {
        super();
        this.x = x;
        this.y = y;
        this.direction = direction;
        move(direction);
    }


    public void move(final Direction d) {
        switch (d) {
            case UP: {
                y--;
                break;
            }
            case DOWN: {
                y++;
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
}

