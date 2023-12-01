package year2022.day03;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Rucksack {
    String fullContent;
    String Compartiment1;
    String Compartiment2;

    public Rucksack(final String content) {
        super();
        this.fullContent = content;
        splitContentToCompatiment();
    }

    private void splitContentToCompatiment() {
        int lenght = fullContent.length();
        Compartiment1 = fullContent.substring(0, lenght / 2);
        Compartiment2 = fullContent.substring(lenght / 2, lenght);

    }

    public List<Character> findItemInBothCompartiment() {

        List<Character> l1 = Compartiment1.chars().mapToObj(i -> (char)i).collect(Collectors.toList());
        List<Character> l2 = Compartiment2.chars().mapToObj(i -> (char)i).collect(Collectors.toList());

        l1.retainAll(l2);

        List<Character> itemInBothCompartiment = new ArrayList<>(new LinkedHashSet<>(l1));
        // System.out.println(itemInBothCompartiment);
        return itemInBothCompartiment;

    }

    public List<Character> getFullContent() {
        return fullContent.chars().mapToObj(i -> (char)i).collect(Collectors.toList());
    }


}
