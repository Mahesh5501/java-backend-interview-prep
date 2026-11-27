package DSA.TrieUnionFind;

/*
 * Problem 02 — Design Add and Search Words (LeetCode 211)
 */
public class WordDictionary {

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
        WordDictionary dict = new WordDictionary();
        dict.addWord("bad");
        dict.addWord("dad");
        dict.addWord("mad");
        System.out.println("search pad: " + dict.search("pad"));
        System.out.println("search .ad: " + dict.search(".ad"));
        System.out.println("search b..: " + dict.search("b.."));
    }
}