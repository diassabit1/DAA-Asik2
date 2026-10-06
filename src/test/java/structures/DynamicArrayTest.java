package structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicArrayTest {

    @Test
    void addAndGet() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
        assertEquals(3, array.size());
    }

    @Test
    void insertAndRemove() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(30);
        array.add(1, 20);

        assertEquals(20, array.get(1));
        assertEquals(20, array.remove(1));
        assertEquals(30, array.get(1));
    }

    @Test
    void containsAndDuplicates() {
        DynamicArray array = new DynamicArray();

        array.add(5);
        array.add(5);
        array.add(10);

        assertTrue(array.contains(5));
        assertTrue(array.contains(10));
        assertFalse(array.contains(20));
    }

    @Test
    void resizeWorks() {
        DynamicArray array = new DynamicArray();

        for (int i = 0; i < 100; i++) {
            array.add(i);
        }

        assertEquals(100, array.size());

        for (int i = 0; i < 100; i++) {
            assertEquals(i, array.get(i));
        }
    }

    @Test
    void invalidIndexThrowsException() {
        DynamicArray array = new DynamicArray();

        assertThrows(IndexOutOfBoundsException.class, () -> array.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(0));
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(-1, 10));
    }
}