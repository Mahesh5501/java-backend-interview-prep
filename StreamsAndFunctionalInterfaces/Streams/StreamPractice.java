package StreamsAndFunctionalInterfaces.Streams;

import java.util.*;
import java.util.stream.*;

public class StreamPractice {

    /*
    Problem: Given a list of Player objects, return the names of all active players.

    Example:
    Mahesh -> score 5  -> active
    Ashish -> score 10 -> inactive
    Varun  -> score 7  -> active

    Expected output:
    [Mahesh, Varun]
    */
    static List<String> getActivePlayerNames(List<Player> players) {

        List<String> names = players.stream()
                .filter(Player::isActive)
                .map(Player::getName)
                .collect(Collectors.toList());

        return names;
    }

    /*
Problem: Given a list of players, return the top K players based on score.

Example:
Mahesh -> 5
Ashish -> 10
Varun  -> 7

k = 2

Expected:
Ashish -> 10
Varun  -> 7
*/
    static List<Player> getTopKPlayers(List<Player> players, int k) {
        List<Player> topPlayer = players.stream()
                .sorted(Comparator.comparing(Player::getScore).reversed())
                .limit(k)
                .collect(Collectors.toList());

        return topPlayer;
    }

    /*
Problem: Given a list of players, return the names of the top K active players based on their score.

Example:
Mahesh -> score 5  -> active
Ashish -> score 10 -> inactive
Varun  -> score 7  -> active

k = 2

Expected: [Varun, Mahesh]
*/
    static List<String> getTopKActivePlayerNames(List<Player> players, int k) {
        List<String> playerNames = players.stream()
                .filter(Player::isActive)
                .sorted(Comparator.comparing(Player::getScore).reversed())
                .limit(k)
                .map(Player::getName)
                .collect(Collectors.toList());
        return playerNames;
    }

    /*
Problem: Given a list of players, group the players based on their active status.

Expected:
{
    true=[Mahesh, Varun],
    false=[Ashish]
}

Return Map<Boolean, List<String>>
*/
    static Map<Boolean, List<String>> groupPlayerNamesByStatus(List<Player> players) {
        Map<Boolean, List<String>> playerByStatus = players.stream()
                .collect(Collectors.groupingBy(Player::isActive, Collectors.mapping(Player::getName, Collectors.toList())));
        return playerByStatus;
    }

    /*
Problem: Given a list of players, separate them into active and inactive players.
Return: Map<Boolean, List<Player>>

Expected:
true  -> [Mahesh, Varun]
false -> [Ashish]
*/
    static Map<Boolean, List<Player>> partitionPlayersByStatus(List<Player> players) {
       Map<Boolean, List<Player>> partitionByStatus = players.stream()
               .collect(Collectors.partitioningBy(Player::isActive));
        return partitionByStatus;
    }

    /*
Problem:
Given a list of players, count how many players are active and how many are inactive.

Expected:
{
    true=2,
    false=1
}

Return: Map<Boolean, Long>
*/
    static Map<Boolean, Long> countPlayersByStatus(List<Player> players) {
        Map<Boolean, Long> playerCountByStatus = players.stream()
                .collect(Collectors.groupingBy(Player::isActive, Collectors.counting()));
        return playerCountByStatus;
    }

    /*
Problem:
Given a list of players, calculate the total score for active and inactive players separately.

Expected:
true  -> 12
false -> 10

Return: Map<Boolean, Integer>
*/
    static Map<Boolean, Integer> totalScoreByStatus(List<Player> players) {
       Map<Boolean, Integer> totalScoreByStatus = players.stream()
               .collect(Collectors.groupingBy(Player::isActive, Collectors.summingInt(Player::getScore)));
        return totalScoreByStatus;
    }

    /*
Problem: Given a list of players, find the highest-scoring player for each active/inactive group.

Expected:
true  -> Varun
false -> Ashish

Return: Map<Boolean, Optional<Player>>
*/
    static Map<Boolean, Optional<Player>> highestScoringPlayerByStatus(List<Player> players) {
        return players.stream()
                .collect(Collectors.groupingBy(Player::isActive, Collectors.maxBy((Comparator.comparing(Player::getScore)))));
    }

    /*
Problem: Given a list of players, find the lowest-scoring player for each active/inactive group.

Expected:
true  -> Mahesh
false -> Ashish

Return: Map<Boolean, Optional<Player>>
*/
    static Map<Boolean, Optional<Player>> lowestScoringPlayerByStatus(List<Player> players) {
        return players.stream()
                .collect(Collectors.groupingBy(Player::isActive,Collectors.minBy(Comparator.comparing(Player::getScore))));
    }


    public static void main(String[] args) {
        List<Player> players = Arrays.asList(
                new Player(1, "Mahesh", 5, true, Arrays.asList("Roulette", "Blackjack")),
                new Player(2, "Ashish", 10, false, Arrays.asList("Poker")),
                new Player(3, "Varun", 7, true, Arrays.asList("Roulette"))
        );
        System.out.println(getActivePlayerNames(players)); // [Mahesh, Varun]
        System.out.println(getTopKPlayers(players, 2)); // [Ashish, Varun]
        System.out.println(getTopKActivePlayerNames(players, 2)); // [Varun, Mahesh]
        System.out.println(groupPlayerNamesByStatus(players)); // {true=[Mahesh, Varun], false=[Ashish]}
        System.out.println(partitionPlayersByStatus(players)); // {false=[Ashish], true=[Mahesh, Varun]}
        System.out.println(countPlayersByStatus(players)); // {false=1, true=2}
        System.out.println(totalScoreByStatus(players)); // {false=10, true=12}
        System.out.println(highestScoringPlayerByStatus(players)); // {false=Optional[Player{Ashish}], true=Optional[Player{Varun}]}
        System.out.println(lowestScoringPlayerByStatus(players)); // {false=Optional[Player{Ashish}], true=Optional[Player{Mahesh}]}

    }
}
