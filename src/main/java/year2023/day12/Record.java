package year2023.day12;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Record {
    String spring;
    List<Integer> damagedSprings;
    Pattern p;

    public Record(final String spring, final List<Integer> damagedSprings) {
        super();
        this.spring = spring;
        this.damagedSprings = damagedSprings;
        this.p = buildPattern();
    }

    @Override
    public String toString() {
        return "Record [spring=" + spring + ", damagedSprings=" + damagedSprings + "]";
    }

    public Pattern buildPattern() {
        String s = "^\\.*";
        int index = 0;
        for (Integer val : damagedSprings) {
            s += "#{" + val + "}";
            if (index == damagedSprings.size() - 1) {
                s += "\\.*$";
            } else {
                s += "\\.+";
            }
            index++;
        }
        System.out.println(s);
        Pattern pattern = Pattern.compile(s);
        return pattern;

    }

    public boolean isSolution(final String input) {
        if (input.contains("?")) {
            return false;
        }
        Matcher matcher = p.matcher(input);
        return matcher.find();
    }

    public Set<String> findAllCommutation() {
        return findAllCommutation(spring);
    }

    public Set<String> findAllCommutation(final String input) {


        if (!input.contains("?")) {
            return Set.of(input);
        }

        Set<String> res = new HashSet<>();
        String temp = input;
        res.addAll(findAllCommutation(temp.replaceFirst("\\?", ".")));
        temp = input;
        res.addAll(findAllCommutation(temp.replaceFirst("\\?", "#")));

        return res;

    }


}
