package DSA.TrieUnionFind;

import java.util.*;

/*
Problem: Accounts Merge (LeetCode 721)

See notes/Problem_06_Accounts_Merge.md — every approach below matches the note.
 */
public class AccountsMerge {

    // APPROACH 1: PAIRWISE EMAIL OVERLAP UNION (BRUTE)
    // Why: union account i and j when any email appears in both — simple O(n²) scan.
    // Time: O(n² · E²)   Extra space: O(n · E)
    public static List<List<String>> accountsMergeBrute(List<List<String>> accounts) {
        int n = accounts.size();
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            Set<String> emailsI = new HashSet<>(accounts.get(i).subList(1, accounts.get(i).size()));
            for (int j = i + 1; j < n; j++) {
                for (int k = 1; k < accounts.get(j).size(); k++) {
                    if (emailsI.contains(accounts.get(j).get(k))) {
                        uf.union(i, j);
                        break;
                    }
                }
            }
        }
        return buildMerged(accounts, uf);
    }

    // APPROACH 2: UNION-FIND VIA EMAIL→ACCOUNT MAP (OPTIMAL — what we use)
    // Why: one pass links accounts sharing any email; same groups as brute, linear-ish.
    // Time: O(n · E · α(n))   Extra space: O(n · E)
    public static List<List<String>> accountsMerge(List<List<String>> accounts) {
        int n = accounts.size();
        UnionFind uf = new UnionFind(n);
        Map<String, Integer> emailToId = new HashMap<>();

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < accounts.get(i).size(); j++) {
                String email = accounts.get(i).get(j);
                if (emailToId.containsKey(email)) {
                    uf.union(i, emailToId.get(email));
                } else {
                    emailToId.put(email, i);
                }
            }
        }
        return buildMerged(accounts, uf);
    }

    private static List<List<String>> buildMerged(List<List<String>> accounts, UnionFind uf) {
        Map<Integer, TreeSet<String>> groups = new HashMap<>();
        for (int i = 0; i < accounts.size(); i++) {
            int root = uf.find(i);
            groups.putIfAbsent(root, new TreeSet<>());
            for (int j = 1; j < accounts.get(i).size(); j++) {
                groups.get(root).add(accounts.get(i).get(j));
            }
        }

        List<List<String>> result = new ArrayList<>();
        for (Map.Entry<Integer, TreeSet<String>> e : groups.entrySet()) {
            List<String> row = new ArrayList<>();
            row.add(accounts.get(e.getKey()).get(0));
            row.addAll(e.getValue());
            result.add(row);
        }
        return result;
    }

    public static void main(String[] args) {
        List<List<String>> accounts = new ArrayList<>();
        accounts.add(Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com"));
        accounts.add(Arrays.asList("John", "johnsmith@mail.com", "john00@mail.com"));
        accounts.add(Arrays.asList("Mary", "mary@mail.com"));
        accounts.add(Arrays.asList("John", "johnnybravo@mail.com"));
        System.out.println("UF:    " + accountsMerge(accounts));
        System.out.println("Brute: " + accountsMergeBrute(accounts));
    }
}