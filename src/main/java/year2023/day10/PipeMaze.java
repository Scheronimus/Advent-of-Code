package year2023.day10;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import helper.Direction;
import helper.Puzzle;


public class PipeMaze extends Puzzle {
    Maze maze;
    List<String> mazeMap = new ArrayList<>();

    public PipeMaze(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;
            while ((line = br.readLine()) != null) {
                mazeMap.add(line);
            }
            maze = new Maze(mazeMap);
        }
    }

    @Override
    public Object getAnswer1() {
        Tile start = maze.getStartingPoint();
        Tile next = null;
        Direction validDirection = null;
        for (Direction d : Direction.values()) {
            TileDirection tileDirection = new TileDirection(start.x, start.y, d);
            if (tileDirection.x >= 0 && tileDirection.y >= 0) {
                Tile nextTile = maze.getTile(tileDirection.x, tileDirection.y);
                if (nextTile.getNextTile(d) != null) {
                    validDirection = d;
                    next = nextTile;
                    break;
                }
            }
        }

        Tile current = next;
        Direction crrentDirection = validDirection;
        int step = 1;

        while (current.symbol != 'S') {
            TileDirection td = current.getNextTile(crrentDirection);
            current = maze.getTile(td.x, td.y);
            crrentDirection = td.direction;
            step++;
        }

        return step / 2;
    }


    @Override
    public Object getAnswer2() {
        Tile start = maze.getStartingPoint();
        Tile next = null;
        Direction validDirection = null;
        for (Direction d : Direction.values()) {
            TileDirection tileDirection = new TileDirection(start.x, start.y, d);
            if (tileDirection.x >= 0 && tileDirection.y >= 0) {
                Tile nextTile = maze.getTile(tileDirection.x, tileDirection.y);
                if (nextTile.getNextTile(d) != null) {
                    validDirection = d;
                    next = nextTile;
                    break;
                }
            }
        }


        List<Tile> mainLoop = findMainLoop(start, next, validDirection);

        int xMax = maze.mazeMap.get(0).length();
        int yMax = maze.mazeMap.size();
        char startChar = getStartCharBaseOnPossibleDirection(getPossibleDirection(start));
        List<String> mazeMapPurified = purifyMazeMap(mainLoop, startChar, xMax, yMax);


        return fillLoop(mazeMapPurified, xMax, yMax);
    }

    private List<Tile> findMainLoop(Tile start, Tile next, Direction validDirection) {
        List<Tile> mainLoop = new ArrayList<>();
        mainLoop.add(start);
        Tile current = next;
        Direction currentDirection = validDirection;
        int step = 1;

        while (current.symbol != 'S') {
            mainLoop.add(current);
            TileDirection td = current.getNextTile(currentDirection);
            current = maze.getTile(td.x, td.y);
            currentDirection = td.direction;
            step++;
        }
        return mainLoop;
    }

    private List<Direction> getPossibleDirection(Tile start) {
        List<Direction> startD = new ArrayList<>();
        for (Direction d : Direction.values()) {
            TileDirection tileDirection = new TileDirection(start.x, start.y, d);
            if (tileDirection.x >= 0 && tileDirection.y >= 0) {
                Tile nextTile = maze.getTile(tileDirection.x, tileDirection.y);
                if (nextTile.getNextTile(d) != null) {
                    startD.add(d);
                }
            }
        }
        return startD;
    }

    private int fillLoop(List<String> mazeMapPurified, int xMax, int yMax) {
        int res = 0;
        for (int y = 0; y < yMax; y++) {
            double t = 0;
            String temp = "";
            char previous = 'X';
            for (int x = 0; x < xMax; x++) {
                char c = mazeMapPurified.get(y).charAt(x);
                if (c == '|') {
                    t++;
                    temp += c;
                } else if (c == 'F' || c == 'L') {
                    t = t + 0.5;
                    temp += c;
                    previous = c;
                } else if (c == '7') {
                    if (previous == 'L') {
                        t = t + 0.5;
                    } else {
                        t = t - 0.5;
                    }
                    temp += c;
                    previous = 'X';
                } else if (c == 'J') {
                    if (previous == 'F') {
                        t = t + 0.5;
                    } else {
                        t = t - 0.5;
                    }
                    temp += c;
                    previous = 'X';
                } else if (c == '.' && Math.floor(t) % 2 == 1) {
                    temp += 'I';
                    res++;
                } else {
                    temp += c;
                }
            }
        }
        return res;
    }

    private List<String> purifyMazeMap(List<Tile> mainLoop, char startChar, int xMax, int yMax) {
        List<String> mazeMapPurified = new ArrayList<>();
        for (int y = 0; y < yMax; y++) {
            String temp = "";
            for (int x = 0; x < xMax; x++) {
                Tile currentT = maze.getTile(x, y);
                if (mainLoop.contains(currentT)) {
                    if (currentT.symbol == 'S') {
                        temp += startChar;
                    } else {
                        temp += currentT.symbol;
                    }
                } else {
                    temp += ".";
                }
            }
            mazeMapPurified.add(temp);
        }
        return mazeMapPurified;
    }


    private char getStartCharBaseOnPossibleDirection(List<Direction> startD) {
        if (startD.contains(Direction.UP) && startD.contains(Direction.DOWN)) {
            return '|';
        }
        if (startD.contains(Direction.RIGHT) && startD.contains(Direction.LEFT)) {
            return '-';
        }
        if (startD.contains(Direction.UP) && startD.contains(Direction.RIGHT)) {
            return 'L';
        }
        if (startD.contains(Direction.UP) && startD.contains(Direction.LEFT)) {
            return 'J';
        }
        if (startD.contains(Direction.DOWN) && startD.contains(Direction.RIGHT)) {
            return 'F';
        }
        if (startD.contains(Direction.DOWN) && startD.contains(Direction.LEFT)) {
            return '7';
        }
        return 'X';
    }

    public static void main(final String[] args) throws IOException {
        PipeMaze puzzle = new PipeMaze("year2023/day10/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }
}
