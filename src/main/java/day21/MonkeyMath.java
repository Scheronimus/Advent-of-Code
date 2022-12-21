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

import day21.operator.IOperator;
import day21.operator.OperatorDivide;
import day21.operator.OperatorMinus;
import day21.operator.OperatorMultiply;
import day21.operator.OperatorPlus;
import helper.Polynomial;
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
                        m = new Monkey(id, new Polynomial(Integer.parseInt(value), 0));
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
        Monkey root = Monkey.findMonkeyById("root", monkeys);
        Polynomial res = root.computeValue(monkeys);
        return (long)res.getCoef(0);
    }

    private void printMonkeys() {
        for (Monkey monkey : monkeys) {
            System.out.println(monkey);
        }
    }


    @Override
    public Object getAnswer2() {

        Monkey root = Monkey.findMonkeyById("root", monkeys);
        Monkey humn = Monkey.findMonkeyById("humn", monkeys);

        humn.value = new Polynomial(1, 1);

        Monkey left = Monkey.findMonkeyById(root.operation.idLeft, monkeys);
        Monkey right = Monkey.findMonkeyById(root.operation.idRight, monkeys);
        Polynomial resL = left.computeValue(monkeys);
        Polynomial resR = right.computeValue(monkeys);

        Polynomial equalZero = resL.minus(resR);

        return (long)(-equalZero.getCoef(0) / equalZero.getCoef(1));
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
        monkeyMath = new MonkeyMath("day21/input");
        System.out.println("Answer2: " + monkeyMath.getAnswer2());
    }
}
