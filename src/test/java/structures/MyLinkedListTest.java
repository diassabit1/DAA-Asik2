package structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {

    @Test
    void addAndGet() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
        assertEquals(3, list.size());
    }

    @Test
    void insertAndRemove() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(30);
        list.add(1, 20);

        assertEquals(20, list.get(1));
        assertEquals(20, list.remove(1));
        assertEquals(30, list.get(1));
    }

    @Test
    void containsAndDuplicates() {
        MyLinkedList list = new MyLinkedList();

        list.add(5);
        list.add(5);
        list.add(10);

        assertTrue(list.contains(5));
        assertTrue(list.contains(10));
        assertFalse(list.contains(20));
    }

    @Test
    void emptyList() {
        MyLinkedList list = new MyLinkedList();

        assertEquals(0, list.size());
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        assertFalse(list.contains(10));
    }

    @Test
    void invalidIndexThrowsException() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(2, 20));
    }
}