package day22;

import helper.Direction;

public class Turn extends Action {
    Direction turnDirection;

    public Turn(String direction) {
        super();
        if (direction.equals("R")) {
            this.turnDirection = helper.Direction.RIGHT;
        } else if (direction.equals("L")) {
            this.turnDirection = helper.Direction.LEFT;
        } else {
            throw new IllegalArgumentException("Unexpected value: " + direction);
        }
    }

    @Override
    public void doIt(Position p, MapOfTheBoard map) {
        switch (p.getDirection()) {
            case UP: {
                if (turnDirection == helper.Direction.RIGHT) {
                    p.setDirection(helper.Direction.RIGHT);
                } else {
                    p.setDirection(helper.Direction.LEFT);
                }
                break;
            }
            case RIGHT: {
                if (turnDirection == helper.Direction.RIGHT) {
                    p.setDirection(helper.Direction.DOWN);
                } else {
                    p.setDirection(helper.Direction.UP);
                }
                break;
            }
            case DOWN: {
                if (turnDirection == helper.Direction.RIGHT) {
                    p.setDirection(helper.Direction.LEFT);
                } else {
                    p.setDirection(helper.Direction.RIGHT);
                }
                break;
            }
            case LEFT: {
                if (turnDirection == helper.Direction.RIGHT) {
                    p.setDirection(helper.Direction.UP);
                } else {
                    p.setDirection(helper.Direction.DOWN);
                }
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + p.getDirection());
        }

    }
}
