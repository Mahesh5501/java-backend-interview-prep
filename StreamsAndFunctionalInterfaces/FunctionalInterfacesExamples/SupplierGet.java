package StreamsAndFunctionalInterfaces.FunctionalInterfacesExamples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class SupplierGet {
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "Mahesh", 5, true, Arrays.asList("Roulette", "Blackjack")));
        players.add(new Player(2, "Ashish", 10, false, Arrays.asList("Poker")));
        players.add(new Player(3, "Varun", 7, true, Arrays.asList("Roulette", "Poker")));

        Supplier<String> unKnownPlayer = () ->  "Unknown Player";
        System.out.println(unKnownPlayer.get());

    }

}
