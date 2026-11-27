package DSA.TrieUnionFind;

import java.util.*;

/*
Problem: Word Search II (LeetCode 212)

See notes/Problem_03_Word_Search_II.md — every approach below matches the note.
 */
public class WordSearchII {

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        String word;
    }

    // APPROACH 1: DFS BOARD PER WORD (BRUTE)
    // Why: for each word, run classic word-search — no shared prefix work.
    // Time: O(W · m · n · 4^L)   Extra space: O(L)
    public static List<String> findWordsBrute(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();
        for (String w : words) {
            if (existsWord(board, w)) {
                result.add(w);
            }
        }
        return result;
    }

    private static boolean existsWord(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (dfsOne(board, r, c, word, 0, new boolean[m][n])) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean dfsOne(char[][] board, int r, int c, String word, int idx, boolean[][] vis) {
        if (idx == word.length()) {
            return true;
        }
        if (r < 0 || c < 0 || r >= board.length || c >= board[0].length || vis[r][c]) {
            return false;
        }
        if (board[r][c] != word.charAt(idx)) {
            return false;
        }
        vis[r][c] = true;
        int[] dr = { 1, -1, 0, 0 };
        int[] dc = { 0, 0, 1, -1 };
        for (int d = 0; d < 4; d++) {
            if (dfsOne(board, r + dr[d], c + dc[d], word, idx + 1, vis)) {
                vis[r][c] = false;
                return true;
            }
        }
        vis[r][c] = false;
        return false;
    }

    // APPROACH 2: TRIE + BOARD DFS (OPTIMAL — what we use)
    // Why: one walk visits prefixes shared across many words; prune dead branches.
    // Time: O(m · n · 4^L) with heavy pruning   Extra space: O(total letters)
    public static List<String> findWords(char[][] board, String[] words) {
        TrieNode root = buildTrie(words);
        List<String> result = new ArrayList<>();
        int m = board.length, n = board[0].length;
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                dfs(board, r, c, root, result);
            }
        }
        return result;
    }

    private static TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String w : words) {
            TrieNode node = root;
            for (char ch : w.toCharArray()) {
                int idx = ch - 'a';
                if (node.children[idx] == null) {
                    node.children[idx] = new TrieNode();
                }
                node = node.children[idx];
            }
            node.word = w;
        }
        return root;
    }

    private static void dfs(char[][] board, int r, int c, TrieNode node, List<String> result) {
        char ch = board[r][c];
        if (ch == '#' || node.children[ch - 'a'] == null) {
            return;
        }
        node = node.children[ch - 'a'];
        if (node.word != null) {
            result.add(node.word);
            node.word = null;
        }
        board[r][c] = '#';
        int[] dr = { 1, -1, 0, 0 };
        int[] dc = { 0, 0, 1, -1 };
        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d], nc = c + dc[d];
            if (nr >= 0 && nc >= 0 && nr < board.length && nc < board[0].length) {
                dfs(board, nr, nc, node, result);
            }
        }
        board[r][c] = ch;
    }

    public static void main(String[] args) {
        char[][] board = {
                { 'o', 'a', 'a', 'n' },
                { 'e', 't', 'a', 'e' },
                { 'i', 'h', 'k', 'r' },
                { 'i', 'f', 'l', 'v' },
        };
        String[] words = { "oath", "pea", "eat", "rain" };
        System.out.println("Trie:  " + findWords(board, words));
        System.out.println("Brute: " + findWordsBrute(board, words));
    }
}