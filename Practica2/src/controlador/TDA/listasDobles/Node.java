package controlador.TDA.listasDobles;

public class Node<E> {
    private E info;
    private Node<E> next;
    private Node<E> prev;
    

    public Node(E info) {
        this.info = info;
        next = null;
        prev = null;
    }

    public Node(E info, Node<E> next, Node<E> prev) {
        this.info = info;
        this.next = next;
        this.prev = prev;
    }

    public Node() {
        prev = null;
        next = null;
        info = null;
    }

    public E getInfo() {
        return info;
    }

    public void setInfo(E info) {
        this.info = info;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public Node<E> getPrev() {
        return prev;
    }

    public void setPrev(Node<E> prev) {
        this.prev = prev;
    }
    
    
}
