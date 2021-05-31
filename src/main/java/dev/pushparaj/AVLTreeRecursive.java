package dev.pushparaj;

public class AVLTreeRecursive<T extends Comparable<T>> {

    Node<T> root = null;

    int nodesCount = 0;

    public AVLTreeRecursive() {}

    public boolean insert(T value) {

        if(value == null || contains(value))
            return false;

        root = insert(value, root);
        nodesCount++;

        return true;
    }

    public boolean remove(T value) {
        if(value == null) return false;

        if(!contains(value)) return false;

        root = remove(value, root);
        nodesCount--;
        return true;
    }

    public boolean contains(T value) {
        return find(value, root) != null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return  nodesCount;
    }

    public int height() {
        if (root == null) return 0;
        return root.height;
    }


    private Node<T> remove(T value,Node<T> currNode) {

        int compareValue = currNode.value.compareTo(value);
        if(compareValue > 0)
            currNode.left = remove(value, currNode.left);
        else if(compareValue < 0)
            currNode.right = remove(value, currNode.right);
        else{
            if(currNode.left == null && currNode.right == null)
                return null;
            if(currNode.left == null)
                return currNode.right;
            if(currNode.right == null)
                return currNode.left;

            Node<T> successor = digMax(currNode.left);
            currNode.value = successor.value;
            currNode.left = remove(successor.value, currNode.left);
        }
        return balance(currNode);
    }


    private Node<T> digMin(Node<T> node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private Node<T> digMax(Node<T> node) {
        while (node.right != null) node = node.right;
        return node;
    }

    private Node<T> insert(T value, Node<T> currNode) {

        if(currNode == null) return new Node<>(value);

        if(currNode.value.compareTo(value) > 0)
            currNode.left = insert(value, currNode.left);
        else
            currNode.right = insert(value, currNode.right);

        return balance(currNode);
    }

    private Node<T> find(T value, Node<T> currNode) {
        if(currNode == null)
            return null;

        if(currNode.value.equals(value))
            return currNode;

        if(currNode.value.compareTo(value) > 0)
            return find(value, currNode.left);

        return find(value, currNode.right);
    }



    private Node<T> balance(Node<T> node) {
        if(node == null)
            return null;
        update(node);
        switch (node.balancingFactor) {
            case -2:
                if(node.left.balancingFactor > 0) {
                    node.left = leftRotation(node.left);
                }
                node = rightRotation(node);
                break;
            case 2:
                if(node.right.balancingFactor < 0) {
                    node.right = rightRotation(node.right);
                }
                node = leftRotation(node);
                break;
        }
        return node;
    }

    private Node<T> leftRotation(Node<T> node) {
        Node<T> nextRoot = node.right;
        node.right = nextRoot.left;
        nextRoot.left = node;
        update(node);
        update(nextRoot);
        return nextRoot;
    }

    private Node<T> rightRotation(Node<T> node) {
        Node<T> nextRoot = node.left;
        node.left = nextRoot.right;
        nextRoot.right = node;
        update(node);
        update(nextRoot);
        return nextRoot;
    }

    private void update(Node<T> node) {
        if(node == null) return;
        int leftNodeHeight = node.left == null ? -1 : node.left.height;
        int rightNodeHeight = node.right == null ? -1 : node.right.height;

        node.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);

        node.balancingFactor = rightNodeHeight - leftNodeHeight;
    }

    public static class Node<T extends Comparable<T>> implements TreePrinter.PrintableNode {

        int balancingFactor = 0, height = 0;
        T value = null;
        Node<T> left = null, right = null;

        public Node() { }

        public Node(T data) {
            this.value = data;
        }

        public Node(T data, Node<T> left, Node<T> right) {
            this.value = data;
            this.left = left;
            this.right = right;
        }

        @Override
        public TreePrinter.PrintableNode getLeft() {
            return this.left;
        }

        @Override
        public TreePrinter.PrintableNode getRight() {
            return this.right;
        }

        @Override
        public String getText() {
            return this.value == null ? "null" : this.value.toString();
        }
    }

    @Override
    public String toString() {
        return TreePrinter.getTreeDisplay(root);
    }

    // Make sure all left child nodes are smaller in value than their parent and
    // make sure all right child nodes are greater in value than their parent.
    // (Used only for testing)
    public boolean validateBSTInvarient(Node<T> node) {
        if (node == null) return true;
        T val = node.value;
        boolean isValid = true;
        if (node.left != null) isValid = isValid && node.left.value.compareTo(val) < 0;
        if (node.right != null) isValid = isValid && node.right.value.compareTo(val) > 0;
        return isValid && validateBSTInvarient(node.left) && validateBSTInvarient(node.right);
    }
}
