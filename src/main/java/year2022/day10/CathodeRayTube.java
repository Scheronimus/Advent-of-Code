package year2022.day10;

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

public class CathodeRayTube extends Puzzle {

    List<IInstruction> instructionList = new ArrayList<>();
    CPU cpu = new CPU();

    protected CathodeRayTube(final String input) throws IOException {
        super(input);
        Pattern instructionPattern = Pattern.compile("(\\w+) ?([-\\d]+)?");
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                Matcher instructionMatcher = instructionPattern.matcher(line);
                if (instructionMatcher.find()) {
                    String inst = instructionMatcher.group(1);

                    if (inst.equals("noop")) {
                        instructionList.add(new Noop());
                    }
                    if (inst.equals("addx")) {
                        instructionList.add(new Addx(Integer.parseInt(instructionMatcher.group(2))));
                    }

                }
            }
        }
        runInstruction();
    }

    private void runInstruction() {
        for (IInstruction inst : instructionList) {
            inst.modifyReigisterX(cpu);
        }
    }

    @Override
    public Object getAnswer1() {
        int result = 0;
        result += calculateSignalStrength(20);
        result += calculateSignalStrength(60);
        result += calculateSignalStrength(100);
        result += calculateSignalStrength(140);
        result += calculateSignalStrength(180);
        result += calculateSignalStrength(220);
        return result;
    }

    private int calculateSignalStrength(final int cycle) {
        return cpu.historyOfX.get(cycle - 1) * cycle;
    }


    private String printCRT() {
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 40; i++) {
                Integer x = cpu.historyOfX.get(i + 40 * j);
                if (i + 1 >= x && i + 1 < x + 3) {
                    result.append("#");
                } else {
                    result.append(".");
                }
            }
            result.append("\r\n");
        }
        return result.toString().trim();
    }

    @Override
    public Object getAnswer2() {
        return printCRT();
    }

    public static void main(final String[] args) throws IOException {
        CathodeRayTube cathodeRayTube = new CathodeRayTube("year2022/day10/input");
        System.out.println("Answer1: " + cathodeRayTube.getAnswer1());
        System.out.println("Answer2: \n" + cathodeRayTube.getAnswer2());
    }

}
