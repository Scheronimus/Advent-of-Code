package day22;

import java.io.IOException;

import day06.TuningTrouble;
import helper.Puzzle;

public class MonkeyMap extends Puzzle {

    protected MonkeyMap(String input) throws IOException {
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
        TuningTrouble tuningTrouble = new TuningTrouble("day22/input");
        System.out.println("Answer1: " + tuningTrouble.getAnswer1());
        System.out.println("Answer2: " + tuningTrouble.getAnswer2());
    }
}
