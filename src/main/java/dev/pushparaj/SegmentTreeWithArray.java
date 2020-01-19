package dev.pushparaj;

public class SegmentTreeWithArray {

    long tree[] = null;
    int treeSize = 0, arraySize = 0;

    public SegmentTreeWithArray(long[] arr) {
        arraySize = arr.length;
        treeSize = (int) Math.pow(arraySize, 2) - 1;
        tree = new long[treeSize];
    }

    private void modify(int index, long value) {
        if(index < 0 || index >= treeSize) throw new IndexOutOfBoundsException("Invalid Index + " + index);
    }
}
