package year2022.day11;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helper.Puzzle;
import year2022.day11.operation.Addition;
import year2022.day11.operation.Multiplication;
import year2022.day11.operation.Square;

public class MonkeyInTheMiddle extends Puzzle {

    List<Monkey> monkeys = new ArrayList<>();

    protected MonkeyInTheMiddle(final String input) throws IOException {
        super(input);

        Pattern monkeyPattern = Pattern.compile("Monkey (\\d)");
        Pattern startingItemsPattern = Pattern.compile("Starting items: (.*)");
        Pattern operationPattern = Pattern.compile("Operation: new = old ([\\+\\*]) ([\\d\\w]+)");
        Pattern testPattern = Pattern.compile("Test: divisible by (\\d+)");
        Pattern ifPattern = Pattern.compile("If (true|false): throw to monkey (\\d)");


        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;
            Monkey currentMonkey = null;
            while ((line = br.readLine()) != null) {

                Matcher monkeyMatcher = monkeyPattern.matcher(line);
                if (monkeyMatcher.find()) {
                    currentMonkey = new Monkey(Integer.parseInt(monkeyMatcher.group(1)), monkeys);
                    monkeys.add(currentMonkey);
                }

                Matcher startingItemsMatcher = startingItemsPattern.matcher(line);
                if (startingItemsMatcher.find()) {
                    List<Item> startingItems = new ArrayList<>();
                    String[] splitted = startingItemsMatcher.group(1).split(",");
                    for (String split : splitted) {
                        startingItems.add(new Item(Integer.parseInt(split.trim())));
                    }
                    currentMonkey.setItems(startingItems);
                }

                Matcher operationMatcher = operationPattern.matcher(line);
                if (operationMatcher.find()) {
                    if (operationMatcher.group(1).equals("+")) {
                        currentMonkey.setOperation(new Addition(Integer.parseInt(operationMatcher.group(2))));
                    }
                    if (operationMatcher.group(1).equals("*")) {
                        if (operationMatcher.group(2).equals("old")) {
                            currentMonkey.setOperation(new Square());
                        } else {
                            currentMonkey.setOperation(new Multiplication(Integer.parseInt(operationMatcher.group(2))));
                        }
                    }
                }


                Matcher testMatcher = testPattern.matcher(line);
                if (testMatcher.find()) {
                    currentMonkey.setTest(new Test(Integer.parseInt(testMatcher.group(1))));
                }

                Matcher ifMatcher = ifPattern.matcher(line);
                if (ifMatcher.find()) {
                    if (ifMatcher.group(1).equals("true")) {
                        currentMonkey.setMonkeyIfTrue(Integer.parseInt(ifMatcher.group(2)));
                    } else {
                        currentMonkey.setMonkeyIfFalse(Integer.parseInt(ifMatcher.group(2)));
                    }
                }
            }
        }
    }

    private void runRound(final boolean reduceWorryLevel) {
        for (Monkey monkey : monkeys) {
            monkey.inspectAllItems(reduceWorryLevel);
        }
    }

    private void runRounds(final int roundNumber, final boolean reduceWorryLevel) {
        for (int i = 0; i < roundNumber; i++) {
            runRound(reduceWorryLevel);
        }
    }

    @Override
    public Object getAnswer1() {
        runRounds(20, true);
        Collections.sort(monkeys, (left, right) -> Long.compare(right.inspected, left.inspected));
        return monkeys.get(0).inspected * monkeys.get(1).inspected;
    }

    @Override
    public Object getAnswer2() {
        runRounds(10000, false);
        Collections.sort(monkeys, (left, right) -> Long.compare(right.inspected, left.inspected));
        return monkeys.get(0).inspected * monkeys.get(1).inspected;
    }

    public static void main(final String[] args) throws IOException {
        MonkeyInTheMiddle monkeyInTheMiddle = new MonkeyInTheMiddle("year2022/day11/input");
        System.out.println("Answer1: " + monkeyInTheMiddle.getAnswer1());
        monkeyInTheMiddle = new MonkeyInTheMiddle("year2022/day11/input");
        System.out.println("Answer2: " + monkeyInTheMiddle.getAnswer2());
    }

}
