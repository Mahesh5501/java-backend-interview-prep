package StreamsAndFunctionalInterfaces.FunctionalInterfacesExamples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class FunctionApply {
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "Mahesh", 5, true, Arrays.asList("Roulette", "Blackjack")));
        players.add(new Player(2, "Ashish", 10, false, Arrays.asList("Poker")));
        players.add(new Player(3, "Varun", 7, true, Arrays.asList("Roulette", "Poker")));

        Function<Player, String> getName = player -> player.getName();
        Function<Player, Integer> getScore = player -> player.getScore();
        Function<Player, String> getNamenScore = player -> player.getName() + " - Score: "+player.getScore();
        String name = getName.apply(players.get(0));
        Integer score = getScore.apply(players.get(0));
        String namenScore = getNamenScore.apply(players.get(0));
        System.out.println(name);
        System.out.println(score);
        System.out.println(namenScore);


    }
}
