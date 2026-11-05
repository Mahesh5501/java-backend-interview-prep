package StreamsAndFunctionalInterfaces.Streams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class StreamPracticePart2 {
/*
Problem: Convert the players list into a Map where:

Key   = player id
Value = player name

Expected:
{
    1=Mahesh,
    2=Ashish,
    3=Varun
}

Return: Map<Integer, String>
*/
    static Map<Integer, String> playerIdToName(List<Player> players) {
        return players.stream()
                .collect(Collectors.toMap(Player::getId,Player::getName));

        //When we have Duplicate Keys we use Merge function (existing, latest) -> latest
//        return players.stream()
//                .collect(Collectors.toMap(Player::getId,Player::getName, (existing, latest) -> latest));
    }

    /*
Problem: Each player can have multiple games.
Return one List<String> containing all games played by all players.

Example:
Mahesh -> [Roulette, Blackjack]
Ashish -> [Poker]
Varun  -> [Roulette]

Expected: [Roulette, Blackjack, Poker, Roulette]
Return: List<String>
*/
    static List<String> getAllGames(List<Player> players) {
        return players.stream()
                .flatMap(player -> player.getGames().stream())
                .collect(Collectors.toList());
    }

/*
Problem: Return all unique games played by all players.

Example:
Mahesh -> [Roulette, Blackjack]
Ashish -> [Poker]
Varun  -> [Roulette]

Expected: [Roulette, Blackjack, Poker]

Return: List<String>
*/
    static List<String> getUniqueGames(List<Player> players) {
        return players.stream()
                .flatMap(player -> player.getGames().stream())
                .distinct()
                .collect(Collectors.toList());
    }

/*
Problem:
Check whether at least one active player has a score
greater than 8.

Expected:
true
*/
    static boolean hasActivePlayerWithScoreGreaterThan8(List<Player> players) {
        return players.stream()
                .anyMatch(player -> player.isActive() && player.getScore()>8);
    }

/*
Problem: Check whether ALL players are active.
Expected: false
*/
    static boolean areAllPlayersActive(List<Player> players) {
        return players.stream().allMatch(Player::isActive);
    }

    /*
Problem: Calculate the total score of all players.
Example:
Mahesh -> 5
Ashish -> 10
Varun  -> 7

Expected: 22
Return: int
*/
    static int getTotalScore(List<Player> players) {
//        return players.stream().map(Player::getScore).reduce(0, (a,b) -> a+b);
        return players.stream()
                .map(Player::getScore)
                .reduce(0,Integer::sum);
    }

    /*
Problem: Find the highest score among all players.
Example:
Mahesh -> 5
Ashish -> 10
Varun  -> 7

Expected: 10
Return: int
*/
    static int getHighestScore(List<Player> players) {
        return players.stream()
                .map(Player::getScore)
                .reduce(0, Integer::max);
    }

/*
Problem: Calculate the average score of all players.
Example:
Mahesh -> 5
Ashish -> 10
Varun  -> 7

Expected: 7.333333333333333
Return: double
*/
    static double getAverageScore(List<Player> players) {
        return players.stream()
                .collect(Collectors.averagingInt(Player::getScore));
    }

    /*
Problem: Find the first active player in the list.
Expected: Optional[Mahesh]
*/
    static Optional<Player> findFirstActivePlayer(List<Player> players) {
        return players.stream()
                .filter(Player::isActive)
                .findFirst();
    }

    /*
Problem: Find any active player from the list.

Expected:
Optional[Player{Mahesh}]
(or another active player)

Return: Optional<Player>
*/
    static Optional<Player> findAnyActivePlayer(List<Player> players) {
        return players.stream()
                .filter(Player::isActive)
                .findAny();
    }


    public static void main(String[] args) {
        List<Player> players = Arrays.asList(
                new Player(1, "Mahesh", 5, true, Arrays.asList("Roulette", "Blackjack")),
                new Player(2, "Ashish", 10, true, Arrays.asList("Poker")),
                new Player(3, "Varun", 7, true, Arrays.asList("Roulette"))
                //                new Player(4, "Akhil", 5, true, Arrays.asList("Roulette", "Blackjack")),
        );

        System.out.println(playerIdToName(players)); // {1=Mahesh, 2=Ashish, 3=Varun}
        System.out.println(getAllGames(players)); // [Roulette, Blackjack, Poker, Roulette]
        System.out.println(getUniqueGames(players)); // [Roulette, Blackjack, Poker]
        System.out.println(hasActivePlayerWithScoreGreaterThan8(players)); // true
        System.out.println(areAllPlayersActive(players)); // false
        System.out.println(getTotalScore(players)); // 22
        System.out.println(getHighestScore(players)); // 10
        System.out.println(getAverageScore(players)); // 7.333333333333333
        System.out.println(findFirstActivePlayer(players)); // Optional[Player{Mahesh}]
        System.out.println(findAnyActivePlayer(players)); // Optional[Player{Mahesh}]
    }
}
