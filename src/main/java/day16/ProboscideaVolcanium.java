package day16;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import helper.Puzzle;

public class ProboscideaVolcanium extends Puzzle {

    List<Valve> valves = new ArrayList<>();

    protected ProboscideaVolcanium(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {

            }
        }
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
        ProboscideaVolcanium proboscideaVolcanium = new ProboscideaVolcanium("day16/input");
        System.out.println("Answer1: " + proboscideaVolcanium.getAnswer1());
        System.out.println("Answer2: " + proboscideaVolcanium.getAnswer2());
    }

}
