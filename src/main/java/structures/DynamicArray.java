package structures;

import metrics.Metrics;

public class DynamicArray {
    private int[] data;
    private int size;
    private final Metrics metrics;

    public DynamicArray() {
        this(null);
    }

    public DynamicArray(Metrics metrics) {
        data = new int[10];
        size = 0;
        this.metrics = metrics;
    }

    public void add(int x) {
        if (size == data.length) {
            resize();
        }

        data[size] = x;
        size++;
    }

    public void add(int index, int x) {
        checkPosition(index);

        if (size == data.length) {
            resize();
        }

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            move();
        }

        data[index] = x;
        size++;
    }

    public int remove(int index) {
        checkIndex(index);

        int removed = data[index];

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            move();
        }

        size--;
        return removed;
    }

    public int get(int index) {
        checkIndex(index);
        step();
        return data[index];
    }

    public boolean contains(int x) {
        for (int i = 0; i < size; i++) {
            step();
            comparison();

            if (data[i] == x) {
                return true;
            }
        }

        return false;
    }

    public int size() {
        return size;
    }

    private void resize() {
        int[] newData = new int[data.length * 2];

        for (int i = 0; i < size; i++) {
            step();
            newData[i] = data[i];
            move();
        }

        data = newData;
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