package dev.pushparaj;

import java.util.ConcurrentModificationException;

/*
* public boolean isEmpty()
* public int size()
* public boolean add(T elem)
* private Node add(Node node, T elem)
* public boolean remove(T elem)
* private Node remove(Node node, T elem)
* private Node findMin(Node node)
* private Node findMax(Node node)
* public boolean contains(T elem)
* private boolean contains(Node node, T elem)
* public int height()
* private int height(Node node)
* public java.util.Iterator<T> traverse(TreeTraversalOrder order)
*
* */
public class BinarySearchTree<T extends Comparable<T>> {

    private int nodesCount = 0;

    private Node<T> root = null;

    public BinarySearchTree(){}

    public boolean isEmpty(){
        return root == null;
    }

    public int size() {
        return nodesCount;
    }

    public boolean add(T element) {
        if(contains(element))
            return false;

        root = add(root, element);
        nodesCount++;

        return true;
    }

    public boolean remove(T element) {
        if(isEmpty())
            return false;

        if(!contains(element))
            return false;

        root = remove(root, element);
        nodesCount--;

        return true;
    }

    public boolean contains(T element){
        return find(root, element) != null;
    }

    public int height() {
        return height(root);
    }
    public java.util.Iterator<T> traverse(TreeTraversalOrder order) {
        switch (order) {
            case PRE_ORDER:
                break;
            case IN_ORDER:
                break;
            case POST_ORDER:
                break;
            case LEVEL_ORDER:
                break;
        }
    }

    private java.util.Iterator<T> preorderIterator = new java.util.Iterator<T>() {

        private int iteratorCount = nodesCount;

        @Override
        public boolean hasNext() {
            if(iteratorCount != nodesCount) throw new ConcurrentModificationException("Tree modified");

            return false;
        }

        @Override
        public T next() {
            return null;
        }
    }

    private int height(Node node) {
        if(node == null) return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    private Node<T> remove(Node<T> node, T element) {

        int compareValue = element.compareTo(node.data);

        if(compareValue > 0) {
            node.right = remove(node.right, element);
        } else if (compareValue < 0) {
            node.left = remove(node.left, element);
        } else {
            if(node.left == null && node.right == null) {

                //Free up the space (not applicable for java just a reminder)
                node.data = null;
                node = null;

                return null;
            } else if (node.left == null) {

                Node<T> rightNode = node.right;

                //Free up the space (not applicable for java just a reminder)
                node.data = null;
                node = null;

                return rightNode;
            } else if(node.right == null){
                Node<T> leftNode = node.left;

                //Free up the space (not applicable for java just a reminder)
                node.data = null;
                node = null;

                return leftNode;
            } else {
                Node<T> rightSubTreeMin = digMin(node.right);
                node.data = rightSubTreeMin.data;
                node.right = remove(node.right, rightSubTreeMin.data);
            }
            return node;
        }
        return node;
    }


    private Node<T> digMin(Node<T> node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private Node<T> digMax(Node<T> node) {
        while (node.right != null) node = node.right;
        return node;
    }

    private Node<T> find(Node<T> node, T element) {

        if(node == null)
            return null;

        int compareValue = element.compareTo(node.data);
        if(compareValue == 0)
            return node;

        if(compareValue > 0) {
            if(node.right == null)
                return null;
            return find(node.right, element);
        }

        if(node.left == null)
            return null;
        return find(node.left, element);
    }

    private Node<T> add(Node<T> node, T data) {

        if(node == null) {
            node = new Node<>(null, null, data);
        } else {
            if(data.compareTo(node.data) > 0) {
                node.right = add(node.right, data);
            } else {
                node.left = add(node.left, data);
            }
        }

        return node;
    }


    private static class Node<T> {

        private Node left, right;
        private T data;

        public Node(Node<T> left, Node<T> right, T data){
            this.left = left;
            this.right = right;
            this.data = data;
        }
    }
}
