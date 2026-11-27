package DSA.TrieUnionFind;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Problem 01 — Implement Trie (LeetCode 208)

See notes/Problem_01_Implement_Trie.md — every approach below matches the note.
 */
public class ImplementTrie {

    // APPROACH 1: HASH SET STORAGE (BRUTE — for learning)
    // Store full words; prefix check scans all stored strings.
    // Why use it: shows what trie optimizes away.
    // Time: insert O(1) avg; search O(total chars); startsWith similar   Space: O(total chars)
    public static class TrieBrute {
        private final Set<String> words = new HashSet<>();

        public void insert(String word) {
            words.add(word);
        }

        public boolean search(String word) {
            return words.contains(word);
        }

        public boolean startsWith(String prefix) {
            for (String w : words) {
                if (w.startsWith(prefix)) {
                    return true;
                }
            }
            return false;
        }
    }

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd;
    }

    // APPROACH 2: PREFIX TRIE (OPTIMAL — what we use)
    static class Trie {
        private final TrieNode root = new TrieNode();

        public void insert(String word) {
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
            TrieNode node = walk(word);
            return node != null && node.isEnd;
        }

        public boolean startsWith(String prefix) {
            return walk(prefix) != null;
        }

        private TrieNode walk(String s) {
            TrieNode node = root;
            for (char ch : s.toCharArray()) {
                int idx = ch - 'a';
                if (node.children[idx] == null) {
                    return null;
                }
                node = node.children[idx];
            }
            return node;
        }
    }

    public static void main(String[] args) {
        TrieBrute brute = new TrieBrute();
        brute.insert("apple");
        System.out.println("Brute search apple: " + brute.search("apple"));
        System.out.println("Brute startsWith app: " + brute.startsWith("app"));

        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println("Trie search apple: " + trie.search("apple"));
        System.out.println("Trie search app: " + trie.search("app"));
        System.out.println("Trie startsWith app: " + trie.startsWith("app"));
        trie.insert("app");
        System.out.println("Trie search app after insert: " + trie.search("app"));
    }
}