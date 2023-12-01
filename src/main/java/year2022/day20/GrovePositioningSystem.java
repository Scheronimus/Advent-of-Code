package year2022.day20;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import helper.Puzzle;


public class GrovePositioningSystem extends Puzzle {
    List<Value> encryptedFile = new ArrayList<>();
    List<Value> encryptedFile2 = new ArrayList<>();

    protected GrovePositioningSystem(String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                encryptedFile.add(new Value(Integer.parseInt(line.trim())));
                encryptedFile2.add(new Value(Integer.parseInt(line.trim()) * 811589153L));
            }
        }
    }

    @Override
    public Object getAnswer1() {
        List<Value> workingCopy = decrypt(encryptedFile);
        Value zero = null;
        for (Value val : workingCopy) {
            if (val.val == 0) {
                zero = val;
            }
        }
        int index0 = workingCopy.indexOf(zero);
        long val1 = workingCopy.get((index0 + 1000) % workingCopy.size()).val;
        long val2 = workingCopy.get((index0 + 2000) % workingCopy.size()).val;
        long val3 = workingCopy.get((index0 + 3000) % workingCopy.size()).val;
        return (int)(val1 + val2 + val3);
    }

    public List<Value> decrypt(List<Value> encryptedFile) {
        List<Value> workingCopy = new ArrayList<>(encryptedFile);


        return decrypt(encryptedFile, workingCopy);
    }

    public List<Value> decrypt(List<Value> encryptedFile, List<Value> workingCopy) {

        for (Value val : encryptedFile) {
            processValue(val, workingCopy);
        }
        return workingCopy;
    }


    Value MAX = new Value(Integer.MAX_VALUE);

    public List<Value> processValue(Value value, List<Value> workingCopy) {
        int index = workingCopy.indexOf(value);

        if (value.val >= 0) {
            workingCopy.remove(value);
            long b2 = (index + value.val) % (encryptedFile.size() - 1);
            if (b2 == 0) {
                b2 = encryptedFile.size() - 1;
            }
            workingCopy.add((int)b2, value);
        } else {

            Collections.reverse(workingCopy);
            long absval = Math.abs(value.val);
            int reverseIndex = workingCopy.size() - index - 1;
            workingCopy.remove(value);

            long b2 = (reverseIndex + absval) % (encryptedFile.size() - 1);
            if (b2 == 0) {
                b2 = encryptedFile.size() - 1;
            }
            workingCopy.add((int)b2, value);
            Collections.reverse(workingCopy);
        }
        return workingCopy;
    }

    @Override
    public Object getAnswer2() {
        List<Value> workingCopy = decrypt(encryptedFile2);
        for (int i = 0; i < 9; i++) {
            workingCopy = decrypt(encryptedFile2, workingCopy);
        }

        Value zero = null;
        for (Value val : workingCopy) {
            if (val.val == 0) {
                zero = val;
            }
        }
        int index0 = workingCopy.indexOf(zero);
        long val1 = workingCopy.get((index0 + 1000) % workingCopy.size()).val;
        long val2 = workingCopy.get((index0 + 2000) % workingCopy.size()).val;
        long val3 = workingCopy.get((index0 + 3000) % workingCopy.size()).val;
        return val1 + val2 + val3;
    }

    public static void main(final String[] args) throws IOException {
        GrovePositioningSystem grovePositioningSystem = new GrovePositioningSystem("year2022/day20/input");
        System.out.println("Answer1: " + grovePositioningSystem.getAnswer1());
        System.out.println("Answer2: " + grovePositioningSystem.getAnswer2());
    }
}
