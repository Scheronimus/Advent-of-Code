package template;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import helper.Puzzle;


public class Template extends Puzzle {

    public Template(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                // TODO
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
        Template template = new Template("year2023/dayXX/input");
        System.out.println("Answer 1: " + template.getAnswer1());
        System.out.println("Answer 2: " + template.getAnswer2());
    }


}
