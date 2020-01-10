package dev.pushparaj;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;


@SuppressWarnings("unchecked")
public abstract class HashTableOpenAddressingBase<K, V> implements Iterable<K> {

    protected double loadFactor;
    protected int threshold, capacity;

    protected int usedBucketCount, keysCount, modificationCount;

    protected K[] keysTable;
    protected V[] valuesTable;

    protected final K THOMBSTONE = (K)(new Object());

    private static final double DEFAULT_LOAD_FACTOR = 0.66;
    private static final int DEFAULT_CAPACITY = 7;

    protected HashTableOpenAddressingBase() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    protected HashTableOpenAddressingBase(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    protected HashTableOpenAddressingBase(int capacity, double loadFactor) {
        if(capacity < 0)
            throw new IllegalArgumentException("Capacity cannot be negative provided value : " + capacity);
        if(loadFactor <= 0 || Double.isInfinite(loadFactor) || Double.isNaN(loadFactor))
            throw new IllegalArgumentException("Invalid load factor provided value : " + loadFactor);

        this.capacity = Math.max(capacity, DEFAULT_CAPACITY);
        adjustCapacity();
        this.loadFactor = loadFactor;
        this.threshold = (int)(capacity * loadFactor);

        keysTable =(K[]) new Object[this.capacity];
        valuesTable = (V[]) new Object[this.capacity];

        keysCount = usedBucketCount = modificationCount = 0;
    }

    protected abstract void setupProbing(K key);
    protected abstract int probe(int x);
    protected abstract void adjustCapacity();

    protected void increaseCapacity() {
        this.capacity = (this.capacity * 2) + 1;
    }

    public void clear() {
        for(int it = 0; it < this.capacity; it++) {
            keysTable[it] = null;
            valuesTable[it] = null;
        }
        usedBucketCount = keysCount = 0;
        modificationCount++;
    }

    public int size() {
        return this.keysCount;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public boolean isEmpty() {
        return keysCount == 0;
    }

    public V put(K key, V value) {
        return insert(key, value);
    }

    public V add(K key, V value) {
        return insert(key, value);
    }

    public boolean containsKey(K key) {
        return hasKey(key);
    }

    public List<K> keys() {
        List<K> keysList = new ArrayList<>();

        for(int it = 0; it < capacity; it++) {
            if(keysTable[it] != null && keysTable[it] != THOMBSTONE)
                keysList.add(keysTable[it]);
        }

        return keysList;
    }

    public List<V> values() {
        List<V> valuesList = new ArrayList<>();

        for(int it = 0; it < capacity; it++) {
            if(keysTable[it] != null && keysTable[it] != THOMBSTONE)
                valuesList.add(valuesTable[it]);
        }

        return valuesList;
    }

    protected void resizeTable() {
        increaseCapacity();
        adjustCapacity();

        this.threshold = (int)(capacity * loadFactor);

        K[] oldKeysTable = (K[]) new Object[this.capacity];
        V[] oldValuesTable = (V[]) new Object[this.capacity];

        K[] keysTableTemp = keysTable;
        keysTable = oldKeysTable;
        oldKeysTable = keysTableTemp;

        V[] valuesTableTemp = valuesTable;
        valuesTable = oldValuesTable;
        oldValuesTable = valuesTableTemp;

        keysCount = usedBucketCount = 0;

        for(int it = 0; it < oldValuesTable.length; it++) {

            if(oldKeysTable[it] != null && oldKeysTable[it] != THOMBSTONE)
                insert(oldKeysTable[it], oldValuesTable[it]);

            oldKeysTable[it] = null;
            oldValuesTable[it] = null;
        }

    }

    protected final int normalizeIndex(int keyHash) {
        return (keyHash & 0x0FFFFFFF) % this.capacity;
    }

    protected static int gcd(int a, int b) {
        if(b == 0) return a;
        return gcd(b, a % b);
    }

    public V insert(K key, V val) {
        if(key == null) throw new IllegalArgumentException("Key cannot be null");

        if(usedBucketCount >= threshold) resizeTable();

        setupProbing(key);

        int offset = normalizeIndex(key.hashCode());

        for(int it = offset,firstThombstoneIndex = -1, x = 1; ; it = normalizeIndex(offset + probe(x++))) {

            if (keysTable[it] == THOMBSTONE) {
                if(firstThombstoneIndex == -1)
                    firstThombstoneIndex = it;
            }else if(keysTable[it] == null) {
                if(firstThombstoneIndex == -1) {
                    keysTable[it] = key;
                    valuesTable[it] = val;
                    keysCount++;
                    usedBucketCount++;
                } else {
                    keysTable[firstThombstoneIndex] = key;
                    valuesTable[firstThombstoneIndex] = val;
                    keysCount++;
                }

                modificationCount++;
                return null;
            } else {

                if(keysTable[it].equals(key)) {

                    V oldValue = valuesTable[it];

                    if(firstThombstoneIndex == -1) {
                        valuesTable[it] = val;
                    } else {
                        keysTable[it] = THOMBSTONE;
                        valuesTable[it] = null;
                        keysTable[firstThombstoneIndex] = key;
                        valuesTable[firstThombstoneIndex] = val;
                    }

                    modificationCount++;
                    return oldValue;
                }
            }
        }
    }

    public boolean hasKey(K key) {
        if(key == null) throw new IllegalArgumentException("Key cannot be null");

        setupProbing(key);
        int offset = normalizeIndex(key.hashCode());

        for(int it = offset, x = 1, firstThombstoneIndex = -1; ; it = normalizeIndex(offset + probe(x++))) {

            if(keysTable[it] == null) {
                return false;
            } else if (keysTable[it] == THOMBSTONE) {
                if(firstThombstoneIndex == -1) firstThombstoneIndex = it;
            } else {

                if(keysTable[it].equals(key)) {
                    if(firstThombstoneIndex != -1) {
                        keysTable[firstThombstoneIndex] = keysTable[it];
                        valuesTable[firstThombstoneIndex] = valuesTable[it];
                        keysTable[it] = THOMBSTONE;
                        valuesTable[it] = null;
                    }
                    return true;
                }
            }
        }
    }

    public V get(K key) {
        if(key == null) throw new IllegalArgumentException("Key cannot be null");

        setupProbing(key);

        int offset = normalizeIndex(key.hashCode());

        for(int it = offset, x = 1, firstThombstoneIndex = -1; ; it = normalizeIndex(offset + probe(x++))) {

            if(keysTable[it] == null) {
                return valuesTable[it];
            } else if (keysTable[it] == THOMBSTONE) {
                if(firstThombstoneIndex == -1) firstThombstoneIndex = it;
            } else {

                if(keysTable[it].equals(key)) {
                    if(firstThombstoneIndex != -1) {
                        keysTable[firstThombstoneIndex] = keysTable[it];
                        valuesTable[firstThombstoneIndex] = valuesTable[it];
                        keysTable[it] = THOMBSTONE;
                        valuesTable[it] = null;
                        return valuesTable[firstThombstoneIndex];
                    }
                    return valuesTable[it];
                }
            }
        }
    }

    public V remove(K key) {
        if(key == null) throw new IllegalArgumentException("Key cannot be null");

        setupProbing(key);
        int offset = normalizeIndex(key.hashCode());

        for(int it = offset, x = 1; ; it = normalizeIndex(offset + probe(x++))) {

            if(keysTable[it] == null) {
                return null;
            } else if (keysTable[it] != THOMBSTONE) {

                if(keysTable[it].equals(key)) {
                    keysCount--;
                    modificationCount++;
                    V value = valuesTable[it];
                    keysTable[it] = THOMBSTONE;
                    valuesTable[it] = null;
                    return value;
                }
            }
        }

    }

    @Override
    public Iterator<K> iterator() {
        final int MODIFICATION_COUNT = modificationCount;

        return new Iterator<K>() {
            int keysRemaining = keysCount, index = 0;

            @Override
            public boolean hasNext() {
                if(MODIFICATION_COUNT != modificationCount) throw new ConcurrentModificationException("HashTable modified concurrently");
                return keysRemaining != 0;
            }

            @Override
            public K next() {
                while (keysTable[index] == null || keysTable[index] == THOMBSTONE) index++;
                keysRemaining--;
                return keysTable[index];
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        for (int i = 0; i < capacity; i++)
            if (keysTable[i] != null && keysTable[i] != THOMBSTONE) sb.append(keysTable[i] + " => " + valuesTable[i] + ", ");
        sb.append("}");

        return sb.toString();
    }
}
