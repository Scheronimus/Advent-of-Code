package day13;

import java.io.IOException;

import helper.Puzzle;

public class DistressSignal extends Puzzle {

    protected DistressSignal(final String input) throws IOException {
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
        DistressSignal distressSignal = new DistressSignal("day13/input");
        System.out.println("Answer1: " + distressSignal.getAnswer1());
        System.out.println("Answer2: " + distressSignal.getAnswer2());
    }

}
