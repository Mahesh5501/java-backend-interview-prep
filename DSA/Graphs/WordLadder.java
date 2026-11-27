package DSA.Graphs;

import java.util.*;

/*
Problem: Word Ladder (LeetCode 127)

See notes/Problem_06_Word_Ladder.md — every approach below matches the note.
 */
public class WordLadder {

    // APPROACH 1: DFS OVER MUTATIONS (BRUTE)
    // Why: explores paths until endWord; not shortest-first — educational only.
    // Time: exponential   Extra space: O(words · wordLen)
    public static int ladderLengthBrute(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) {
            return 0;
        }
        int[] best = { Integer.MAX_VALUE };
        Set<String> path = new HashSet<>();
        path.add(beginWord);
        dfsLadder(beginWord, endWord, dict, path, 1, best);
        return best[0] == Integer.MAX_VALUE ? 0 : best[0];
    }

    private static void dfsLadder(String word, String end, Set<String> dict, Set<String> path, int len, int[] best) {
        if (word.equals(end)) {
            best[0] = Math.min(best[0], len);
            return;
        }
        char[] arr = word.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char old = arr[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == old) {
                    continue;
                }
                arr[i] = c;
                String next = new String(arr);
                if (dict.contains(next) && path.add(next)) {
                    dfsLadder(next, end, dict, path, len + 1, best);
                    path.remove(next);
                }
            }
            arr[i] = old;
        }
    }

    // APPROACH 2: BFS LEVELS (OPTIMAL — shortest unweighted path)
    // Why: first time we reach endWord is minimum number of transformations.
    // Time: O(n · L² · 26)   Extra space: O(n)
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
        System.out.println("BFS length:   " + ladderLength(begin, end, list));
        System.out.println("DFS length:   " + ladderLengthBrute(begin, end, list));
    }
}