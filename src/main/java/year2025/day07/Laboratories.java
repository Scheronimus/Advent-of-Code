package year2025.day07;

import java.io.IOException;
import java.util.List;

import helper.Point;
import helper.Puzzle;

public class Laboratories extends Puzzle {

    TachyonManifoldsDiagram tachyonManifoldsDiagram;

    public Laboratories(String input) throws IOException {
        super(input);
        parse(getLines());

        Point start = tachyonManifoldsDiagram.find('S');
        tachyonManifoldsDiagram.beam(start);
    }

    private void parse(List<String> lines) {
        tachyonManifoldsDiagram = new TachyonManifoldsDiagram(lines);
    }

    @Override
    public Object getAnswer1() {
        // tachyonManifoldsDiagram.print();
        // Point start = tachyonManifoldsDiagram.find('S');

        // tachyonManifoldsDiagram.beam(start);
        return tachyonManifoldsDiagram.splitCount;
    }

    @Override
    public Object getAnswer2() {
        // tachyonManifoldsDiagram.print();
        // Point start = tachyonManifoldsDiagram.find('S');

        // List<Point> lastBeam = tachyonManifoldsDiagram.beamL(start);
        System.out.println(tachyonManifoldsDiagram.countMap);
        long result = tachyonManifoldsDiagram.countMap.values().stream().mapToLong(Long::longValue).sum();
        return result;

    }

    public static void main(String[] args) throws IOException {
        Laboratories puzzle = new Laboratories("year2025/day07/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }
}
