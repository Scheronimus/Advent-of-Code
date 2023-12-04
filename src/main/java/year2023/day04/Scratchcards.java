package year2023.day04;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import helper.ListUtils;
import helper.Puzzle;


public class Scratchcards extends Puzzle {

    List<Card> cardSet = new ArrayList<>();

    public Scratchcards(final String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] cardSplit = line.split(":");
                int cardId = Integer.parseInt(cardSplit[0].replace("Card ", "").trim());

                String[] numbersSplit = cardSplit[1].trim().split("\\|");
                List<Integer> winningNumbers = stringToList(numbersSplit[0].trim());
                List<Integer> playNumbers = stringToList(numbersSplit[1].trim());

                cardSet.add(new Card(cardId, winningNumbers, playNumbers));
            }
        }
    }

    private List<Integer> stringToList(final String stringList) {
        List<Integer> list = new ArrayList<>();

        String[] numbers = stringList.split(" +");

        for (String number : numbers) {
            list.add(Integer.parseInt(number));
        }
        return list;
    }

    @Override
    public Object getAnswer1() {
        int res = 0;
        for (Card card : cardSet) {
            res += card.getPoints();
        }
        return res;
    }

    @Override
    public Object getAnswer2() {
        List<Integer> cardAmount = ListUtils.initializeIntegerList(cardSet.size(), 1);
        for (Card card : cardSet) {
            card.calculateWinningCard(cardAmount);
        }
        return cardAmount.stream().mapToInt(i -> i).sum();
    }

    public static void main(final String[] args) throws IOException {
        Scratchcards scratchcards = new Scratchcards("year2023/day04/input");
        System.out.println("Answer 1: " + scratchcards.getAnswer1());
        System.out.println("Answer 2: " + scratchcards.getAnswer2());
    }
}
