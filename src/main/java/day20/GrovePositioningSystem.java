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
    List<Integer> encryptedFile = new ArrayList<>();

    protected GrovePositioningSystem(String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                encryptedFile.add(Integer.parseInt(line.trim()));
            }
        }
    }

    @Override
    public Object getAnswer1() {
        List<Integer> workingCopy = decrypt();
        System.out.println(workingCopy);
        int index0 = workingCopy.indexOf(0);
        Integer val1 = workingCopy.get((index0 + 1000) % workingCopy.size());
        Integer val2 = workingCopy.get((index0 + 2000) % workingCopy.size());
        Integer val3 = workingCopy.get((index0 + 3000) % workingCopy.size());
        return val1 + val2 + val3;
    }

    private List<Integer> decrypt() {
        List<Integer> workingCopy = new ArrayList<>(encryptedFile);

        for (Integer val : encryptedFile) {
            int index = workingCopy.indexOf(val);

            if (val >= 0) {
                workingCopy.set(index, Integer.MAX_VALUE);

                workingCopy.add((index + val + 1) % encryptedFile.size(), val);
                workingCopy.remove(workingCopy.indexOf(Integer.MAX_VALUE));
            } else {
                workingCopy.set(index, Integer.MAX_VALUE);
                Collections.reverse(workingCopy);
                int absval = Math.abs(val);
                int reverseIndex = encryptedFile.size() - index - 1;
                int newIndex = (reverseIndex + absval + 1) % encryptedFile.size();

                workingCopy.add(newIndex, val);
                Collections.reverse(workingCopy);
                workingCopy.remove(workingCopy.indexOf(Integer.MAX_VALUE));
            }

        }
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
