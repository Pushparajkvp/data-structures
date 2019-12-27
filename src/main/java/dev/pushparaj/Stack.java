package dev.pushparaj;

import java.util.Iterator;

public class Stack<T> implements Iterable<T>{

    private int size = 0;
    private Node<T> head = null;

    public Stack(){}

    public Stack(T element){
        push(element);
    }

    public void push(T element){
        head = new Node<>(element, head);
        size++;
    }

    public T pop(){
        T data = head.data;
        head = head.next;
        size--;
        return data;
    }

    public T peek(){
        if(head == null)
            throw new RuntimeException("Stack empty");
        return head.data;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }



    private static class Node<T>{
        private T data;
        private Node<T> next;

        public Node(T data, Node<T> next){
            this.data = data;
            this.next = next;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private Node<T> trav = head;

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
