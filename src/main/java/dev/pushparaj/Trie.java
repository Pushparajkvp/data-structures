package dev.pushparaj;

import java.util.HashMap;
import java.util.Map;

public class Trie {

    private TrieNode root = null;

    public Trie() {
        root = new TrieNode();
    }

    public Trie(String[] strings) {
        root = new TrieNode();
        for(String string : strings) insert(string);
    }

    public boolean insert(String key) {
        return insert(key, 1);
    }

    public boolean delete(String key) {
        return delete(key, 1);
    }

    public boolean contains(String key) {
        return count(key) != 0;
    }

    public boolean insert(String key, int numInserts) {
        if(numInserts <= 0) throw new IllegalArgumentException("Number of inserts should not be less than or equal to zero value : " + numInserts);

        if(key == null) throw new IllegalArgumentException("Key cannot be null");

        TrieNode trav = root;
        int size = key.length();

        boolean createdNewNode = false, isPrefix = false;

        for(int index = 0; index < size; index++) {

            char chr = key.charAt(index);

            TrieNode nextNode = trav.characterTrieNodeMap.get(chr);

            if(nextNode == null) {
                nextNode = new TrieNode(index == size - 1);
                trav.characterTrieNodeMap.put(chr, nextNode);
                createdNewNode = true;
            } else {
                if(nextNode.isEndOfWord) isPrefix = true;
            }

            nextNode.count += numInserts;
            trav = nextNode;
        }
        return !createdNewNode || isPrefix;
    }

    public boolean delete(String key, int numDeletions) {
        if(numDeletions <= 0) throw new IllegalArgumentException("number of deletes cant be zero or negative value : " + numDeletions);

        if(key == null) throw new IllegalArgumentException("Key cannot be null");

        if(count(key) <= 0) return false;

        TrieNode trav = root;
        int size = key.length();

        for(int index = 0; index < size; index++) {

            char chr = key.charAt(index);

            TrieNode nextNode = trav.characterTrieNodeMap.get(chr);
            nextNode.count -= numDeletions;

            if(nextNode.count <= 0) {
                trav.characterTrieNodeMap.remove(chr);
                nextNode.characterTrieNodeMap = null;
                nextNode = null;
                return true;
            }

            trav = nextNode;
        }
        return true;
    }

    public int count(String key) {
        if(key == null) throw new IllegalArgumentException("Key cannot be null");
        TrieNode trav = root;
        int size = key.length();

        for(int index = 0; index < size; index++) {

            char chr = key.charAt(index);

            TrieNode nextNode = trav.characterTrieNodeMap.get(chr);

            if(nextNode == null) return 0;

            trav = nextNode;
        }

        if(trav != null) return trav.count;
        return 0;
    }

    // Clear the trie
    public void clear() {

        root.characterTrieNodeMap = null;
        root = new TrieNode();
    }
    private static class TrieNode {
        Map<Character, TrieNode> characterTrieNodeMap =null;
        boolean isEndOfWord;
        int count;

        public TrieNode() {
            this.characterTrieNodeMap = new HashMap<>();
            this.isEndOfWord = false;
            this.count = 0;
        }

        public TrieNode(boolean isEndOfWord) {
            this.characterTrieNodeMap = new HashMap<>();
            this.isEndOfWord = isEndOfWord;
            this.count = 0;
        }

        public void addChild(char chr, TrieNode node) {
            characterTrieNodeMap.put(chr, node);
        }

    }
}
