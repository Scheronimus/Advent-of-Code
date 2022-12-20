package day20;

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

    public record Value(int val) {

        @Override
        public String toString() {
            return "" + val;
        }

    }


    protected GrovePositioningSystem(String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                encryptedFile.add(new Value(Integer.parseInt(line.trim())));
            }
        }
    }

    @Override
    public Object getAnswer1() {

        System.out.println(encryptedFile);
        List<Value> workingCopy = decrypt(encryptedFile);


        Value zero = null;
        for (Value val : workingCopy) {
            if (val.val == 0) {
                zero = val;
            }
        }
        int index0 = workingCopy.indexOf(zero);
        Integer val1 = workingCopy.get((index0 + 1000) % workingCopy.size()).val;
        Integer val2 = workingCopy.get((index0 + 2000) % workingCopy.size()).val;
        Integer val3 = workingCopy.get((index0 + 3000) % workingCopy.size()).val;
        return val1 + val2 + val3;
    }

    public List<Value> decrypt(List<Value> encryptedFile) {
        List<Value> workingCopy = new ArrayList<>(encryptedFile);

        for (Value val : encryptedFile) {
            processValue(val, workingCopy);

        }
        return workingCopy;
    }


    Value MAX = new Value(Integer.MAX_VALUE);

    public List<Value> processValue(Value value, List<Value> workingCopy) {
        int index = workingCopy.indexOf(value);

        if (value.val >= 0) {
            workingCopy.set(index, MAX);
            workingCopy.add((index + value.val + 1) % encryptedFile.size(), value);
            workingCopy.remove(MAX);


        } else {
            workingCopy.set(index, MAX);
            Collections.reverse(workingCopy);
            int absval = Math.abs(value.val);
            int reverseIndex = workingCopy.size() - index - 1;
            int newIndex = (reverseIndex + absval + 1) % workingCopy.size();

            workingCopy.add(newIndex, value);
            Collections.reverse(workingCopy);
            workingCopy.remove(MAX);
        }
        System.out.println(workingCopy);
        return workingCopy;

    }

    @Override
    public Object getAnswer2() {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(final String[] args) throws IOException {
        GrovePositioningSystem grovePositioningSystem = new GrovePositioningSystem("day20/input");
        System.out.println("Answer1: " + grovePositioningSystem.getAnswer1());
        System.out.println("Answer2: " + grovePositioningSystem.getAnswer2());
    }
}
