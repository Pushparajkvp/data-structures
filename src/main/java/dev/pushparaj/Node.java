package dev.pushparaj;

public class Node {

    static final int INFINITY = Integer.MAX_VALUE;

    int maxPos = 0, minPos = 0, lazy = 0, min = 0, sum = 0;

    Node left = null, right = null;

    public Node(int[] values) {
        if(values == null) throw new IllegalArgumentException("Values cannot be null");
        buildTree(0, values.length);

        for(int it = 0; it < values.length; it++)
            update(it, it + 1, values[it]);
    }

    public Node(int size) {
        if(size < 0) throw new IllegalArgumentException("Size cannot be negative");

        buildTree(0, size);
    }

    public Node(int left, int right) {
        buildTree(left, right);
    }

    private void buildTree(int left, int right) {
        if(left < 0 || right < 0 || left > right) throw new IllegalArgumentException("Left and right cannot be negative and left must be smaller than right");

        minPos = left;
        maxPos = right;

        if(left == right - 1) {
            this.left = this.right = null;
        } else {
            int mid = (left + right) / 2;
            this.left = new Node(left, mid);
            this.right = new Node(mid, right);
        }
    }

    public void update(int left, int right, int value) {
        propagate();

        if(left <= minPos && right >= maxPos) {
            sum += value * (maxPos - minPos);
            min += value;

            if(this.left != null) this.left.lazy += value;
            if(this.right != null) this.right.lazy += value;
        } else if (left >= maxPos || right <= minPos) {

        } else {

            if(this.left != null) this.left.update(left, right, value);
            if(this.right != null) this.right.update(left, right, value);

            sum = (this.left != null ? this.left.sum : 0) + (this.right != null ? this.right.sum : 0);
            min = Math.min(this.left != null ? this.left.min : INFINITY, this.right != null ? this.right.min : INFINITY);
        }
    }

    public int sum(int left, int right) {
        propagate();

        if(left <= minPos && right >= maxPos)
            return sum;

        if(left >= maxPos || right <= minPos)
            return 0;

        return (this.left != null ? this.left.sum(left, right) : 0) + (this.right != null ? this.right.sum(left, right) : 0);
    }

    public int min(int left, int right) {
        propagate();

        if(left <= minPos && right >= maxPos)
            return min;

        if(left >= maxPos || right <= minPos)
            return INFINITY;

        return Math.min(this.left != null ? this.left.min(left, right): INFINITY, this.right != null ? this.right.min(left,right) : INFINITY);
    }

    private void propagate() {

        if(lazy != 0) {
            sum += lazy * (maxPos - minPos);
            min += lazy;

            if(this.left != null) this.left.lazy = lazy;
            if(this.right != null) this.right.lazy = lazy;

            lazy = 0;
        }
    }
}
