package structures;

import metrics.Metrics;

public class MyLinkedList {
    private Node head;
    private int size;
    private final Metrics metrics;

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    public MyLinkedList() {
        this(null);
    }

    public MyLinkedList(Metrics metrics) {
        this.metrics = metrics;
    }

    public void add(int x) {
        Node newNode = new Node(x);

        if (head == null) {
            head = newNode;
            size++;
            return;
        }

        Node current = head;

        while (current.next != null) {
            step();
            current = current.next;
        }

        current.next = newNode;
        move();
        size++;
    }

    public void add(int index, int x) {
        checkPosition(index);

        Node newNode = new Node(x);

        if (index == 0) {
            newNode.next = head;
            move();
            head = newNode;
            move();
            size++;
            return;
        }

        Node current = head;

        for (int i = 0; i < index - 1; i++) {
            step();
            current = current.next;
        }

        newNode.next = current.next;
        move();

        current.next = newNode;
        move();

        size++;
    }

    public int remove(int index) {
        checkIndex(index);

        if (index == 0) {
            int removed = head.value;
            head = head.next;
            move();
            size--;
            return removed;
        }

        Node current = head;

        for (int i = 0; i < index - 1; i++) {
            step();
            current = current.next;
        }

        int removed = current.next.value;
        current.next = current.next.next;
        move();
        size--;

        return removed;
    }

    public int get(int index) {
        checkIndex(index);

        Node current = head;

        for (int i = 0; i < index; i++) {
            step();
            current = current.next;
        }

        return current.value;
    }

    public boolean contains(int x) {
        Node current = head;

        while (current != null) {
            step();
            comparison();

            if (current.value == x) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    public int size() {
        return size;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkPosition(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void step() {
        if (metrics != null) {
            metrics.step();
        }
    }

    private void move() {
        if (metrics != null) {
            metrics.move();
        }
    }

    private void comparison() {
        if (metrics != null) {
            metrics.comparison();
        }
    }
}