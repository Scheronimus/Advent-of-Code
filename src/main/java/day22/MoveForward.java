package day22;

public class MoveForward extends Action {
    int distance;

    public MoveForward(int distance) {
        super();
        this.distance = distance;
    }

    @Override
    public void doIt(Position p, MapOfTheBoard map) {


        int y = p.getY();
        int[] val = map.rows.get(y);


        int newX = wrapArround(p.getX() + distance, val);
        for (int x = p.getX(); x < p.getX() + distance; x++) {

            int xWithWrapArround = wrapArround(x, val);


            System.out.println(map.get(wrapArround(x + 1, val), p.getY()));
            if (map.get(wrapArround(x + 1, val), p.getY()) == '#') {
                newX = xWithWrapArround;
                break;
            }
        }
        p.setX(newX);


    }

    private int wrapArround(int x, int[] val) {
        // @TODO to fix...
        int xWithWrapArround = x;
        if (x > val[1]) {
            xWithWrapArround = x % val[1] + val[0];

        }
        return xWithWrapArround;
    }


}
