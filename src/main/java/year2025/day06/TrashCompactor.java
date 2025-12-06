package year2025.day06;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import helper.Puzzle;

public class TrashCompactor extends Puzzle {

    List<String[]> list = new ArrayList<String[]>();
    String[] operators;
    List<String> fullLines;

    public TrashCompactor(String input) throws IOException {
        super(input);
        parse(getLines());
    }

    private void parse(List<String> lines) {

        fullLines = lines;
        for (String line : lines) {
            // System.out.println(line);
            line = line.trim();
            String[] splittedLine = line.split(" +");

            Arrays.toString(splittedLine);
            // System.out.println(Arrays.toString(splittedLine));
            if (line.contains("+") || line.contains("*")) {
                operators = splittedLine;
            } else {
                list.add(splittedLine);
            }

        }
    }

    @Override
    public Object getAnswer1() {


        int l = list.get(0).length;
        long res = 0;
        for (int i = 0; i < l; i++) {

            String op = operators[i];
            // System.out.println(op);
            long subRes = 0;
            if (op.equals("+")) {
                subRes = 0;
                for (int k = 0; k < list.size(); k++) {
                    subRes += Long.parseLong(list.get(k)[i]);
                }


            } else if (op.equals("*")) {
                subRes = 1;
                for (int k = 0; k < list.size(); k++) {
                    subRes *= Long.parseLong(list.get(k)[i]);
                }
            }
            res += subRes;
        }

        return res;
    }

    @Override
    public Object getAnswer2() {
        String opreatorLine = fullLines.get(fullLines.size() - 1);
        fullLines.remove(fullLines.size() - 1);
        System.out.println(opreatorLine);
        System.out.println(fullLines);
        long finalResult = 0;
        char currentOperator = 'X';
        long currentResult = 0;
        for (int i = 0; i < opreatorLine.length(); i++) {


            if (opreatorLine.charAt(i) != ' ') {
                finalResult += currentResult;
                System.out.println("- currentResult: " + currentResult);
                System.out.println("- finalResult set to " + finalResult);
                currentOperator = opreatorLine.charAt(i);
                if (currentOperator == '+') {
                    System.out.println("START '+' at " + i);
                    currentResult = 0;
                } else if (currentOperator == '*') {
                    System.out.println("START '*' at " + i);
                    currentResult = 1;
                }
                System.out.println("currentResult set to " + currentResult);
            }

            String s = "";
            for (int k = 0; k < fullLines.size(); k++) {
                s += fullLines.get(k).charAt(i);

            }
            s = s.trim();

            if (!s.isEmpty()) {
                // System.out.println("columnValue: " + s + " at " + i);
                long columsValue = Long.parseLong(s);
                // System.out.println("columnValue: " + columsValue + " at " + i);
                // System.out.println("currentOperator: " + currentOperator);
                // System.out.println("currentResult: " + currentResult);
                if (currentOperator == '+') {

                    currentResult += columsValue;
                } else if (currentOperator == '*') {

                    currentResult *= columsValue;
                }
            }
            // System.out.println("currentResult: " + currentResult);

            if (i == opreatorLine.length() - 1) {
                finalResult += currentResult;
                System.out.println("- currentResult: " + currentResult);
                System.out.println("- finalResult set to " + finalResult);
            }
        }
        return finalResult;
    }

    public static void main(String[] args) throws IOException {
        TrashCompactor puzzle = new TrashCompactor("year2025/day06/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }
}
