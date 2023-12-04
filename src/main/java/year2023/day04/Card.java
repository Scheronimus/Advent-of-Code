package year2023.day04;

import java.util.ArrayList;
import java.util.List;

public class Card {

    int id;
    List<Integer> winningNumbers;
    List<Integer> playNumbers;

    public Card(final int id, final List<Integer> winningNumbers, final List<Integer> playNumbers) {
        super();
        this.id = id;
        this.winningNumbers = winningNumbers;
        this.playNumbers = playNumbers;
    }

    public int getPoints() {
        List<Integer> match = getMatchingNumber();
        int n = match.size();
        if (n > 0) {
            return (int)Math.pow(2, n - 1);
        } else {
            return 0;
        }
    }

    public List<Integer> getMatchingNumber() {
        List<Integer> list = new ArrayList<>();

        for (Integer n : winningNumbers) {
            if (playNumbers.contains(n)) {
                list.add(n);
            }
        }

        return list;
    }

    public void calculateWinningCard(final List<Integer> cardAmount) {
        List<Integer> match = getMatchingNumber();
        int n = match.size();
        int multiplier = cardAmount.get(this.id - 1);
        for (int i = 0; i < n; i++) {
            cardAmount.set(this.id + i, cardAmount.get(this.id + i) + multiplier);
        }
    }


}
