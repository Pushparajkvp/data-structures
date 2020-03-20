package dev.pushparaj;

public class RedBlackTree<T extends Comparable> {

    public Node<T> root = null;

    private int nodesCount = 0;

    public RedBlackTree(){}

    public boolean insert(T data) {
        if(data == null)
            throw new IllegalArgumentException("Value cannot be null");

        if(root == null) {
            root = new Node<T>(data, null);
            nodesCount++;
            return true;
        }

        Node<T> trav = root, newNode = new Node<>(data, null, Color.RED);

        while (true) {
            int compareValue = trav.value.compareTo(data);
            if(compareValue > 0) {
                if(trav.left != null)
                    trav = trav.left;
                else {
                    trav.left = newNode;
                    newNode.parent = trav;
                    break;
                }
            }
            else if(compareValue < 0) {
                if(trav.right != null)
                    trav = trav.right;
                else {
                    trav.right = newNode;
                    newNode.parent = trav;
                    break;
                }
            } else {
                return false;
            }
        }
        balance(newNode);
        nodesCount++;
        return true;
    }

    public int height() {
        return height(root);
    }

    private int height(Node<T> node) {
        if(node == null)
            return 0;
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    public int size() {
        return nodesCount;
    }

    private void balance(Node<T> node) {
        if(node.color == Color.BLACK)
            return;
        Node<T> parent = node.parent;
        if(parent == null) //Root node case
            return;
        if(parent.color == Color.BLACK)
            return;
        Node<T> grandParent = parent.parent;
        if(grandParent == null) {
            parent.color = Color.BLACK;
            return;
        }
        Node<T> uncle = grandParent.left == parent ? grandParent.right : grandParent.left;
        if(uncle == null || uncle.color == Color.BLACK) {
            if(grandParent.right == parent) {
                if(parent.left == node){
                    rightRotate(parent);
                    leftRotate(grandParent);
                    swapColors(grandParent, node);
                } else {
                    leftRotate(grandParent);
                    swapColors(grandParent, parent);
                }
            } else {
                if(parent.right == node) {
                    leftRotate(parent);
                    rightRotate(grandParent);
                    swapColors(grandParent, node);
                } else {
                    rightRotate(grandParent);
                    swapColors(grandParent, parent);
                }
            }
        } else {
            uncle.color = Color.BLACK;
            parent.color = Color.BLACK;
            grandParent.color = grandParent == root ? Color.BLACK : Color.RED;
            balance(grandParent);
        }
    }



    private void leftRotate(Node<T> pivot){
        //Identify successor
        Node<T> successorNode = pivot.right;
        if(successorNode == null)
            return;

        //Map child of successor
        if(successorNode.left != null)
            successorNode.left.parent = pivot;
        pivot.right = successorNode.left;

        //Map successor to pivot
        successorNode.left = pivot;
        successorNode.parent = pivot.parent;
        pivot.parent = successorNode;

        //Fix parent ref
        if(successorNode.parent != null) {
            if(successorNode.parent.left == pivot)
                successorNode.parent.left = successorNode;
            else
                successorNode.parent.right = successorNode;
        } else {
            root = successorNode;
        }
    }

    private void rightRotate(Node<T> pivot) {
        //Identify successor
        Node<T> successorNode = pivot.left;
        if(successorNode == null)
            return;

        //Map child of successor
        if(successorNode.right != null)
            successorNode.right.parent = pivot;
        pivot.left = successorNode.right;

        //Map successor to pivot
        successorNode.right = pivot;
        successorNode.parent = pivot.parent;
        pivot.parent = successorNode;

        //Fix parent ref
        if(successorNode.parent != null) {
            if(successorNode.parent.left == pivot)
                successorNode.parent.left = successorNode;
            else
                successorNode.parent.right = successorNode;
        } else {
            root = successorNode;
        }
    }

    private void swapColors(Node<T> node1, Node<T> node2) {
        if(node1 == null || node2 == null)
            return;
        if(node1 == root) {
            node2.color = node1.color;
            node1.color = Color.BLACK;
        } else if(node2 == root) {
            node1.color = node2.color;
            node2.color = Color.BLACK;
        } else {
            Color temp = node1.color;
            node1.color = node2.color;
            node2.color = temp;
        }

    }

    public boolean contains(T data) {
        Node<T> trav = root;
        while (trav != null) {
            int compareValue = trav.value.compareTo(data);
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

        public  T value;

        public Node(T data, Node<T> parent) {
            this.value = data;
            this.parent = parent;
        }

        public Node(T data, Node<T> parent, Color color) {
            this.value = data;
            this.color = color;
            this.parent = parent;
        }

        public Node(T data,Node<T> parent, Node<T> left, Node<T> right) {
            this.value = data;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    public enum Color{
        BLACK, RED, DOUBLE_BLACK
    }
}
