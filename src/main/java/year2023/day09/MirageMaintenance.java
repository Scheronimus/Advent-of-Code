package year2023.day09;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import helper.ListUtils;
import helper.Puzzle;


public class MirageMaintenance extends Puzzle {
    List<List<Integer>> sequences = new ArrayList<>();

    public MirageMaintenance(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                sequences.add(ListUtils.stringToList(line));
            }
        }
    }

    public List<Integer> derivate(List<Integer> sequence) {
        List<Integer> derivate = new ArrayList<>();
        for (int i = 1; i < sequence.size(); i++) {
            derivate.add(sequence.get(i) - sequence.get(i - 1));
        }
        return derivate;
    }

    @Override
    public Object getAnswer1() {
        int res = 0;
        for (List<Integer> sequence : sequences) {

            List<List<Integer>> derivates = findAllDerivate(sequence);
            int val = calculateNextValue(derivates);
            res += val;
        }
        return res;
    }

    private int calculateNextValue(List<List<Integer>> derivates) {

        int index = derivates.get(0).size() - 1;
        int val = 0;
        for (List<Integer> derivate : derivates) {
            val += derivate.get(index);
            index--;
        }
        return val;
    }

    private int calculateFirstValue(List<List<Integer>> derivates) {

        int index = 0;
        int val = 0;
        for (List<Integer> derivate : derivates) {
            val += Math.pow(-1, index) * derivate.get(0);
            index++;
        }
        return val;
    }

    private List<List<Integer>> findAllDerivate(List<Integer> sequence) {

        List<List<Integer>> derivates = new ArrayList<>();
        derivates.add(sequence);
        boolean stop = false;

        List<Integer> temp = sequence;
        while (!stop) {
            List<Integer> derivate = derivate(temp);
            derivates.add(derivate);
            if (isListOfZero(derivate)) {
                stop = true;
            }
            temp = derivate;
        }
        return derivates;
    }


    @Override
    public Object getAnswer2() {
        int res = 0;
        for (List<Integer> sequence : sequences) {

            List<List<Integer>> derivates = findAllDerivate(sequence);
            int val = calculateFirstValue(derivates);
            res += val;

        }
        return res;
    }

    boolean isListOfZero(List<Integer> list) {
        for (Integer value : list) {
            if (value != 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(final String[] args) throws IOException {
        MirageMaintenance puzzle = new MirageMaintenance("year2023/day09/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }
}
