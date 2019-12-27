package dev.pushparaj;

import java.util.Iterator;

public class DoublyLinkedList<T> implements Iterable<T> {

    private int size = 0;
    private Node<T> head = null, tail = null;

    public DoublyLinkedList(){}

    // Add Operations
    public void add(T element){
        addLast(element);
    }

    public void addLast(T element){
        if(tail == null){
            tail = new Node<>(element, null, null);
            head = tail;
        } else {
            Node<T> newNode = new Node<>(element, null, tail);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public void addFirst(T element){
        if(head == null){
            head = new Node<>(element, null, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(element, head, null);
            head.prev = newNode;
            head = newNode;
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

        Node<T> newNode = new Node<>(element, temp.next, temp);
        temp.next.prev = newNode;
        temp.next = newNode;

        size++;
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

    //Remove Operations
    public T removeFirst(){
        if(isEmpty())
            throw new RuntimeException("List is empty");
        T data = head.data;
        if(tail == head){
            tail.data = null;
            tail.next = tail.prev = null;
            tail = head = null;
        } else {
            Node<T> nextHead = head.next;
            head.next = head.prev = null;
            head.data = null;
            head = nextHead;
            head.prev = null;
        }
        size--;
        return data;
    }

    public T removeLast(){
        if(isEmpty())
            throw new RuntimeException("List is empty");
        T data = tail.data;
        if(tail == head){
            tail.data = null;
            tail.next = tail.prev = null;
            tail = head = null;
        } else {
            Node<T> nextTail = tail.prev;
            tail.next = tail.prev = null;
            tail.data = null;
            tail = nextTail;
            tail.next = null;
        }
        size--;
        return data;
    }

    public T removeAt(int index){
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Invalid Index");

        Node<T> trav;

        if(index < size/2){
            trav  = head;
            for(int i=0; i < index ; i++){
                trav = trav.next;
            }
        } else {
            trav  = tail;
            for(int i = size-1; i != index; i--){
                trav = trav.prev;
            }
        }

        return remove(trav);
    }

    public boolean remove(T element){
        Node<T> trav = head;

        if(element == null){
            for(trav = head; trav != null; trav = trav.next){
                if(trav.data == null){
                    remove(trav);
                    return true;
                }
            }
        } else {
            for(trav = head; trav != null; trav = trav.next){
                if(element.equals(trav.data)){
                    remove(trav);
                    return true;
                }
            }
        }
        return false;
    }

    private T remove(Node<T> node){
        if(node == null)
            throw new IllegalArgumentException("Node cant be null");

        if(node.next == null)
            return removeLast();
        if(node.prev == null)
            return removeFirst();

        node.next.prev = node.prev;
        node.prev.next = node.next;

        T data = node.data;

        node.next = node.prev = null;
        node.data = null;

        size--;
        return data;
    }

    public void clear(){
        if(isEmpty())
            return;
        Node<T> trav = head;
        while (trav.next != null){
            Node<T> next = trav.next;
            trav.prev = trav.next = null;
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

    private static class Node<T>{
        private T data;
        private Node<T> next,prev;

        public Node(T data, Node<T> next, Node<T> prev){
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString(){
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
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Node<T> trav = head; trav != null; trav = trav.next){
            if(trav.next != null){
                if(trav.data == null){
                    stringBuilder.append("(null) -> ");
                }else{
                    stringBuilder.append("(").append(trav.data.toString()).append(") -> ");
                }
            } else {
                if(trav.data == null){
                    stringBuilder.append("(null)");
                }else{
                    stringBuilder.append("(").append(trav.data.toString()).append(")");
                }
            }
        }
        return stringBuilder.toString();
    }
}
