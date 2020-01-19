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
        for(String string : strings) add(string);
    }

    public void add(String string) {
        TrieNode trav = root;

        for(int index = 0; index < string.length(); index++) {

            char chr = string.charAt(index);

            TrieNode nextNode = trav.getNodeForCharacter(chr);
            if(nextNode == null) {
                nextNode = new TrieNode(index == string.length() - 1);
                trav.insertIntoMap(chr, nextNode);
                trav = nextNode;
            } else {
                trav = nextNode;
            }
        }

    }

    public boolean contains(String string) {

        TrieNode trav = root;

        for(int index = 0; index < string.length(); index++) {

            char chr = string.charAt(index);

            TrieNode nextNode = trav.getNodeForCharacter(chr);

            if(nextNode == null) return false;
            else {
                if(index == string.length() - 1) {
                    if(nextNode.isEndOfWord) return true;
                }
                trav = nextNode;
            }
        }
        return false;
    }

    private static class TrieNode {
        Map<Character, TrieNode> characterTrieNodeMap =null;
        boolean isEndOfWord;

        public TrieNode() {
            this.characterTrieNodeMap = new HashMap<>();
            this.isEndOfWord = false;
        }

        public TrieNode(boolean isEndOfWord) {
            this.characterTrieNodeMap = new HashMap<>();
            this.isEndOfWord = isEndOfWord;
        }

        public TrieNode getNodeForCharacter(char chr) {
            return this.characterTrieNodeMap.get(chr);
        }

        public void insertIntoMap(char chr, TrieNode node) {
            this.characterTrieNodeMap.put(chr, node);
        }

    }
}
