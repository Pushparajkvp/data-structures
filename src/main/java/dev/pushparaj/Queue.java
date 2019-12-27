package dev.pushparaj;

import java.util.Iterator;

public class Queue<T> implements Iterable<T> {

    private int size = 0;
    private Node<T> head = null, tail = null;

    public Queue(){}

    public Queue(T firstElement){
        offer(firstElement);
    }

    public void offer(T element){
        addLast(element);
    }

    public T poll(){
        if(isEmpty())
            throw new RuntimeException("Queue Empty");
        return removeFirst();
    }

    public T peek(){
        if(isEmpty())
            throw new RuntimeException("Queue Empty");
        return head.data;
    }

    private void addLast(T element){
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

    private T removeFirst(){
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
