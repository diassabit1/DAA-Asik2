package structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {

    @Test
    void insertAndPeek() {
        MinHeap heap = new MinHeap();

        heap.insert(5);
        heap.insert(2);
        heap.insert(8);
        heap.insert(1);

        assertEquals(1, heap.peekMin());
        assertEquals(4, heap.size());
    }

    @Test
    void sortedExtraction() {
        MinHeap heap = new MinHeap();

        int[] values = {7, 2, 9, 1, 5, 3};

        for (int value : values) {
            heap.insert(value);
        }

        int previous = Integer.MIN_VALUE;

        while (heap.size() > 0) {
            int current = heap.extractMin();
            assertTrue(current >= previous);
            previous = current;
        }
    }

    @Test
    void duplicates() {
        MinHeap heap = new MinHeap();

        heap.insert(5);
        heap.insert(5);
        heap.insert(5);

        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
    }

    @Test
    void emptyHeapThrowsException() {
        MinHeap heap = new MinHeap();

        assertThrows(IllegalStateException.class, heap::peekMin);
        assertThrows(IllegalStateException.class, heap::extractMin);
    }

    @Test
    void manyElements() {
        MinHeap heap = new MinHeap();

        for (int i = 100; i >= 1; i--) {
            heap.insert(i);
        }

        for (int i = 1; i <= 100; i++) {
            assertEquals(i, heap.extractMin());
        }
    }
}