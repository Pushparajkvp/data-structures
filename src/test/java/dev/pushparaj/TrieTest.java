package dev.pushparaj;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;

public class TrieTest {

    @Test
    public void initialTest() {

        String[] input = new String[]{"abcd", "bcd", "bce", "abde"};
        Trie trie = new Trie(input);

        assertTrue(trie.contains("abcd"));
        assertTrue(trie.contains("bcd"));
        assertFalse(trie.contains("zde"));
        assertFalse(trie.contains("ab"));
    }
}
