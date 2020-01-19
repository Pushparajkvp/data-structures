package dev.pushparaj;

import java.util.Arrays;

public class CompactSegmentTree {

    private int N;

    private long UNIQUE_VALUE = 942353125671234567L;

    private long[] tree;

    public CompactSegmentTree(int size) {
        tree = new long[2 * (N = size)];
        Arrays.fill(tree, UNIQUE_VALUE);
    }

    public CompactSegmentTree(long[] values) {
        this(values.length);

        for(int i = 0; i < N; i++)
            modify(i, values[i]);
    }

    private long function(long v1, long v2) {
        if(v1 == UNIQUE_VALUE)
            return v2;
        if(v2 == UNIQUE_VALUE)
            return v1;

        return v1 < v2 ? v1 : v2; // Replace this with need operation
    }

    public void modify(int index, long value) {
        tree[index + N] = function(tree[index + N], value);
        for(index += N; index > 1; index >>= 1)
            tree[index >> 1] = function(tree[index], tree[index ^ 1]);
    }

    public long query(int left, int right) {
        long result = UNIQUE_VALUE;

        for(left += N, right += N; left < right; left >>= 1, right >>= 1) {
            if((left & 1) != 0) result = function(result, tree[left++]);
            if((right & 1) != 0) result = function(result, tree[right--]);
        }

        if(result == UNIQUE_VALUE)
            throw new IllegalStateException("Illegal state");

        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < tree.length; i++)
            stringBuilder.append("Index : ").append(i).append(" Value : ").append(tree[i]).append(",");
        return stringBuilder.toString();
    }
}
