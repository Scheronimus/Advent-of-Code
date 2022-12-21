package day21;

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

public class MonkeyMath extends Puzzle {

    List<Monkey> monkeys = new ArrayList<>();

    protected MonkeyMath(final String input) throws IOException {
        super(input);

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {

            Pattern reciepePattern = Pattern.compile("(\\w+): (?:(\\d+)|(?:(\\w+) ([\\/*\\+\\-]) (\\w+)))");

            String line;

            while ((line = br.readLine()) != null) {
                Matcher reciepeMatcher = reciepePattern.matcher(line);
                while (reciepeMatcher.find()) {
                    String id = reciepeMatcher.group(1);
                    String value = reciepeMatcher.group(2);
                    String idLeft = reciepeMatcher.group(3);
                    String operator = reciepeMatcher.group(4);
                    String idRight = reciepeMatcher.group(5);

                    Monkey m;
                    if (value != null) {
                        m = new Monkey(id, Long.parseLong(value));
                    } else {
                        IOperator op;
                        switch (operator) {
                            case "+": {
                                op = new OperatorPlus();
                                break;
                            }
                            case "-": {
                                op = new OperatorMinus();
                                break;
                            }
                            case "*": {
                                op = new OperatorMultiply();
                                break;
                            }
                            case "/": {
                                op = new OperatorDivide();
                                break;
                            }
                            default:
                                throw new IllegalArgumentException("Unexpected value: " + operator);
                        }

                        m = new Monkey(id, new Operation(idLeft, idRight, op));
                    }
                    monkeys.add(m);
                }
            }
        }
    }

    @Override
    public Object getAnswer1() {
        // printMonkey();

        Monkey root = Monkey.findMonkeyById("root", monkeys);
        // System.out.println(root);
        long res = root.computeValue(monkeys);
        return res;
    }

    private void printMonkey() {
        for (Monkey monkey : monkeys) {
            System.out.println(monkey);
        }
    }


    @Override
    public Object getAnswer2() {
        Long res = 0L;
        boolean notFound = true;
        // printMonkey();
        while (notFound) {
            List<Monkey> clone = clone(monkeys);

            Monkey root = Monkey.findMonkeyById("root", clone);
            Monkey humn = Monkey.findMonkeyById("humn", clone);
            humn.value = res;
            // System.out.println(root);
            Monkey left = Monkey.findMonkeyById(root.operation.idLeft, clone);
            Monkey right = Monkey.findMonkeyById(root.operation.idRight, clone);

            if (left.computeValue(clone) == right.computeValue(clone)) {
                notFound = false;
            } else {
                res++;
            }
            clone.clear();

        }
        return res;
    }

    List<Monkey> clone(final List<Monkey> original) {
        List<Monkey> clone = new ArrayList<>();
        for (Monkey monkey : original) {
            clone.add(new Monkey(monkey));
        }
        return clone;

    }

    public static void main(final String[] args) throws IOException {
        MonkeyMath monkeyMath = new MonkeyMath("day21/input");
        System.out.println("Answer1: " + monkeyMath.getAnswer1());
        System.out.println("Answer2: " + monkeyMath.getAnswer2());
    }
}
