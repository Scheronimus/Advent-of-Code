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

    private int getMaxGeode(final Blueprint blueprint, final int maxTime) {

        Material maxCost = blueprint.getMaximals();

        List<State> states = new ArrayList<>();
        states.add(new State(new Material(1, 0, 0, 0), new Material(0, 0, 0, 0)));

        for (int i = 1; i <= maxTime; i++) {
            List<State> newStates = new ArrayList<>();


            for (State state : states) {
                if (i < maxTime) {
                    // state.currentMaterial.add(robotCount);
                    // Reciepe reciepe = b.oreRobot;
                    int timeLeft = maxTime - i;
                    if (i < maxTime - 1) {

                        processReciepe(blueprint.oreRobot, state.robotCount, state.currentMaterial, newStates, maxCost,
                                timeLeft);
                        processReciepe(blueprint.clayRobot, state.robotCount, state.currentMaterial, newStates, maxCost,
                                timeLeft);
                        processReciepe(blueprint.obsidianRobot, state.robotCount, state.currentMaterial, newStates,
                                maxCost, timeLeft);
                    }
                    processReciepe(blueprint.geodeRobot, state.robotCount, state.currentMaterial, newStates, maxCost,
                            timeLeft);
                }
                state.currentMaterial.add(state.robotCount);

            }
            states.addAll(newStates);
            System.out.println("time: " + i);
        }

        int max = 0;
        for (State state : states) {
            max = Math.max(max, state.currentMaterial().geode);

        }


        states.clear();
        System.out.println("max: " + max);

        return max;
    }

    private int getQualityLevel(final int index, final int maxGeode) {
        return index * maxGeode;
    }

    private void processReciepe(final Reciepe reciepe, final Material robotCount, final Material currentMaterial,
            final List<State> states, final Material maxCost, final int timeLeft) {
        if (check(reciepe.type, robotCount, currentMaterial, maxCost, timeLeft)
                && (reciepe.type == MaterialEnum.GEODE || robotCount.get(reciepe.type) < maxCost.get(reciepe.type))
                && reciepe.isPossible(currentMaterial) && !reciepe.isWaiting(currentMaterial, robotCount)) {

            // System.out.println(reciepe.type);
            Material newRobotCount = new Material(robotCount);
            newRobotCount.add(reciepe.created());
            Material newCurrentMaterial = new Material(currentMaterial);
            newCurrentMaterial.add(robotCount);
            newCurrentMaterial.remove(reciepe.cost);
            states.add(new State(newRobotCount, newCurrentMaterial));
        }
    }

    boolean check(final MaterialEnum type, final Material robotCount, final Material currentMaterial,
            final Material maxCost, final int timeLeft) {
        return !(type != MaterialEnum.GEODE
                && robotCount.get(type) * timeLeft + currentMaterial.get(type) >= timeLeft * maxCost.get(type));
    }

    @Override
    public Object getAnswer1() {
        int solution = 0;
        int maxTime = 24;
        int index = 1;
        for (Blueprint blueprint : blueprints) {
            int max = getMaxGeode(blueprint, maxTime);
            solution += getQualityLevel(index, max);
            index++;
        }
        return solution;
    }

    @Override
    public Object getAnswer2() {
        int solution = 1;
        int maxTime = 32;
        int index = 1;
        for (Blueprint blueprint : blueprints) {
            int max = getMaxGeode(blueprint, maxTime);
            solution *= max;
            index++;
            if (index > 3) {
                break;
            }
        }
        return solution;
    }

    public static void main(final String[] args) throws IOException {
        NotEnoughMinerals notEnoughMinerals = new NotEnoughMinerals("day19/input");
        // System.out.println("Answer1: " + notEnoughMinerals.getAnswer1());
        System.out.println("Answer2: " + notEnoughMinerals.getAnswer2());
    }

}
