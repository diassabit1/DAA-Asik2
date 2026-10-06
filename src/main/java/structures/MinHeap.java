package structures;

import metrics.Metrics;

public class MinHeap {
    private int[] heap;
    private int size;
    private final Metrics metrics;

    public MinHeap() {
        this(null);
    }

    public MinHeap(Metrics metrics) {
        heap = new int[10];
        size = 0;
        this.metrics = metrics;
    }

    public void insert(int x) {
        if (size == heap.length) {
            resize();
        }

        heap[size] = x;
        move();

        int index = size;
        size++;

        while (index > 0) {
            int parent = (index - 1) / 2;

            step();
            comparison();

            if (heap[parent] <= heap[index]) {
                break;
            }

            swap(parent, index);
            index = parent;
        }
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        step();
        return heap[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        step();
        int result = heap[0];

        heap[0] = heap[size - 1];
        move();
        size--;

        if (size > 0) {
            bubbleDown(0);
        }

        return result;
    }

    public int size() {
        return size;
    }

    public void buildHeap(int[] values) {
        heap = new int[Math.max(10, values.length)];
        size = values.length;

        for (int i = 0; i < values.length; i++) {
            heap[i] = values[i];
        }

        for (int i = size / 2 - 1; i >= 0; i--) {
            bubbleDown(i);
        }
    }

    private void bubbleDown(int index) {
        while (true) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            int smallest = index;

            if (left < size) {
                step();
                comparison();

                if (heap[left] < heap[smallest]) {
                    smallest = left;
                }
            }

            if (right < size) {
                step();
                comparison();

                if (heap[right] < heap[smallest]) {
                    smallest = right;
                }
            }

            if (smallest == index) {
                break;
            }

            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int a, int b) {
        int temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
        move();
    }

    private void resize() {
        int[] newHeap = new int[heap.length * 2];

        for (int i = 0; i < size; i++) {
            step();
            newHeap[i] = heap[i];
            move();
        }

        heap = newHeap;
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