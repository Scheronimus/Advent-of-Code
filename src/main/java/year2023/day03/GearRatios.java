package year2023.day03;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import helper.Puzzle;


public class GearRatios extends Puzzle {
    List<String> engineMap = new ArrayList<>();
    List<PartNumber> partNumber = new ArrayList<>();

    public GearRatios(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                engineMap.add(line);
            }
        }

        partNumber = findPartNumbers(engineMap);
    }

    private List<PartNumber> findPartNumbers(List<String> engineMap) {

        List<PartNumber> partNumbers = new ArrayList<>();
        int lineNb = 0;
        for (String line : engineMap) {

            String temp = "";
            int start = -1;
            int charindex = 0;
            for (char c : line.toCharArray()) {
                if (Character.isDigit(c)) {
                    temp += c;
                    if (start == -1) {
                        start = charindex;
                    }
                } else if (!temp.isEmpty()) {

                    partNumbers.add(new PartNumber(Integer.parseInt(temp), lineNb, start, (start + temp.length() - 1)));
                    temp = "";
                    start = -1;
                }
                charindex++;
            }
            lineNb++;
        }
        return partNumbers;
    }

    @Override
    public Object getAnswer1() {
        int res = 0;
        for (PartNumber number : partNumber) {
            if (number.isValid(engineMap)) {
                res += number.value;
            }
        }
        return res;
    }


    @Override
    public Object getAnswer2() {

        List<Gear> gearList = getGearList(engineMap);
        int res = 0;
        for (Gear gear : gearList) {
            if (gear.partNumber.size() == 2) {
                res += gear.partNumber.get(0).value * gear.partNumber.get(1).value;
            }
        }

        return res;
    }

    private List<Gear> getGearList(List<String> engineMap) {
        List<Gear> gearList = new ArrayList<>();

        for (PartNumber number : partNumber) {
            number.isGear(engineMap, gearList);
        }
        return gearList;
    }

    public static void main(final String[] args) throws IOException {
        GearRatios gearRatios = new GearRatios("year2023/day03/input");
        System.out.println("Answer 1: " + gearRatios.getAnswer1());
        System.out.println("Answer 2: " + gearRatios.getAnswer2());
    }


}
