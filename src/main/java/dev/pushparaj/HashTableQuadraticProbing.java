package dev.pushparaj;

public class HashTableQuadraticProbing<K, V> extends HashTableOpenAddressingBase<K, V> {

    public HashTableQuadraticProbing() {
        super();
    }

    public HashTableQuadraticProbing(int capacity) {
        super(capacity);
    }

    public HashTableQuadraticProbing(int capacity, double loadFactor) {
        super(capacity, loadFactor);
    }

    private static int nextPowerOfTwo(int n) {
        return Integer.highestOneBit(n) << 1;
    }

    @Override
    protected void setupProbing(K key) { }

    @Override
    protected int probe(int x) {
        return (int) (Math.pow(x, 2) + x) >> 1;
    }

    @Override
    protected void increaseCapacity() {
        this.capacity = nextPowerOfTwo(this.capacity);
    }

    @Override
    protected void adjustCapacity() {
        int pow = Integer.highestOneBit(this.capacity);
        if(pow == this.capacity) return;
        increaseCapacity();
    }
}
