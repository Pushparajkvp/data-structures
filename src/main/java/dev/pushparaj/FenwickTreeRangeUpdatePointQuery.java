package dev.pushparaj;

public class FenwickTreeRangeUpdatePointQuery {

    private final int N;

    private long[] currentTree, unmodifiedTree;

    public FenwickTreeRangeUpdatePointQuery(int size) {
        if(size < 0) throw new IllegalArgumentException("Size cannot be negative");
        N = size;
        currentTree = new long[N];
        unmodifiedTree = new long[N];
    }

    public FenwickTreeRangeUpdatePointQuery(long[] values) {
        if(values == null) throw new IllegalArgumentException("Values cannot be null");

        N = values.length;
        values[0] = 0;

        currentTree = values.clone();

        for(int it = 1; it < N; it++) {
            int parent = it + lsb(it);
            if(parent < N)
                currentTree[parent] += currentTree[it];
        }

        this.unmodifiedTree = currentTree.clone();
    }

    public void updateRange(int left, int right, long val) {
        add(left, val);
        add(right + 1, -val);
    }

    private void add(int i, long value) {
        while (i < N) {
            currentTree[i] += value;
            i += lsb(i);
        }
    }

    public long get(int i) {
        return prefixSum(i, currentTree) - prefixSum(i - 1, unmodifiedTree);
    }

    private long prefixSum(int i, long[] tree) {
        long sum = 0;
        while (i != 0) {
            sum += tree[i];
            i -= lsb(i);
        }
        return sum;
    }

    private static int lsb(int n) {
        return n & -n;
    }
}
