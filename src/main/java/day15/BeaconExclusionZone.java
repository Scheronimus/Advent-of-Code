package day15;

import java.io.IOException;

import helper.Puzzle;

public class BeaconExclusionZone extends Puzzle {

    protected BeaconExclusionZone(final String input) throws IOException {
        super(input);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Object getAnswer1() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getAnswer2() {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(final String[] args) throws IOException {
        BeaconExclusionZone beaconExclusionZone = new BeaconExclusionZone("day15/input");
        System.out.println("Answer1: " + beaconExclusionZone.getAnswer1());

        System.out.println("Answer2: " + beaconExclusionZone.getAnswer2());
    }

}
