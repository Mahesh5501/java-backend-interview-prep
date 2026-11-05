package StreamsAndFunctionalInterfaces.FunctionalInterfacesExamples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class PredicateTest {
    public static void main(String[] args) {

        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "Mahesh", 5, true, Arrays.asList("Roulette", "Blackjack")));
        players.add(new Player(2, "Ashish", 10, false, Arrays.asList("Poker")));
        players.add(new Player(3, "Varun", 7, true, Arrays.asList("Roulette", "Poker")));

        Predicate<Player> pridicate = (player) -> player.getId() == 1;
        Predicate<Player> actuvenScore = (player) -> player.isActive() && player.getScore() > 150;
        System.out.println(pridicate.test(players.get(0)));
        System.out.println(actuvenScore.test(players.get(0)));

        Predicate<Player> isActive = (player) -> player.isActive();
        Predicate<Player> score = player -> player.getScore() > 5;
        Predicate<Player> eligibleAnd = isActive.and(score);
        Predicate<Player> eligibleOr = isActive.or(score);
        System.out.println(eligibleAnd.test(players.get(1)));
        System.out.println(eligibleOr.test(players.get(1)));
    }
}
