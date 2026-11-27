package DSA.TrieUnionFind;

import java.util.ArrayList;
import java.util.List;

/*
Problem: Design Add and Search Words (LeetCode 211)

See notes/Problem_02_Design_Add_and_Search_Words.md — every approach below matches the note.
 */
public class WordDictionary {

    // APPROACH 1: LINEAR LIST + PATTERN MATCH (BRUTE)
    // Why: scan every stored word; check char-by-char with '.' wildcard.
    // addWord: O(1) amortized   search: O(n · L)
    public static class WordDictionaryList {
        private final List<String> words = new ArrayList<>();

        public void addWord(String word) {
            words.add(word);
        }

        public boolean search(String word) {
            for (String w : words) {
                if (matches(w, word)) {
                    return true;
                }
            }
            return false;
        }

        private static boolean matches(String stored, String pattern) {
            if (stored.length() != pattern.length()) {
                return false;
            }
            for (int i = 0; i < pattern.length(); i++) {
                char p = pattern.charAt(i);
                if (p != '.' && p != stored.charAt(i)) {
                    return false;
                }
            }
            return true;
        }
    }

    // APPROACH 2: TRIE + DFS ON '.' (OPTIMAL — what we use)
    // Why: prefix pruning; only branches that could match '.' paths.
    // addWord: O(L)   search: O(26^dots · L) worst, much better average

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd;
    }

    private final TrieNode root = new TrieNode();

    public void addWord(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            int idx = ch - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        return searchFrom(0, word, root);
    }

    private boolean searchFrom(int i, String word, TrieNode node) {
        if (i == word.length()) {
            return node.isEnd;
        }
        char ch = word.charAt(i);
        if (ch == '.') {
            for (TrieNode child : node.children) {
                if (child != null && searchFrom(i + 1, word, child)) {
                    return true;
                }
            }
            return false;
        }
        int idx = ch - 'a';
        if (node.children[idx] == null) {
            return false;
        }
        return searchFrom(i + 1, word, node.children[idx]);
    }

    public static void main(String[] args) {
        WordDictionaryList listDict = new WordDictionaryList();
        listDict.addWord("bad");
        listDict.addWord("dad");
        listDict.addWord("mad");
        System.out.println("List .ad: " + listDict.search(".ad"));

        WordDictionary dict = new WordDictionary();
        dict.addWord("bad");
        dict.addWord("dad");
        dict.addWord("mad");
        System.out.println("Trie pad: " + dict.search("pad"));
        System.out.println("Trie .ad: " + dict.search(".ad"));
        System.out.println("Trie b..: " + dict.search("b.."));
    }
}