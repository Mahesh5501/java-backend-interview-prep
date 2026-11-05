package StreamsAndFunctionalInterfaces.FunctionalInterfacesExamples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerAccept {
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "Mahesh", 5, true, Arrays.asList("Roulette", "Blackjack")));
        players.add(new Player(2, "Ashish", 10, false, Arrays.asList("Poker")));
        players.add(new Player(3, "Varun", 7, true, Arrays.asList("Roulette", "Poker")));

        Consumer<Player> printName = player -> System.out.println(player.getName());
        Consumer<Player> printPlayer = player -> System.out.println(player.getName() + " - " + player.getScore() + " - " + player.isActive());

        printName.accept(players.get(0));
        printPlayer.accept(players.get(0));

    }
}
