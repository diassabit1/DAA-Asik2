package structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FloydHeapTest {

    @Test
    void buildHeapProducesSortedOutput() {
        MinHeap heap = new MinHeap();

        int[] values = {7, 2, 9, 1, 5, 3, 2};

        heap.buildHeap(values);

        int previous = Integer.MIN_VALUE;

        while (heap.size() > 0) {
            int current = heap.extractMin();

            assertTrue(current >= previous);

            previous = current;
        }
    }

    @Test
    void buildHeapHandlesDuplicates() {
        MinHeap heap = new MinHeap();

        int[] values = {5, 5, 1, 1, 3};

        heap.buildHeap(values);

        assertEquals(1, heap.extractMin());
        assertEquals(1, heap.extractMin());
        assertEquals(3, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
    }

    @Test
    void buildHeapHandlesEmptyArray() {
        MinHeap heap = new MinHeap();

        heap.buildHeap(new int[0]);

        assertEquals(0, heap.size());
    }
}