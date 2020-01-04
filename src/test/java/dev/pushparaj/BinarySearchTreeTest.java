package dev.pushparaj;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;


class TestTreeNode {

    Integer data;
    TestTreeNode left, right;

    public TestTreeNode(Integer data, TestTreeNode l, TestTreeNode r) {
        this.data = data;
        this.right = r;
        this.left = l;
    }

    static TestTreeNode add(TestTreeNode node, int data) {

        if (node == null) {
            node = new TestTreeNode(data, null, null);
        } else {
            // Place lower elem values on left
            if (data < node.data) {
                node.left = add(node.left, data);
            } else {
                node.right = add(node.right, data);
            }
        }
        return node;
    }

    static void preOrder(List<Integer> lst, TestTreeNode node) {

        if (node == null) return;

        lst.add(node.data);
        if (node.left != null) preOrder(lst, node.left);
        if (node.right != null) preOrder(lst, node.right);
    }

    static void inOrder(List<Integer> lst, TestTreeNode node) {

        if (node == null) return;

        if (node.left != null) inOrder(lst, node.left);
        lst.add(node.data);
        if (node.right != null) inOrder(lst, node.right);
    }

    static void postOrder(List<Integer> lst, TestTreeNode node) {

        if (node == null) return;

        if (node.left != null) postOrder(lst, node.left);
        if (node.right != null) postOrder(lst, node.right);
        lst.add(node.data);
    }

    static void levelOrder(List<Integer> lst, TestTreeNode node) {

        Deque<TestTreeNode> q = new ArrayDeque<>();
        if (node != null) q.offer(node);

        while (!q.isEmpty()) {

            node = q.poll();
            lst.add(node.data);
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
    }
}

public class BinarySearchTreeTest {

    static final int LOOPS = 1000;

    @Before
    public void setup() {}

    @Test
    public void testIsEmpty() {

        BinarySearchTree<String> tree = new BinarySearchTree<>();
        assertTrue(tree.isEmpty());

        tree.add("Hello World!");
        assertFalse(tree.isEmpty());
    }

    @Test
    public void testSize() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        assertEquals(tree.size(), 0);

        tree.add("Hello World!");
        assertEquals(tree.size(), 1);
    }

    @Test
    public void testHeight() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();

        // Tree should look like:
        //        M
        //      J  S
        //    B   N Z
        //  A

        // No tree
        assertEquals(tree.height(), 0);

        // Layer One
        tree.add("M");
        assertEquals(tree.height(), 1);

        // Layer Two
        tree.add("J");
        assertEquals(tree.height(), 2);
        tree.add("S");
        assertEquals(tree.height(), 2);

        // Layer Three
        tree.add("B");
        assertEquals(tree.height(), 3);
        tree.add("N");
        assertEquals(tree.height(), 3);
        tree.add("Z");
        assertEquals(tree.height(), 3);

        // Layer 4
        tree.add("A");
        assertEquals(tree.height(), 4);
    }

    @Test
    public void testAdd() {

        // Add element which does not yet exist
        BinarySearchTree<Character> tree = new BinarySearchTree<>();
        assertTrue(tree.add('A'));

        // Add duplicate element
        assertFalse(tree.add('A'));

        // Add a second element which is not a duplicate
        assertTrue(tree.add('B'));
    }

    @Test
    public void testRemove() {

        // Try removing an element which doesn't exist
        BinarySearchTree<Character> tree = new BinarySearchTree<>();
        tree.add('A');
        assertEquals(tree.size(), 1);
        assertFalse(tree.remove('B'));
        assertEquals(tree.size(), 1);

        // Try removing an element which does exist
        tree.add('B');
        assertEquals(tree.size(), 2);
        assertTrue(tree.remove('B'));
        assertEquals(tree.size(), 1);
        assertEquals(tree.height(), 1);

        // Try removing the root
        assertTrue(tree.remove('A'));
        assertEquals(tree.size(), 0);
        assertEquals(tree.height(), 0);
    }

    @Test
    public void testContains() {

        // Setup tree
        BinarySearchTree<Character> tree = new BinarySearchTree<>();

        tree.add('B');
        tree.add('A');
        tree.add('C');

        // Try looking for an element which doesn't exist
        assertFalse(tree.contains('D'));

        // Try looking for an element which exists in the root
        assertTrue(tree.contains('B'));

        // Try looking for an element which exists as the left child of the root
        assertTrue(tree.contains('A'));

        // Try looking for an element which exists as the right child of the root
        assertTrue(tree.contains('C'));
    }


    @Test
    public void randomRemoveTests() {

        for (int i = 0; i < LOOPS; i++) {

            int size = i;
            BinarySearchTree<Integer> tree = new BinarySearchTree<>();
            List<Integer> lst = genRandList(size);
            for (Integer value : lst) tree.add(value);

            Collections.shuffle(lst);
            // Remove all the elements we just placed in the tree
            for (int j = 0; j < size; j++) {

                Integer value = lst.get(j);

                assertTrue(tree.remove(value));
                assertFalse(tree.contains(value));
                assertEquals(tree.size(), size - j - 1);
            }

            assertTrue(tree.isEmpty());
        }
    }

    static List<Integer> genRandList(int sz) {
        List<Integer> lst = new ArrayList<>(sz);
        for (int i = 0; i < sz; i++) lst.add(i);
        Collections.shuffle(lst);
        return lst;
    }

}