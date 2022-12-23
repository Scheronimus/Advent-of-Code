package day22;

public class MoveForward extends Action {
    int distance;

    public MoveForward(int distance) {
        super();
        this.distance = distance;
    }

    @Override
    public void doIt(Position p, MapOfTheBoard map) {

        int newX = p.getX() + distance;
        for (int x = p.getX(); x < p.getX() + distance; x++) {
            System.out.println(map.get(x + 1, p.getY()));
            if (map.get(x + 1, p.getY()) == '#') {
                newX = x;
                break;
            }
        }
        p.setX(newX);


    }


}
