package day19;

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

public class NotEnoughMinerals extends Puzzle {
    List<Blueprint> blueprints = new ArrayList<>();

    protected NotEnoughMinerals(final String input) throws IOException {
        super(input);
        Pattern reciepePattern = Pattern.compile(
                "Blueprint (\\d+): Each ore robot costs (\\d+) ore\\. Each clay robot costs (\\d+) ore\\. Each obsidian robot costs (\\d+) ore and (\\d+) clay\\. Each geode robot costs (\\d+) ore and (\\d+) obsidian\\.");
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                Matcher reciepeMatcher = reciepePattern.matcher(line);
                while (reciepeMatcher.find()) {
                    Reciepe oreRobotReciepe = Reciepe.newOreRobotReciepe(Integer.parseInt(reciepeMatcher.group(2)));
                    Reciepe clayRobotReciepe = Reciepe.newClayRobotReciepe(Integer.parseInt(reciepeMatcher.group(3)));
                    Reciepe obsidianRobotReciepe = Reciepe.newObsidianRobotReciepe(
                            Integer.parseInt(reciepeMatcher.group(4)), Integer.parseInt(reciepeMatcher.group(5)));
                    Reciepe geodeRobotReciepe = Reciepe.newGeodeRobotReciepe(Integer.parseInt(reciepeMatcher.group(6)),
                            Integer.parseInt(reciepeMatcher.group(7)));

                    blueprints.add(
                            new Blueprint(oreRobotReciepe, clayRobotReciepe, obsidianRobotReciepe, geodeRobotReciepe));
                }
            }
        }
    }

    public record State(Material robotCount, Material currentMaterial) {
    }

    @Override
    public Object getAnswer1() {

        // Material robotCount = new Material(1, 0, 0, 0);
        // Material currentMaterial = new Material(0, 0, 0, 0);
        int solution = 0;

        // Blueprint b = null;
        int maxTime = 24;
        int index = 1;
        for (Blueprint blueprint : blueprints) {
            int max = getMaxGeode(blueprint, maxTime);
            solution += getQualityLevel(index, max);
            index++;
        }

        return solution;
    }

    private int getMaxGeode(Blueprint blueprint, int maxTime) {
        List<State> states = new ArrayList<>();
        states.add(new State(new Material(1, 0, 0, 0), new Material(0, 0, 0, 0)));

        for (int i = 1; i <= maxTime; i++) {
            List<State> newStates = new ArrayList<>();
            for (State state : states) {

                // state.currentMaterial.add(robotCount);
                // Reciepe reciepe = b.oreRobot;
                processReciepe(blueprint.oreRobot, state.robotCount, state.currentMaterial, newStates);
                processReciepe(blueprint.clayRobot, state.robotCount, state.currentMaterial, newStates);
                processReciepe(blueprint.obsidianRobot, state.robotCount, state.currentMaterial, newStates);
                processReciepe(blueprint.geodeRobot, state.robotCount, state.currentMaterial, newStates);

                state.currentMaterial.add(state.robotCount);
            }
            states.addAll(newStates);
            // System.out.println("hello");
        }

        int max = 0;
        for (State state : states) {
            max = Math.max(max, state.currentMaterial().geode);
        }
        return max;
    }

    private int getQualityLevel(final int index, final int maxGeode) {
        return index * maxGeode;
    }

    private void processReciepe(final Reciepe reciepe, final Material robotCount, final Material currentMaterial,
            final List<State> states) {
        if (reciepe.isPossible(currentMaterial) && !reciepe.isWaiting(currentMaterial, robotCount)) {

            // System.out.println(reciepe.type);

            Material newRobotCount = new Material(robotCount);
            newRobotCount.add(reciepe.created());
            Material newCurrentMaterial = new Material(currentMaterial);
            newCurrentMaterial.add(robotCount);
            newCurrentMaterial.remove(reciepe.cost);
            states.add(new State(newRobotCount, newCurrentMaterial));
        }
    }

    @Override
    public Object getAnswer2() {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(final String[] args) throws IOException {
        NotEnoughMinerals notEnoughMinerals = new NotEnoughMinerals("day19/input");
        System.out.println("Answer1: " + notEnoughMinerals.getAnswer1());
        System.out.println("Answer2: " + notEnoughMinerals.getAnswer2());
    }

}
