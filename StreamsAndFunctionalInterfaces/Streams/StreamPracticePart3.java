package StreamsAndFunctionalInterfaces.Streams;

import java.util.*;
import java.util.stream.Collectors;

public class StreamPracticePart3 {

    /*
Problem:
Find the first active player and return their name.
If no active player exists, return "No Active Player".

Expected: "Mahesh"
Return: String
*/
    static String getFirstActivePlayerName(List<Player> players) {
        return players.stream()
                .filter(Player::isActive)
                .findFirst()
                .map(Player::getName)
                .orElse("Player Not Found");
    }

    /*
Problem: Count how many times each game is played by all players.

Example:
Mahesh -> [Roulette, Blackjack]
Ashish -> [Poker]
Varun  -> [Roulette]

Expected:
{
    Roulette=2,
    Blackjack=1,
    Poker=1
}
Return: Map<String, Long>
*/
    static Map<String, Long> countGames(List<Player> players) {
        return players.stream()
                .flatMap(player -> player.getGames().stream())
                .collect(Collectors.groupingBy(game -> game , Collectors.counting()));
    }

/*
Problem: Calculate the average score of active players and inactive players separately.

Expected:
{
    true=6.0,
    false=10.0
}
Return: Map<Boolean, Double>
*/
    static Map<Boolean, Double> averageScoreByStatus(List<Player> players) {
        return players.stream()
                .collect(Collectors.partitioningBy(Player::isActive, Collectors.averagingInt(Player::getScore)));
    }

/*
Problem:
Given a string containing multiple words with random capitalization and spaces, convert it into the following format:

1. Remove leading/trailing spaces.
2. Remove spaces between words.
3. Make the first character of each word uppercase.
4. Make the remaining characters lowercase.
5. Add '#' at the beginning.

Example:

Input:
" jaVa ProGraMing langUAGE"

Output:
"#JavaProgrammingLanguage"
*/
    static String convertToCamelCase(String input) {
        String s = input.trim();

        String result = "#";
        String [] arr = s.split(" +");

        for(String word : arr){
           String str = word.toLowerCase();
            String finalWord = Character.toUpperCase(str.charAt(0)) + str.substring(1);
            result += finalWord;
        }
        return result;
    }

/*
Problem: Find the Player with the highest score.

Players:
Mahesh -> 5
Ashish -> 10
Varun  -> 7

Expected result: Ashish
*/
    static Optional<Player> getHighestScoringPlayer(List<Player> players) {
       return players.stream()
                .max(Comparator.comparing(Player::getScore));
    }

    /*
Problem:
Find the second-highest score among Players.

Example:
Mahesh -> 5
Ashish -> 10
Varun  -> 7

Expected: 7
Important: If two players have the same highest score, consider the distinct score values.

Example: 10, 10, 7, 5
Second highest = 7
*/
    static Optional<Integer> getSecondHighestScore(List<Player> players) {

        return players.stream()
                .map(Player::getScore)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst();

    }

    /*
Problem: Find all duplicate scores.
Example: 10, 7, 10, 5, 7
Expected: [10, 7]
*/
    static List<Integer> findDuplicateScores(List<Player> players) {
        return players.stream()
                .collect(Collectors.groupingBy(Player::getScore, Collectors.counting()))
                .entrySet()
                .stream().filter(entry -> entry.getValue() > 1)
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());
    }

    /*
Problem:
Group players by active status and find the highest-scoring player in each group.

Example:
Mahesh -> 5  -> active
Ashish -> 10 -> inactive
Varun  -> 7  -> active

Expected conceptually:
true  -> Mahesh/Varun -> Varun (7)
false -> Ashish       -> Ashish (10)
*/
    static Map<Boolean, Optional<Player>> getHighestScorerByStatus(List<Player> players) {
       return players.stream()
                .collect(Collectors.groupingBy(Player::isActive, Collectors.maxBy(Comparator.comparing(Player::getScore))));
    }

    /*
Problem: Find the highest score among ACTIVE players.
Example:
Mahesh -> 5  -> active
Ashish -> 10 -> inactive
Varun  -> 7  -> active

Expected: 7
*/
    static Optional<Integer> getHighestActiveScore(List<Player> players) {
      return players.stream()
               .filter(Player::isActive)
               .map(Player::getScore)
               .max(Integer::compare);
    }

    /*
Problem: Return the names of the top 2 players by score.
Example:
Mahesh -> 5
Ashish -> 10
Varun  -> 7
Expected: [Ashish, Varun]
*/

    static List<String> getTop2PlayerNames(List<Player> players) {
        return players.stream()
                .sorted(Comparator.comparing(Player::getScore).reversed())
                .limit(2)
                .map(Player::getName)
                .collect(Collectors.toList());
    }

/*
Problem: Group players into HIGH and LOW based on score.

HIGH -> score >= 8
LOW  -> score < 8

Example:
Mahesh -> 5
Ashish -> 10
Varun  -> 7

Expected:
HIGH -> [Ashish]
LOW  -> [Mahesh, Varun]
*/

    static Map<String, List<Player>> groupPlayersByScoreRange(List<Player> players) {

       return players.stream()
                .collect(Collectors.groupingBy(player -> {
                    if (player.getScore() >= 8)
                        return "HIGH";
                    else return "LOW";
                }, Collectors.toList()));

//        return players.stream()
//                .collect(Collectors.groupingBy(player -> {
//                    if (player.getScore() >= 8)
//                        return "HIGH";
//                    else
//                        return "LOW";
//                }));

    }

    public static void main(String[] args) {
        List<Player> players = Arrays.asList(
                new Player(1, "Mahesh", 5, true, Arrays.asList("Roulette", "Blackjack")),
                new Player(2, "Ashish", 10, true, Arrays.asList("Poker")),
                new Player(3, "Varun", 7, true, Arrays.asList("Roulette")),
                new Player(4, "Akhil", 7, false, Arrays.asList("Blackjack"))
                //new Player(4, "Akhil", 5, true, Arrays.asList("Roulette", "Blackjack")),
        );

        System.out.println(getFirstActivePlayerName(players)); // Mahesh
        System.out.println(countGames(players)); // {Roulette=2, Blackjack=1, Poker=1}
        System.out.println(averageScoreByStatus(players)); // {false=0.0, true=7.333333333333333}
        System.out.println(convertToCamelCase(" jaVa ProGraMing  langUAGE")); // #JavaProgrammingLanguage
        System.out.println(getHighestScoringPlayer(players)); //Optional[Player{id=2, name='Ashish', score=10, active=true}]
        System.out.println(getSecondHighestScore(players)); //Optional[7]
        System.out.println(findDuplicateScores(players)); //[7]
        System.out.println(getHighestScorerByStatus(players)); //{false=Optional[Player{id=4, name='Akhil', score=7, active=false}], true=Optional[Player{id=2, name='Ashish', score=10, active=true}]}
        System.out.println(getHighestActiveScore(players)); //Optional[10]
        System.out.println(getTop2PlayerNames(players)); //[Ashish, Varun]
        System.out.println(groupPlayersByScoreRange(players)); //{HIGH=[Player{id=2, name='Ashish', score=10, active=true}], LOW=[Player{id=1, name='Mahesh', score=5, active=true}, Player{id=3, name='Varun', score=7, active=true}, Player{id=4, name='Akhil', score=7, active=false}]}
    }
}
