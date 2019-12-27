package dev.pushparaj;


import java.util.Arrays;
import java.util.Iterator;

public class DynamicArray<T> implements Iterable<T> {

    private int size = 0, capacity = 0, initialCapacity = 0;
    private T[] arr;

    public DynamicArray(){
        this(16);
    }

    public DynamicArray(int size){
        if(size < 0)
            throw new IllegalArgumentException("Negative Size");
        this.capacity = size;
        this.initialCapacity = size;
        this.arr = (T[]) new Object[this.capacity];
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size <= 0;
    }

    public T get(int index){
        if(index >= size || index < 0)
            throw new IllegalArgumentException("Invalid Index");
        return arr[index];
    }

    public void set(int index, T element){
        if(index >= size || index < 0)
            throw new IndexOutOfBoundsException("Invalid Index");
        arr[index] = element;
    }

    public void clear(){
        this.capacity = this.initialCapacity;
        this.size = 0;
        this.arr = (T[]) new Object[this.capacity];
    }

    public void add(T element){
        if(size >= capacity){
            if(capacity == 0)
                capacity = 1;
            else
                capacity *= 2;

            this.arr = Arrays.copyOf(this.arr, capacity);
        }
        this.arr[size++] = element;
    }

    public boolean remove(T element){
        int index = indexOf(element);
        if(index == -1)
            return false;
        return removeAt(index);
    }

    public boolean removeAt(int index){
        if(index >= size || index < 0)
            return false;
        T[] newArr =(T[]) new Object[capacity];

        for(int i=0,j=0; i < this.size; i++,j++){
            if(i == index)
                j--;
            else
                newArr[j] = this.arr[i];
        }
        this.arr = newArr;
        this.size--;
        return true;
    }

    public boolean contains(T element){
        return indexOf(element) >= 0;
    }

    public int indexOf(T element){
        for(int i = 0; i < this.size; i++){
            if(this.arr[i] == element)
                return i;
        }
        return -1;
    }


    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ ");
        for(int i = 0; i < this.size; i++){
            if(i != this.size -1)
                stringBuilder.append(this.arr[i].toString()).append(" ,");
            else
                stringBuilder.append(this.arr[i].toString());
        }
        stringBuilder.append(" ]");
        return stringBuilder.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < capacity;
            }

            @Override
            public T next() {
                return get(index++);
            }
        };
    }
}
