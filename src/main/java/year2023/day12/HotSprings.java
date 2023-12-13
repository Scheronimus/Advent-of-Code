package year2023.day12;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import helper.ListUtils;
import helper.Puzzle;


public class HotSprings extends Puzzle {
    List<Record> records = new ArrayList<>();

    public HotSprings(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ");

                ListUtils.stringToList(split[1].replace(',', ' '));

                records.add(new Record(split[0], ListUtils.stringToList(split[1].replace(',', ' '))));


            }
        }

        // System.out.println(records);
        // Pattern p = records.get(0).buildPattern();
        // Matcher matcher = p.matcher(".#.#.###.......");
        // System.out.println(matcher.find());
        Set<String> val = records.get(0).findAllCommutation();
        // System.out.println(val);
    }

    @Override
    public Object getAnswer1() {
        int res = 0;
        for (Record r : records) {
            int valid = 0;
            // System.out.println(r.spring);
            Set<String> com = r.findAllCommutation();
            // System.out.println(com);
            // System.out.println(com.size());
            /*
             * for (String s : com) { if (r.isSolution(s)) { System.out.println(s); valid++; } }
             * System.out.println(valid);
             */
            res += com.size();
        }
        return res;
    }


    @Override
    public Object getAnswer2() {
        long res = 0;
        int index = 0;
        for (Record r : records) {
            Record unfold = r.unfold();
            // int valid = 0;
            // System.out.println(r.spring);
            Set<String> com = unfold.findAllCommutation();
            // System.out.println(com);
            // System.out.println(com.size());
            /*
             * for (String s : com) { if (r.isSolution(s)) { System.out.println(s); valid++; } }
             * System.out.println(valid);
             */
            res += com.size();
            index++;
            System.out.println(com.size());
            System.out.println(index);
        }
        return res;
    }

    public static void main(final String[] args) throws IOException {
        HotSprings puzzle = new HotSprings("year2023/day12/input");
        System.out.println("Answer 1: " + puzzle.getAnswer1());
        System.out.println("Answer 2: " + puzzle.getAnswer2());
    }


}
