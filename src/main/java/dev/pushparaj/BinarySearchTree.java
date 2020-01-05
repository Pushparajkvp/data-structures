package dev.pushparaj;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

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
                return getPreorderIterator();
                break;
            case IN_ORDER:
                break;
            case POST_ORDER:
                break;
            case LEVEL_ORDER:
                break;
        }
    }

    private java.util.Iterator<T> getPreorderIterator() {
        final int iteratorCount = nodesCount;
        final Stack<Node<T>> stack = new Stack<>();
        stack.push(root);

        return new java.util.Iterator<T>() {

            @Override
            public boolean hasNext() {
                if(iteratorCount != nodesCount) throw new ConcurrentModificationException("Tree modified");
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if(iteratorCount != nodesCount) throw new ConcurrentModificationException("Tree modified");
                Node<T> node = stack.pop();
                if(node.left != null) stack.push(node.left);
                if(node.right != null) stack.push(node.right);
                return node.data;
            }
        };
    }

    private java.util.Iterator<T> getInOrderIterator() {

        final int iteratorCount = nodesCount;
        final Stack<Node<T>> iteratorStack = new Stack<>();
        final Queue<Node<T>> queue = new Queue<>();

        Node<T> currNode = root;

        while (currNode != null || iteratorStack.size() > 0) {

            while (currNode != null) {
                iteratorStack.push(currNode);
                currNode = currNode.left;
            }

            currNode = iteratorStack.pop();
            queue.offer(currNode);

            currNode = currNode.right;
        }

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if(iteratorCount != nodesCount) throw new ConcurrentModificationException("Tree modified");
                return root != null && !queue.isEmpty();
            }

            @Override
            public T next() {
                return queue.poll().data;
            }
        };
    }

    private java.util.Iterator<T> getPostorderIterator() {
        final int iteratorCount = nodesCount;
        final Stack<Node<T>> iteratorStack = new Stack<>();
        final Stack<Node<T>> stack = new Stack<>();

        iteratorStack.push(root);

        while (!iteratorStack.isEmpty()){
            Node<T> node = iteratorStack.p
            stack.push();

        }
        return new java.util.Iterator<T>() {

            @Override
            public boolean hasNext() {
                if(iteratorCount != nodesCount) throw new ConcurrentModificationException("Tree modified");
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                if(iteratorCount != nodesCount) throw new ConcurrentModificationException("Tree modified");
                Node<T> node = stack.pop();
                if(node.left != null) stack.push(node.left);
                if(node.right != null) stack.push(node.right);
                return node.data;
            }
        };
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
