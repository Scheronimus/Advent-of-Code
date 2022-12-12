package day09;

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

public class RopeBridge extends Puzzle {

    List<Motion> motionSeries = new ArrayList<>();
    Grid bridge = new Grid();

    protected RopeBridge(final String input) throws IOException {
        super(input);

        Pattern motionPattern = Pattern.compile("(\\w) (\\d+)");
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                Matcher motionMatcher = motionPattern.matcher(line);
                if (motionMatcher.find()) {
                    motionSeries.add(new Motion(motionMatcher.group(1), Integer.parseInt(motionMatcher.group(2))));
                }
            }
        }
        runMotionSeries(motionSeries, bridge);
    }

    public void runMotionSeries(final List<Motion> motionSeries, final Grid bridge) {
        for (Motion motion : motionSeries) {
            bridge.moveHead(motion);
        }
    }

    @Override
    public Object getAnswer1() {

        return bridge.visited.size();
    }

    @Override
    public Object getAnswer2() {
        return bridge.visitedTail9.size();
    }

    public static void main(final String[] args) throws IOException {
        RopeBridge ropeBridge = new RopeBridge("day09/input");
        System.out.println("Answer1: " + ropeBridge.getAnswer1());
        System.out.println("Answer2: " + ropeBridge.getAnswer2());
    }

}
