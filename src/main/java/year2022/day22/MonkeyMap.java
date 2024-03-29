package year2022.day22;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helper.Puzzle;

public class MonkeyMap extends Puzzle {

    MapOfTheBoard map;
    List<Action> actions = new ArrayList<>();

    protected MonkeyMap(String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;
            List<String> values = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if (line.contains(".")) {
                    values.add(line);
                } else if (!line.isEmpty()) {

                    Pattern reciepePattern = Pattern.compile("(\\d+)|([RL])");

                    Matcher reciepeMatcher = reciepePattern.matcher(line);
                    while (reciepeMatcher.find()) {

                        String number = reciepeMatcher.group(1);
                        String letter = reciepeMatcher.group(2);

                        if (number == null) {
                            actions.add(new Turn(letter));
                        } else {
                            actions.add(new MoveForward(Integer.parseInt(number)));
                        }
                    }
                }
            }
            map = new MapOfTheBoard(values);
        }
    }

    int generateFinalPassword(Position p) {
        int res = p.getY() * 1000 + p.getX() * 4;

        switch (p.getDirection()) {
            case UP: {
                res += 3;
                break;
            }
            case RIGHT: {
                break;
            }
            case DOWN: {
                res += 1;
                break;
            }
            case LEFT: {
                res += 2;
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + p.getDirection());
        }

        return res;
    }

    @Override
    public Object getAnswer1() {
        Position position = map.getStatingPoint();
        System.out.println(position);
        for (Action action : actions) {
            action.doIt(position, map);
            System.out.println(position);
        }

        return generateFinalPassword(position);
    }

    @Override
    public Object getAnswer2() {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(final String[] args) throws IOException {
        MonkeyMap monkeyMap = new MonkeyMap("year2022/day22/input");
        System.out.println("Answer1: " + monkeyMap.getAnswer1());
        System.out.println("Answer2: " + monkeyMap.getAnswer2());
    }
}
