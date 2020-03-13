package dev.pushparaj;

public class RedBlackTree<T extends Comparable> {

    public Node<T> root = null;

    private int nodesCount = 0;

    public RedBlackTree(){}

    public void insert(T data) {
        if(data == null)
            return;

        if(root == null) {
            root = new Node<T>(data);
            return;
        }
        root = insert(data, root);
    }

    private Node<T> insert(T data, Node<T> node) {
        int compareValue = node.data.compareTo(data);

        if(compareValue == 0)
            return node;

        if(compareValue > 0) {
            if(node.left == null)
                node.left = new Node<T>(data, Color.RED);
            else
                node.left = insert(data, node.left);
        } else {
            if(node.right == null)
                node.right = new Node<T>(data, Color.RED);
            else
                node.right = insert(data, node.right);
        }

        return node;
    }

    private Node<T> leftRotate(Node<T> pivot){
        Node<T> successorNode = pivot.right;
        pivot.right = successorNode.left;
        successorNode.left = pivot;
        return successorNode;
    }

    private Node<T> rightRotate(Node<T> pivot) {
        Node<T> successorNode = pivot.left;
        pivot.left = successorNode.right;
        successorNode.right = pivot;
        return successorNode;
    }

    public boolean contains(T data) {
        Node<T> trav = root;
        while (trav != null) {
            int compareValue = trav.data.compareTo(data);
            if(compareValue == 0)
                return true;
            if(compareValue > 0)
                trav = trav.left;
            else
                trav = trav.right;
        }
        return false;
    }

    public static class Node<T extends Comparable> {

        public Color color = Color.BLACK;

        public Node<T> left = null, right = null, parent = null;

        public  T data;

        public Node(T data) {
            this.data = data;
        }

        public Node(T data, Color color) {
            this.data = data;
            this.color = color;
        }

        public Node(T data, Node<T> left, Node<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    public enum Color{
        BLACK, RED, DOUBLE_BLACK
    }
}
