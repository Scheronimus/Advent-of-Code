package year2022.day05;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import helper.Puzzle;

public class SupplyStacks extends Puzzle {

    List<RearrangementStep> rearrangementProcedure = new ArrayList<>();
    List<CrateStack> crateStacks = new ArrayList<>();

    public SupplyStacks(final String input) throws IOException {
        super(input);
        Pattern stepPattern = Pattern.compile("move (\\d+) from (\\d) to (\\d)");
        Pattern cratePattern = Pattern.compile("\\[(\\w)\\]");

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;


            while ((line = br.readLine()) != null) {

                Matcher crateMatcher = cratePattern.matcher(line);
                while (crateMatcher.find()) {
                    int column = crateMatcher.start() / 4;
                    while (crateStacks.size() <= column) {
                        crateStacks.add(new CrateStack());
                    }
                    crateStacks.get(column).crateslist.addFirst(crateMatcher.group(1));
                }


                Matcher matcher = stepPattern.matcher(line);

                if (matcher.find()) {
                    int quantity = Integer.parseInt(matcher.group(1));
                    int start = Integer.parseInt(matcher.group(2));
                    int end = Integer.parseInt(matcher.group(3));

                    RearrangementStep step = new RearrangementStep(quantity, start, end);
                    rearrangementProcedure.add(step);
                }
            }
        }
    }

    public void print() {
        for (CrateStack stack : crateStacks) {
            System.out.println(stack.crateslist.toString());
        }
    }

    public void executeStep_CrateMover_9000(final List<CrateStack> crateStacks, final RearrangementStep step) {
        for (int i = 0; i < step.quantity; i++) {
            String last = crateStacks.get(step.start - 1).crateslist.removeLast();
            crateStacks.get(step.end - 1).crateslist.addLast(last);
        }
    }

    public void executeStep_CrateMover_9001(final List<CrateStack> crateStacks, final RearrangementStep step) {
        CrateStack tempStack = new CrateStack();
        for (int i = 0; i < step.quantity; i++) {
            String last = crateStacks.get(step.start - 1).crateslist.removeLast();
            tempStack.crateslist.addFirst(last);
        }
        crateStacks.get(step.end - 1).crateslist.addAll(tempStack.crateslist);
    }

    public void executeProcedure_CrateMover_9000(final List<CrateStack> crateStacks,
            final List<RearrangementStep> rearrangementProcedure) {
        for (RearrangementStep step : rearrangementProcedure) {
            executeStep_CrateMover_9000(crateStacks, step);
        }
    }

    public void executeProcedure_CrateMover_9001(final List<CrateStack> crateStacks,
            final List<RearrangementStep> rearrangementProcedure) {
        for (RearrangementStep step : rearrangementProcedure) {
            executeStep_CrateMover_9001(crateStacks, step);
        }
    }

    public String getLastCratesOfEachStacks(final List<CrateStack> crateStacks) {

        StringBuilder builder = new StringBuilder();
        for (CrateStack stack : crateStacks) {
            builder.append(stack.crateslist.getLast());
        }
        return builder.toString();
    }

    @Override
    public Object getAnswer1() {
        executeProcedure_CrateMover_9000(crateStacks, rearrangementProcedure);
        return getLastCratesOfEachStacks(crateStacks);
    }

    @Override
    public Object getAnswer2() {
        executeProcedure_CrateMover_9001(crateStacks, rearrangementProcedure);
        return getLastCratesOfEachStacks(crateStacks);
    }

    public static void main(final String[] args) throws IOException {
        SupplyStacks supplyStacks = new SupplyStacks("year2022/day05/input");
        System.out.println("Answer1: " + supplyStacks.getAnswer1());
        supplyStacks = new SupplyStacks("day05/input");
        System.out.println("Answer2: " + supplyStacks.getAnswer2());
    }
}
