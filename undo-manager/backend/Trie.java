import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEnd;
    String word;
}

class Trie {
    private final TrieNode root = new TrieNode();

    void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            char normalized = Character.toLowerCase(c);
            node.children.putIfAbsent(normalized, new TrieNode());
            node = node.children.get(normalized);
        }
        node.isEnd = true;
        node.word = word;
    }

    boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            char normalized = Character.toLowerCase(c);
            if (!node.children.containsKey(normalized)) {
                return false;
            }
            node = node.children.get(normalized);
        }
        return node.isEnd;
    }

    List<String> suggest(String prefix, int limit) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            char normalized = Character.toLowerCase(c);
            if (!node.children.containsKey(normalized)) {
                return new ArrayList<>();
            }
            node = node.children.get(normalized);
        }

        List<String> suggestions = new ArrayList<>();
        collectWords(node, suggestions, limit);
        return suggestions;
    }

    private void collectWords(TrieNode node, List<String> suggestions, int limit) {
        if (node == null || suggestions.size() >= limit) {
            return;
        }

        if (node.isEnd) {
            suggestions.add(node.word);
        }

        for (TrieNode child : node.children.values()) {
            collectWords(child, suggestions, limit);
            if (suggestions.size() >= limit) {
                return;
            }
        }
    }
}
