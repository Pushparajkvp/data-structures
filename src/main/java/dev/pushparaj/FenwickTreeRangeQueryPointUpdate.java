package dev.pushparaj;

public class FenwickTreeRangeQueryPointUpdate {

    final int N;
    private long[] tree;

    public FenwickTreeRangeQueryPointUpdate(int sz) {
        if(sz < 0) throw new IllegalArgumentException("Size cannot be negative");
        tree = new long[( N = sz + 1 )]; // Because 0 is not used
    }

    public FenwickTreeRangeQueryPointUpdate(long[] values) {

        if(values == null) throw new IllegalArgumentException("Values array cannot be null");

        N = values.length;
        values[0] = 0;

        tree = values.clone();

        for(int it = 1; it < N; it++){
            int parent = it + lsb(it);
            if(parent < N)
                tree[parent] += tree[it];
        }
    }

    public long sum(int left, int right) {
        if(left > right) throw new IllegalArgumentException("Left cannot be greater than right");
        return prefixSum(right) - prefixSum(left - 1);
    }

    public long get(int i) {
        return sum(i, i);
    }

    public void add(int i, long v) {
        while(i < N){
            tree[i] += v;
            i += lsb(i);
        }
    }

    public void set(int i, long v){
        add(i, v - sum(i,i)); // since we can only add we must add the difference to old value
    }

    private static int lsb(int i) {
        return i & -i;
    }

    private long prefixSum(int i) {
        long sum = 0;
        while (i != 0) {
            sum += tree[i];
            i &= ~lsb(i); // i -= lsb(i);
        }
        return sum;
    }

    @Override
    public String toString() {
        return java.util.Arrays.toString(tree);
    }
}
