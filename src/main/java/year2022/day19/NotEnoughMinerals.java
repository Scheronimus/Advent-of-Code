package year2022.day19;

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


    private int getMaxGeodeNew(final Blueprint blueprint, final int maxTime) {
        int maxGeode = 0;
        Best currentBest = new Best(0);
        Material maxCost = blueprint.getMaximals();

        List<State> states = new ArrayList<>();
        states.add(new State(new Material(1, 0, 0, 0), new Material(0, 0, 0, 0), 0));

        boolean ongoing = true;
        while (ongoing) {

            boolean allFinished = true;

            List<State> newStates = new ArrayList<>();
            for (State state : states) {
                if (state.time != maxTime) {
                    pocessState(state, blueprint, maxCost, maxTime, newStates, currentBest);
                    allFinished = false;
                }

            }
            states.addAll(newStates);
            ongoing = !allFinished;
        }

        for (State state : states) {
            if (state.time >= maxTime) {
                maxGeode = Math.max(maxGeode, state.currentMaterial.geode);
            }
        }

        System.out.println("max: " + maxGeode);
        states.clear();
        return maxGeode;
    }


    void pocessState(State state, final Blueprint blueprint, Material maxCost, final int maxTime, List<State> newStates,
            Best currentBest) {
        currentBest.currentBest = Math.max(currentBest.currentBest,
                state.currentMaterial.geode + state.robotCount.geode * (maxTime - state.time));


        if ((maxGeodePossibleInBestCase(state.currentMaterial.geode, state.robotCount.geode,
                maxTime - state.time) > currentBest.currentBest) && state.time < maxTime - 1) {
            if (state.time < maxTime - 2) {
                if (state.time < maxTime - 3) {
                    createClayRobot(state, maxCost, blueprint, newStates, maxTime);
                }
                createOreRobot(state, maxCost, blueprint, newStates, maxTime);
                createObsidianRobot(state, maxCost, blueprint, newStates, maxTime);
            }
            createGeodeRobot(state, maxCost, blueprint, newStates, maxTime);
        }
        idle(state, maxTime);
    }


    private void idle(State state, final int maxTime) {
        int idleTime = (maxTime - state.time);
        state.currentMaterial.addxtime(state.robotCount, idleTime);
        state.time = maxTime;
    }


    private void createOreRobot(State state, Material maxCost, final Blueprint blueprint, List<State> states,
            int maxTime) {
        if (state.currentMaterial.ore <= (maxCost.ore) * (maxTime - state.time) && state.robotCount.ore > 0
                && state.robotCount.ore < maxCost.ore) {
            Material newRobotCount = new Material(state.robotCount);
            Material newCurrentMaterial = new Material(state.currentMaterial);
            int newTime = state.time;


            if ((state.currentMaterial.ore - blueprint.oreRobot.cost.ore) >= 0) {
                newCurrentMaterial.add(newRobotCount);
                newCurrentMaterial.remove(blueprint.oreRobot.cost);
                newTime++;

            } else {
                int missingOre = Math.abs(state.currentMaterial.ore - blueprint.oreRobot.cost.ore);
                int numberOfCycle = missingOre / state.robotCount.ore;
                if ((missingOre % state.robotCount.ore) > 0) {
                    numberOfCycle++;
                }


                int timeRemaining = maxTime - newTime;
                if (numberOfCycle + 1 >= timeRemaining - 2) {
                    return; // this is the idling case.
                }
                newTime += numberOfCycle + 1;

                newCurrentMaterial.addxtime(newRobotCount, numberOfCycle + 1);
                newCurrentMaterial.remove(blueprint.oreRobot.cost);
            }
            newRobotCount.add(blueprint.oreRobot.created());
            State newState = new State(newRobotCount, newCurrentMaterial, newTime);
            states.add(newState);
        }
    }

    private void createClayRobot(State state, Material maxCost, final Blueprint blueprint, List<State> states,
            int maxTime) {
        if (state.robotCount.obsidian < maxCost.obsidian
                && state.currentMaterial.clay <= (maxCost.clay) * (maxTime - state.time) && state.robotCount.ore > 0
                && state.robotCount.clay < maxCost.clay) {
            Material newRobotCount = new Material(state.robotCount);
            Material newCurrentMaterial = new Material(state.currentMaterial);
            int newTime = state.time;

            if ((state.currentMaterial.ore - blueprint.clayRobot.cost.ore) >= 0) {
                newCurrentMaterial.add(newRobotCount);
                newCurrentMaterial.remove(blueprint.clayRobot.cost);
                newTime++;

            } else {
                int missingOre = Math.abs(state.currentMaterial.ore - blueprint.clayRobot.cost.ore);
                int numberOfCycle = missingOre / state.robotCount.ore;
                if ((missingOre % state.robotCount.ore) > 0) {
                    numberOfCycle++;
                }

                int timeRemaining = maxTime - newTime;
                if (numberOfCycle + 1 > timeRemaining - 3) {
                    return; // this is the idling case.
                }

                newTime += numberOfCycle + 1;
                newCurrentMaterial.addxtime(newRobotCount, numberOfCycle + 1);
                newCurrentMaterial.remove(blueprint.clayRobot.cost);
            }
            newRobotCount.add(blueprint.clayRobot.created());

            State newState = new State(newRobotCount, newCurrentMaterial, newTime);

            states.add(newState);
        }
    }


    private void createObsidianRobot(State state, Material maxCost, final Blueprint blueprint, List<State> states,
            int maxTime) {
        if (state.currentMaterial.obsidian <= (maxCost.obsidian) * (maxTime - state.time) && state.robotCount.clay > 0
                && state.robotCount.obsidian < maxCost.obsidian) {
            Material newRobotCount = new Material(state.robotCount);
            Material newCurrentMaterial = new Material(state.currentMaterial);
            int newTime = state.time;

            if ((state.currentMaterial.ore - blueprint.obsidianRobot.cost.ore) >= 0
                    && (state.currentMaterial.clay - blueprint.obsidianRobot.cost.clay) >= 0) {
                newCurrentMaterial.add(newRobotCount);
                newCurrentMaterial.remove(blueprint.obsidianRobot.cost);
                newTime++;

            } else {
                int missingOre = 0;
                if ((state.currentMaterial.ore - blueprint.obsidianRobot.cost.ore) < 0) {
                    missingOre = Math.abs(state.currentMaterial.ore - blueprint.obsidianRobot.cost.ore);
                }
                int numberOfCycleOre = missingOre / state.robotCount.ore;
                if ((missingOre % state.robotCount.ore) > 0) {
                    numberOfCycleOre++;
                }

                int missingClay = 0;
                if ((state.currentMaterial.clay - blueprint.obsidianRobot.cost.clay) < 0) {
                    missingClay = Math.abs(state.currentMaterial.clay - blueprint.obsidianRobot.cost.clay);
                }
                int numberOfCycleClay = missingClay / state.robotCount.clay;
                if ((missingClay % state.robotCount.clay) > 0) {
                    numberOfCycleClay++;
                }


                int numberOfCycle = Math.max(numberOfCycleOre, numberOfCycleClay);

                int timeRemaining = maxTime - newTime;
                if (numberOfCycle + 1 > timeRemaining - 2) {
                    return; // this is the idling case.
                }

                newTime += numberOfCycle + 1;
                newCurrentMaterial.addxtime(newRobotCount, numberOfCycle + 1);
                newCurrentMaterial.remove(blueprint.obsidianRobot.cost);


            }
            newRobotCount.add(blueprint.obsidianRobot.created());

            State newState = new State(newRobotCount, newCurrentMaterial, newTime);

            states.add(newState);
        }
    }

    private void createGeodeRobot(State state, Material maxCost, final Blueprint blueprint, List<State> states,
            int maxTime) {
        if (state.robotCount.obsidian > 0) {
            Material newRobotCount = new Material(state.robotCount);
            Material newCurrentMaterial = new Material(state.currentMaterial);
            int newTime = state.time;

            if ((state.currentMaterial.ore - blueprint.geodeRobot.cost.ore) >= 0
                    && (state.currentMaterial.obsidian - blueprint.geodeRobot.cost.obsidian) >= 0) {
                newCurrentMaterial.add(newRobotCount);
                newCurrentMaterial.remove(blueprint.geodeRobot.cost);
                newTime++;

            } else {
                int missingOre = 0;
                if ((state.currentMaterial.ore - blueprint.geodeRobot.cost.ore) < 0) {
                    missingOre = Math.abs(state.currentMaterial.ore - blueprint.geodeRobot.cost.ore);
                }

                int missingObsidian = 0;
                if ((state.currentMaterial.obsidian - blueprint.geodeRobot.cost.obsidian) < 0) {
                    missingObsidian = Math.abs(state.currentMaterial.obsidian - blueprint.geodeRobot.cost.obsidian);
                }

                int numberOfCycleOre = missingOre / state.robotCount.ore;
                if ((missingOre % state.robotCount.ore) > 0) {
                    numberOfCycleOre++;
                }

                int numberOfCycleOsidian = missingObsidian / state.robotCount.obsidian;
                if ((missingObsidian % state.robotCount.obsidian) > 0) {
                    numberOfCycleOsidian++;
                }

                int numberOfCycle = Math.max(numberOfCycleOre, numberOfCycleOsidian);

                int timeRemaining = maxTime - newTime;
                if (numberOfCycle + 1 > timeRemaining - 1) {
                    return; // this is the idling case.
                }

                newTime += numberOfCycle + 1;
                newCurrentMaterial.addxtime(newRobotCount, numberOfCycle + 1);
                newCurrentMaterial.remove(blueprint.geodeRobot.cost);


            }
            newRobotCount.add(blueprint.geodeRobot.created());

            State newState = new State(newRobotCount, newCurrentMaterial, newTime);
            states.add(newState);
        }
    }


    private int getQualityLevel(final int index, final int maxGeode) {
        return index * maxGeode;
    }


    @Override
    public Object getAnswer1() {
        int solution = 0;
        int maxTime = 24;
        int index = 1;
        for (Blueprint blueprint : blueprints) {
            int max = getMaxGeodeNew(blueprint, maxTime);
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
            int max = getMaxGeodeNew(blueprint, maxTime);
            solution *= max;
            index++;
            if (index > 3) {
                break;
            }
        }
        return solution;
    }

    public static void main(final String[] args) throws IOException {
        NotEnoughMinerals notEnoughMinerals = new NotEnoughMinerals("year2022/day19/input");
        System.out.println("Answer1: " + notEnoughMinerals.getAnswer1());
        System.out.println("Answer2: " + notEnoughMinerals.getAnswer2());
    }

    public int maxGeodePossibleInBestCase(int currentGeode, int currentGeodeRobot, int timeRemaining) {
        // Current geode + Arithmetic sum of geode Robot
        // e.g for input (1,2,3) : 1 + (2+3+4)
        return currentGeode + (timeRemaining + 1) * (2 * currentGeodeRobot + timeRemaining) / 2;
    }

}
