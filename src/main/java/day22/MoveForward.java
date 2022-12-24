package day22;

import helper.Direction;

public class MoveForward extends Action {
    int distance;

    public MoveForward(int distance) {
        super();
        this.distance = distance;
    }

    @Override
    public void doIt(Position p, MapOfTheBoard map) {

        int[] valRow = map.rows.get(p.getY());
        int[] valCol = map.columns.get(p.getX());

        if (p.getDirection() == Direction.RIGHT) {
            int newX = wrapArround(p.getX() + distance, valRow);
            for (int x = p.getX(); x < p.getX() + distance; x++) {
                int xWithWrapArround = wrapArround(x, valRow);
                if (map.get(wrapArround(x + 1, valRow), p.getY()) == '#') {
                    newX = xWithWrapArround;
                    break;
                }
            }
            p.setX(newX);
            return;
        }
        if (p.getDirection() == Direction.LEFT) {
            int newX = wrapArround(p.getX() - distance, valRow);
            for (int x = p.getX(); x > p.getX() - distance; x--) {
                int xWithWrapArround = wrapArround(x, valRow);
                if (map.get(wrapArround(x - 1, valRow), p.getY()) == '#') {
                    newX = xWithWrapArround;
                    break;
                }
            }
            p.setX(newX);
            return;
        }
        if (p.getDirection() == Direction.DOWN) {
            int newY = wrapArround(p.getY() + distance, valCol);
            for (int y = p.getY(); y < p.getY() + distance; y++) {
                int xWithWrapArround = wrapArround(y, valCol);
                if (map.get(p.getX(), wrapArround(y + 1, valCol)) == '#') {
                    newY = xWithWrapArround;
                    break;
                }
            }
            p.setY(newY);
            return;
        }
        if (p.getDirection() == Direction.UP) {
            int newY = wrapArround(p.getY() - distance, valCol);
            for (int y = p.getY(); y > p.getY() - distance; y--) {
                int xWithWrapArround = wrapArround(y, valCol);
                if (map.get(p.getX(), wrapArround(y - 1, valCol)) == '#') {
                    newY = xWithWrapArround;
                    break;
                }
            }
            p.setY(newY);
            return;
        }


    }

    int wrapArround(int x, int[] val) {
        return Math.floorMod(x - val[0], val[1] - val[0] + 1) + val[0];
    }


}
