package dev.pushparaj;

import java.util.ArrayList;
import java.util.List;


public class BinaryHeap<T extends Comparable<T>> {

    private List<T> arrayList = null;

    public BinaryHeap(){
        this(16);
    }

    public BinaryHeap(int size){
        arrayList = new ArrayList<>(size);
    }

    public BinaryHeap(T[] elements){
        arrayList = new ArrayList<>(elements.length);

        // Priority queues does not allow null values
        for(T element : elements){
            if(element != null)
                arrayList.add(element);
        }

        //Heapify process to convert heap to min heap
        for(int j=Math.max(0,(elements.length / 2) - 1); j >= 0; j--) sink(j);
    }

    public boolean isEmpty() {
        return arrayList.isEmpty();
    }

    public T peek() {
        if(isEmpty())
            return null;
        return arrayList.get(0);
    }

    public T poll() {
        return removeAt(0);
    }

    public boolean contains(T elementToFind) {
        for(T element : arrayList)
            if(elementToFind.equals(element))
                return true;
        return false;
    }

    public void clear() {
        arrayList.clear();
    }

    public void add(T element) {
        if(element == null)
            throw new IllegalArgumentException("Null cannot be inserted into a binary heap");

        arrayList.add(element);
        swim(arrayList.size() - 1);
    }

    public boolean remove(T element) {
        if(element == null)
            throw new IllegalArgumentException("Null cannot be present in a binary heap");

        int index;
        for(index = 0; index < arrayList.size(); index++){
            if(arrayList.get(index).equals(element)){
                removeAt(index);
                return true;
            }
        }
        return false;
    }

    public boolean isMinHeap(int index) {
        if(index >= arrayList.size() / 2) return true; // Leaf Node

        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        if(leftChild < arrayList.size() && !isLesser(index, leftChild)) return false;

        if(rightChild < arrayList.size() && !isLesser(index, rightChild)) return false;

        return isMinHeap(leftChild) && isMinHeap(rightChild);
    }

    public int size(){
        return arrayList.size();
    }

    private T removeAt(int index) {
        if(isEmpty())
            return null;

        if(index < 0 || index >= arrayList.size())
            throw new IndexOutOfBoundsException("Invalid Index Provided For removeAt : " + index);

        T removedElement = arrayList.get(index);

        if(index == 0){
            swap(index, arrayList.size() - 1);
            arrayList.remove(arrayList.size() - 1);
            if(index == arrayList.size()) return removedElement;
            sink(index);
            return removedElement;
        }

        swap(index, arrayList.size() - 1);
        arrayList.remove(arrayList.size() - 1);

        if(index == arrayList.size())
            return removedElement;

        int parent = (index - 1) / 2;

        if(isLesser(index, parent)){
            swim(index);
        } else if(index < arrayList.size()/2){
            // Non leaf nodes
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;

            if(leftChild < arrayList.size() && isLesser(leftChild, index) || rightChild < arrayList.size() && isLesser(rightChild, index))
                sink(index);

        }

        return removedElement;
    }

    private void sink(int index) {
        if(isEmpty())
            return;

        if(index < 0 || index >= arrayList.size())
            throw new IndexOutOfBoundsException("Invalid Index Provided For Sink : " + index);

        while (true){

            if(index >= arrayList.size()/2) break; // Leaf node

            int left = 2 * index + 1;
            int right = 2 * index + 2;

            int smallest = left;

            if(right < arrayList.size() && isLesser(right, left)) smallest = right;

            if(smallest >= arrayList.size() || isLesser(index, smallest)) break;

            swap(index, smallest);

            index = smallest;
        }

    }

    private void swim(int index) {
        if(isEmpty())
            return;

        if(index < 0 || index >= arrayList.size())
            throw new IndexOutOfBoundsException("Invalid Index Provided For Swim");

        while (index > 0) {

            int parent = (index - 1) / 2;

            if(isLesser(parent, index)) break;

            swap(parent, index);

            index = parent;
        }

    }

    private boolean isLesser(int firstElementIndex, int secondElementIndex) {
        if(firstElementIndex < 0 || firstElementIndex >= arrayList.size() || secondElementIndex < 0 || secondElementIndex >= arrayList.size())
            throw new IndexOutOfBoundsException("Invalid Index Provided For isLesser first : " + firstElementIndex + " second : " + secondElementIndex);

        T firstElement = arrayList.get(firstElementIndex);
        T secondElement = arrayList.get(secondElementIndex);

        return firstElement.compareTo(secondElement) <= 0;
    }

    private void swap(int firstElementIndex, int secondElementIndex) {
        if(firstElementIndex < 0 || firstElementIndex >= arrayList.size() || secondElementIndex < 0 || secondElementIndex >= arrayList.size())
            throw new IndexOutOfBoundsException("Invalid Index Provided For Swap");

        T temp = arrayList.get(firstElementIndex);
        arrayList.set(firstElementIndex, arrayList.get(secondElementIndex));
        arrayList.set(secondElementIndex, temp);
    }


    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ ");
        for(int i = 0; i < arrayList.size(); i++){
            if(i != arrayList.size() -1)
                stringBuilder.append(arrayList.get(i).toString()).append(" ,");
            else
                stringBuilder.append(arrayList.get(i).toString());
        }
        stringBuilder.append(" ]");
        return stringBuilder.toString();
    }
}

