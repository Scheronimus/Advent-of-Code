package day19;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import testhelper.PuzzleUnitTest;

public class NotEnoughMineralsTest extends PuzzleUnitTest {

    public NotEnoughMineralsTest() throws IOException {
        super(new NotEnoughMinerals("day19/test_input"), 33, 56 * 62);
    }

    @Test
    public void testPocessState() {
        NotEnoughMinerals p = ((NotEnoughMinerals)puzzle);


        Material robotCount = new Material(1, 4, 2, 2);
        Material currentMaterial = new Material(3, 29, 2, 3);
        int time = 21;
        State state = new State(robotCount, currentMaterial, time);
        Blueprint blueprint = p.blueprints.get(0);
        Material maxCost = blueprint.getMaximals();
        int maxTime = 24;
        List<State> newStates = new ArrayList<>();
        p.pocessState(state, blueprint, maxCost, maxTime, newStates);
        System.out.println("yop");

    }

    @Test
    public void testPocessState2() {
        NotEnoughMinerals p = ((NotEnoughMinerals)puzzle);


        Material robotCount = new Material(2, 7, 2, 0);
        Material currentMaterial = new Material(3, 13, 3, 0);
        int time = 21;
        State state = new State(robotCount, currentMaterial, time);
        Blueprint blueprint = p.blueprints.get(0);
        Material maxCost = blueprint.getMaximals();
        int maxTime = 24;
        List<State> newStates = new ArrayList<>();
        p.pocessState(state, blueprint, maxCost, maxTime, newStates);
        System.out.println("yop");

    }

}
