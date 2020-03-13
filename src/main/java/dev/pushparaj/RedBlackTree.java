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
        //Identify successor
        Node<T> successorNode = pivot.right;
        if(successorNode == null)
            return pivot;

        //Map child of successor
        if(successorNode.left != null)
            successorNode.left.parent = pivot;
        pivot.right = successorNode.left;

        //Map successor to pivot
        successorNode.left = pivot;
        successorNode.parent = pivot.parent;
        pivot.parent = successorNode;

        return successorNode;
    }

    private Node<T> rightRotate(Node<T> pivot) {
        //Identify successor
        Node<T> successorNode = pivot.left;
        if(successorNode == null)
            return pivot;

        //Map child of successor
        if(successorNode.right != null)
            successorNode.right.parent = pivot;
        pivot.left = successorNode.right;

        //Map successor to pivot
        successorNode.right = pivot;
        successorNode.parent = pivot.parent;
        pivot.parent = successorNode;

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

        public Node(T data, Node<T> parent) {
            this.data = data;
            this.parent = parent;
        }

        public Node(T data, Node<T> parent, Color color) {
            this.data = data;
            this.color = color;
            this.parent = parent;
        }

        public Node(T data,Node<T> parent, Node<T> left, Node<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    public enum Color{
        BLACK, RED, DOUBLE_BLACK
    }
}
