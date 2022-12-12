package day09;

import java.util.HashSet;
import java.util.Set;

public class Grid {
    Knot head = new Knot();
    Knot tail = new Knot();
    Knot tail2 = new Knot();
    Knot tail3 = new Knot();
    Knot tail4 = new Knot();
    Knot tail5 = new Knot();
    Knot tail6 = new Knot();
    Knot tail7 = new Knot();
    Knot tail8 = new Knot();
    Knot tail9 = new Knot();

    Set<Knot> visited = new HashSet<>();
    Set<Knot> visitedTail9 = new HashSet<>();


    public void moveHead(final Motion m) {
        for (int i = 0; i < m.length; i++) {
            head.move(m.direction);
            tail.follow(head);
            tail2.follow(tail);
            tail3.follow(tail2);
            tail4.follow(tail3);
            tail5.follow(tail4);
            tail6.follow(tail5);
            tail7.follow(tail6);
            tail8.follow(tail7);
            tail9.follow(tail8);

            visited.add(new Knot(tail.x, tail.y));
            visitedTail9.add(new Knot(tail9.x, tail9.y));
        }

    }
}
