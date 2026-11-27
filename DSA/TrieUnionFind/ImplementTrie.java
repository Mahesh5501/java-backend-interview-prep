package DSA.TrieUnionFind;

/*
 * Problem 01 — Implement Trie (LeetCode 208)
 */
public class ImplementTrie {

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd;
    }

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
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println("search apple: " + trie.search("apple"));
        System.out.println("search app: " + trie.search("app"));
        System.out.println("startsWith app: " + trie.startsWith("app"));
        trie.insert("app");
        System.out.println("search app after insert: " + trie.search("app"));
    }
}