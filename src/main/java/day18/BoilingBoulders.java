package day18;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import helper.Puzzle;

public class BoilingBoulders extends Puzzle {

    protected BoilingBoulders(String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
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
        BoilingBoulders boilingBoulders = new BoilingBoulders("day18/input");
        System.out.println("Answer1: " + boilingBoulders.getAnswer1());
        System.out.println("Answer2: " + boilingBoulders.getAnswer2());
    }
}
