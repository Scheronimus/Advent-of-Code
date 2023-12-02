package year2023.day02;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import helper.Puzzle;


public class CubeConundrum extends Puzzle {

    List<Game> games = new ArrayList<>();

    public CubeConundrum(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] gameSplit = line.split(":");

                int gameId = Integer.parseInt(gameSplit[0].replace("Game ", ""));

                String[] setSplit = gameSplit[1].trim().split(";");
                List<CubeSet> cubeSets = new ArrayList<>();

                for (String set : setSplit) {
                    set = set.trim();
                    String[] colorSplit = set.split(",");
                    int red = 0;
                    int blue = 0;
                    int green = 0;
                    for (String color : colorSplit) {
                        color = color.trim();
                        if (color.contains("red")) {
                            red = Integer.valueOf(color.replace(" red", ""));
                        } else if (color.contains("blue")) {
                            blue = Integer.valueOf(color.replace(" blue", ""));
                        } else if (color.contains("green")) {
                            green = Integer.valueOf(color.replace(" green", ""));
                        }
                    }
                    cubeSets.add(new CubeSet(blue, green, red));
                }
                games.add(new Game(gameId, cubeSets));
            }
        }
    }


    @Override
    public Object getAnswer1() {
        int res = 0;
        for (Game game : games) {
            if (game.isValid(14, 13, 12)) {
                res += game.id;
            }

        }
        return res;
    }


    @Override
    public Object getAnswer2() {
        int res = 0;
        for (Game game : games) {
            res += game.getMinimumSet().power();

        }
        return res;
    }

    public static void main(final String[] args) throws IOException {
        CubeConundrum cubeConundrum = new CubeConundrum("year2023/day02/input");
        System.out.println("Answer 1: " + cubeConundrum.getAnswer1());
        System.out.println("Answer 2: " + cubeConundrum.getAnswer2());
    }


}
