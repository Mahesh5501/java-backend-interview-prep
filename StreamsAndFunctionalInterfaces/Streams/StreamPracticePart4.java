package StreamsAndFunctionalInterfaces.Streams;

import java.util.*;
import java.util.stream.Collectors;

public class StreamPracticePart4 {

    /*
Problem: Given a String, find the first character that appears only once.

Example: "swiss"

Frequencies:
s -> 3
w -> 1
i -> 1

Expected: 'w'

Return Optional<Character> because the String may contain no unique character.
*/
    static Optional<Character> firstNonRepeatedCharacter(String input) {

        return input.chars()
                .mapToObj( c -> (char) c)
                .collect(Collectors.groupingBy(character -> character , LinkedHashMap::new , Collectors.counting()))
                .entrySet()
                .stream().filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst();

    }



    public static void main(String[] args) {
        List<Player> players = Arrays.asList(
                new Player(1, "Mahesh", 5, true, Arrays.asList("Roulette", "Blackjack")),
                new Player(2, "Ashish", 10, true, Arrays.asList("Poker")),
                new Player(3, "Varun", 7, true, Arrays.asList("Roulette")),
                new Player(4, "Akhil", 7, false, Arrays.asList("Blackjack"))
                //new Player(4, "Akhil", 5, true, Arrays.asList("Roulette", "Blackjack")),
        );
        System.out.println(firstNonRepeatedCharacter("swiss")); //Optional[w]
    }
}
