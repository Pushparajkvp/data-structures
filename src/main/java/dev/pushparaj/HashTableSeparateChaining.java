package dev.pushparaj;

import java.util.*;

/*
 * HashTableSeparateChaining()
 * HashTableSeparateChaining(int capacity)
 * HashTableSeparateChaining(int capacity, double maxLoadFactor)
 * int size()
 * boolean isEmpty()
 * int normalizeIndex(int keyHash)
 * clear()
 * boolean containsKey(K key)
 * boolean hasKey(K key)
 * V put(K key, V value)
 * V add(K key, V value)
 * V insert(K key, V value)
 * V get(K key)
 * V remove(K key)
 * V bucketRemoveEntry(int bucketIndex, K key)
 * V bucketInsertEntry(int bucketIndex, Entry<K, V> entry)
 * Entry<K, V> bucketSeekEntry(int bucketIndex, K key)
 * resizeTable()
 * List<K> keys()
 * List<V> values()
 * java.util.Iterator<K> iterator()
 * String toString()
 * */
class Entry<K, V> {

    int hash;
    K key;
    V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.hash = key.hashCode();
    }

    public boolean equals(Entry<K, V> anotherValue) {
        if(this.hash != anotherValue.hash) return false;
        return this.key.equals(anotherValue.key);
    }

    @Override
    public String toString() {
        return this.key + "=>" + this.value;
    }
}

@SuppressWarnings("unchecked")
public class HashTableSeparateChaining<K, V> implements Iterable<K> {

    public static final int DEFAULT_CAPACITY = 3;
    public static final double DEFAULT_LOAD_FACTOR = 0.75;

    private int capacity, threshold, size;
    private double maxLoadFactor;

    private LinkedList<Entry<K, V>> table[];


    public HashTableSeparateChaining() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashTableSeparateChaining(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    public HashTableSeparateChaining(int capacity, double maxLoadFactor) {
        if(maxLoadFactor <= 0 || Double.isNaN(maxLoadFactor) || Double.isInfinite(maxLoadFactor))
            throw new IllegalArgumentException("Invalid maxLoadFactor : " + maxLoadFactor);
        if(capacity < 0)
            throw new IllegalArgumentException("Capacity cannot be negative : " + capacity);
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = Math.max(capacity, DEFAULT_CAPACITY);
        this.size = 0;
        this.threshold = (int) (this.capacity * this.maxLoadFactor);
        this.table = new LinkedList[this.capacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    public boolean hasKey(K key) {
        return containsKey(key);
    }

    public boolean containsKey(K key) {
        int hashCode = key.hashCode();
        int index = normalizeIndex(hashCode);
        LinkedList<Entry<K, V>> bucket = table[index];
        if(bucket == null) return false;
        for(Entry<K, V> entry : bucket) if(entry.key.equals(key)) return true;
        return false;
    }

    public V put(K key, V value) {
        return insert(key, value);
    }

    public V add(K key, V value) {
        return insert(key, value);
    }

    public V insert(K key, V value) {
        if(key == null)
            throw new IllegalArgumentException("Key cannot be null");
        Entry<K, V> entry = new Entry<>(key, value);
        int index = normalizeIndex(entry.hash);
        return bucketInsertEntry(index, entry);
    }

    public V get(K key) {
        int hashCode = key.hashCode();
        int index = normalizeIndex(hashCode);
        Entry<K, V> entry = bucketSeekEntry(index, key);
        if(entry == null)
            return null;
        return entry.value;
    }

    public V remove(K key) {
        int hashCode = key.hashCode();
        int index = normalizeIndex(hashCode);
        return bucketRemoveEntry(index, key);
    }

    private V bucketRemoveEntry(int bucketIndex, K key) {
        Entry<K, V> entry = bucketSeekEntry(bucketIndex, key);

        if(entry == null) return null;

        LinkedList<Entry<K, V>> bucket = table[bucketIndex];
        bucket.remove(entry);

        if(bucket.isEmpty()) table[bucketIndex] = null;

        size--;
        return entry.value;
    }

    private Entry<K, V> bucketSeekEntry(int bucketIndex, K key) {
        LinkedList<Entry<K, V>> bucket = table[bucketIndex];
        if(bucket == null) return null;
        for(Entry<K, V> entry : bucket) if(entry.key.equals(key)) return entry;
        return null;
    }

    private V bucketInsertEntry(int bucketIndex, Entry<K, V> entry) {
        LinkedList<Entry<K, V>> bucket = table[bucketIndex];
        if(bucket == null){
            bucket = table[bucketIndex] = new LinkedList<>();
        }

        Entry<K, V> existingValue = bucketSeekEntry(bucketIndex, entry.key);

        if(existingValue == null) {
            bucket.addLast(entry);
            if(++size > threshold) resizeTable();
            return null;
        } else {
            V oldValue = existingValue.value;
            existingValue.value = entry.value;
            return oldValue;
        }
    }

    public void resizeTable() {

        this.capacity *= 2;
        this.threshold = (int) (this.capacity * this.maxLoadFactor);

        LinkedList<Entry<K, V>> newTable[] = new LinkedList[this.capacity];

        for(int it = 0; it < this.table.length; it++) {
            if(table[it] != null) {
                LinkedList<Entry<K, V>> bucket = table[it];
                for(Entry<K, V> entry : bucket) {
                    int hashCode = entry.key.hashCode();
                    int index = normalizeIndex(hashCode);

                    LinkedList<Entry<K, V>> newBucket = newTable[index];
                    if(newBucket == null)
                        newBucket = newTable[index] = new LinkedList<>();

                    newBucket.add(entry);
                }
                table[it].clear();
                table[it] = null;
            }
        }
        this.table = newTable;

    }

    public List<K> keys() {
        List<K> keys = new ArrayList<>();

        for(int it = 0; it < table.length; it++) {
            LinkedList<Entry<K, V>> bucket = table[it];
            if(bucket == null) continue;

            for(Entry<K, V> entry : bucket) {
                keys.add(entry.key);
            }
        }
        return keys;
    }

    public List<V> values() {
        List<V> values = new ArrayList<>();

        for(int it = 0; it < table.length; it++) {
            LinkedList<Entry<K, V>> bucket = table[it];
            if(bucket == null) continue;

            for(Entry<K, V> entry : bucket) {
                values.add(entry.value);
            }
        }
        return values;
    }

    private int normalizeIndex(int keyHash) {
        return (keyHash & 0x7FFFFFFF) % capacity;
    }

    @Override
    public Iterator<K> iterator() {

        int sizeBeforeIterator = size;

        return new Iterator<K>() {

            int bucketIndex = 0;
            Iterator<Entry<K, V>> bucketIterator = table[0] == null ? null : table[0].iterator();

            @Override
            public boolean hasNext() {

                if(sizeBeforeIterator != size) throw new ConcurrentModificationException("HashTable Concurrently Modified");

                if (bucketIterator == null || !bucketIterator.hasNext()) {
                    while (++bucketIndex < capacity) {
                        LinkedList<Entry<K, V>> bucket = table[bucketIndex];
                        if(bucket != null) {
                            Iterator<Entry<K, V>> iterator = table[bucketIndex].iterator();
                            if(iterator.hasNext()) {
                                bucketIterator = iterator;
                                break;
                            }
                        }
                    }
                }
                return bucketIndex < capacity;
            }

            @Override
            public K next() {
                return bucketIterator.next().key;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < capacity; i++) {
            if (table[i] == null) continue;
            for (Entry<K, V> entry : table[i]) sb.append(entry + ", ");
        }
        sb.append("}");
        return sb.toString();
    }
}
