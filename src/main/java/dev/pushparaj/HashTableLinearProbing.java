package dev.pushparaj;

public class HashTableLinearProbing<K, V> extends HashTableOpenAddressingBase<K, V>{

    public static final int PROBING_CONSTANT = 17;

    public HashTableLinearProbing() {
        super();
    }

    public HashTableLinearProbing(int capacity) {
        super(capacity);
    }

    public HashTableLinearProbing(int capacity, double loadFactor) {
        super(capacity, loadFactor);
    }

    @Override
    protected void setupProbing(K key) { }

    @Override
    protected int probe(int x) {
        return PROBING_CONSTANT * x;
    }

    @Override
    protected void adjustCapacity() {
        while (gcd(this.capacity, PROBING_CONSTANT) != 1) this.capacity++;
    }
}
