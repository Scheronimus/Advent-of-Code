package year2025.day01;

import java.util.ArrayList;

public class Dial {
    int currentPos;
    ArrayList<Integer> history = new ArrayList<>();
    int crossZero = 0;

    public Dial() {
        super();
        currentPos = 50;
        history.add(currentPos);
    }

    void move(String cmd) {
        char direction = cmd.charAt(0);
        int distance = Integer.parseInt(cmd.substring(1));
        // System.out.println(direction);
        // System.out.println(distance);
        if (direction == 'L') {
            distance = -distance;
        }


        int newPos = Math.floorMod((currentPos + distance), 100);
        // System.out.println(currentPos + distance);

        int passingZero = Math.abs(Math.floorDiv((currentPos + distance), 100));

        if (currentPos == 0 && direction == 'L') {
            passingZero--;
        }
        if (newPos == 0 && direction == 'L') {
            passingZero++;
        }
        crossZero += passingZero;
        // System.out.println(Math.abs(Math.floorDiv((currentPos + distance), 100)));
        System.out.println(cmd + " : " + currentPos + " " + newPos + " --> " + passingZero + "(" + crossZero + ")");
        // System.out.println(crossZero + " " + Math.abs(Math.floorDiv((currentPos + distance), 100)));
        // System.out.println("new Pos : " + newPos);
        currentPos = newPos;
        history.add(currentPos);
    }

    long countZero() {
        return history.stream().filter(val -> (val == 0)).count();

    }

    int crossZero() {
        return crossZero;

    }

}
