package day09;

import helper.Direction;

public class Motion {
    Direction direction;
    int length;

    public Motion(final String directionShort, final int length) {
        super();
        switch (directionShort) {
            case "U": {

                this.direction = Direction.UP;
                break;
            }
            case "L": {

                this.direction = Direction.LEFT;
                break;
            }
            case "D": {

                this.direction = Direction.DOWN;
                break;
            }
            case "R": {

                this.direction = Direction.RIGHT;
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + directionShort);
        }

        this.length = length;
    }


}
