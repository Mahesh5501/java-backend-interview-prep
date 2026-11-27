package DSA.Graphs;

import java.util.*;

/*
 * Problem 06 — Word Ladder (LeetCode 127)
 */
public class WordLadder {

    // BFS — shortest path in unweighted implicit graph
    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) {
            return 0;
        }
        ArrayDeque<String> q = new ArrayDeque<>();
        q.offer(beginWord);
        Set<String> seen = new HashSet<>();
        seen.add(beginWord);
        int level = 1;
        int len = beginWord.length();

        while (!q.isEmpty()) {
            int size = q.size();
            for (int s = 0; s < size; s++) {
                String word = q.poll();
                char[] arr = word.toCharArray();
                for (int i = 0; i < len; i++) {
                    char old = arr[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == old) {
                            continue;
                        }
                        arr[i] = c;
                        String next = new String(arr);
                        if (next.equals(endWord)) {
                            return level + 1;
                        }
                        if (dict.contains(next) && seen.add(next)) {
                            q.offer(next);
                        }
                    }
                    arr[i] = old;
                }
            }
            level++;
        }
        return 0;
    }

    public static void main(String[] args) {
        String begin = "hit";
        String end = "cog";
        List<String> list = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
        System.out.println("Ladder length: " + ladderLength(begin, end, list));
    }
}