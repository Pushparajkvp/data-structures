package dev.pushparaj;

import java.util.Iterator;

public class SinglyLinkedList<T> implements Iterable<T> {

    private int size = 0;
    private Node<T> head = null, tail = null;

    public SinglyLinkedList(){}

    // Add Operations
    public void add(T element){
        addLast(element);
    }

    public void addLast(T element){
        if(tail == null){
            tail = new Node<>(element, null);
            head = tail;
        } else {
            Node<T> newNode = new Node<>(element, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public void addFirst(T element){
        if(head == null){
            head = new Node<>(element, null);
            tail = head;
        } else {
            head = new Node<>(element, head);
        }
        size++;
    }

    public void addAt(int index,T element){
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException("Invalid Index");

        if(index == 0) {
            addFirst(element);
            return;
        }
        if(index == size){
            addLast(element);
            return;
        }

        Node<T> temp = head;
        for(int i=0; i < index - 1; i++){
            temp = temp.next;
        }

        temp.next = new Node<>(element, temp.next);

        size++;
    }

    //Remove Operations
    public T removeFirst(){
        if(isEmpty())
            throw new RuntimeException("List is empty");
        T data = head.data;
        if(tail == head){
            tail.data = null;
            tail.next = null;
            tail = head = null;
        } else {
            Node<T> nextHead = head.next;
            head.next = null;
            head.data = null;
            head = nextHead;
        }
        size--;
        if(size == 0){
            head = tail = null;
        }
        return data;
    }

    public T removeLast(){
        if(isEmpty())
            throw new RuntimeException("List is empty");
        T data = tail.data;
        if(tail == head){
            tail.data = null;
            tail.next = null;
            tail = head = null;
        } else {
            Node<T> firstIterator = head;
            Node<T> secondIterator = head.next;
            while (secondIterator != tail){
                firstIterator = firstIterator.next;
                secondIterator = secondIterator.next;
            }
            tail = firstIterator;
            tail.next = null;
            secondIterator.data = null;
            secondIterator.next = null;
        }
        size--;
        if(size == 0){
            head = tail = null;
        }
        return data;
    }

    public T removeAt(int index){
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Invalid Index");

        if(index == 0)
            return removeFirst();
        if(index == size-1)
            return removeLast();


        Node<T> prev = head;
        Node<T> trav = head.next;

        for(int i=1; i < index ; i++){
            prev = prev.next;
            trav = trav.next;
        }

        return remove(prev, trav);
    }

    private T remove(Node<T> prev, Node<T> toDelete){
        if(prev == null || toDelete == null)
            throw new IllegalArgumentException("Invalid Parameters");

        prev.next = toDelete.next;
        T data = toDelete.data;
        toDelete.next = null;
        toDelete.data = null;
        toDelete = null;
        size--;
        if(size == 0){
            head = tail = null;
        }
        return data;
    }

    public boolean remove(T element){

        if(head == null || tail == null)
            return false;
        if(element == null){
            if(head.data == null){
                removeFirst();
                return true;
            }
            if(head != tail){
                Node<T> prev = head;
                Node<T> trav = head.next;

                while (trav != null){
                    if(trav.data == null){
                        remove(prev, trav);
                        return true;
                    }
                    prev = prev.next;
                    trav = trav.next;
                }
            }
        } else {
            if(element.equals(head.data)){
                removeFirst();
                return true;
            }
            if(head != tail){
                Node<T> prev = head;
                Node<T> trav = head.next;

                while (trav != null){
                    if(element.equals(trav.data)){
                        remove(prev, trav);
                        return true;
                    }
                    prev = prev.next;
                    trav = trav.next;
                }
            }
        }
        return false;
    }


    public void clear(){
        if(isEmpty())
            return;
        Node<T> trav = head;
        while (trav.next != null){
            Node<T> next = trav.next;
            trav.data = null;
            trav = next;
        }
        head = tail = null;
        size = 0;
    }

    public int indexOf(T element){
        Node<T> trav = head;
        int index = 0;
        if(element == null){
            while (trav != null){
                if(trav.data == null)
                    return index;
                index++;
                trav = trav.next;
            }
        } else {
            while (trav != null){
                if(element.equals(trav.data))
                    return index;
                index++;
                trav = trav.next;
            }
        }
        return -1;
    }

    public boolean contains(T element){
        return indexOf(element) != -1;
    }

    public boolean isEmpty(){
        return head == null;
    }

    public int size(){
        return size;
    }
    //Peek Operations
    public T peekFirst(){
        if(isEmpty())
            throw new RuntimeException("List is empty");
        return head.data;
    }

    public T peekLast(){
        if(isEmpty())
            throw new RuntimeException("List is empty");
        return tail.data;
    }
    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return data == null ? "null" : data.toString();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> trav = head;

            @Override
            public boolean hasNext() {
                return trav != null;
            }

            @Override
            public T next() {
                T data = trav.data;
                trav = trav.next;
                return data;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Node<T> trav = head; trav != null; trav = trav.next) {
            if (trav.next != null) {
                if (trav.data == null) {
                    stringBuilder.append("(null) -> ");
                } else {
                    stringBuilder.append("(").append(trav.data.toString()).append(") -> ");
                }
            } else {
                if (trav.data == null) {
                    stringBuilder.append("(null)");
                } else {
                    stringBuilder.append("(").append(trav.data.toString()).append(")");
                }
            }
        }
        return stringBuilder.toString();
    }
}
