package benchmark;

import org.openjdk.jol.info.GraphLayout;
import structures.DynamicArray;
import structures.MyLinkedList;
import structures.MinHeap;

import java.io.FileWriter;
import java.io.IOException;

public class MemoryBenchmark {

    private static final int[] SIZES = {100, 1000, 10000, 100000};

    public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("results/memory.csv");

        writer.write("structure,n,memory_bytes\n");

        for (int n : SIZES) {
            DynamicArray array = new DynamicArray();
            MyLinkedList list = new MyLinkedList();
            MinHeap heap = new MinHeap();

            for (int i = 0; i < n; i++) {
                array.add(i);
                list.add(i);
                heap.insert(i);
            }

            long arrayMemory = GraphLayout.parseInstance(array).totalSize();
            long listMemory = GraphLayout.parseInstance(list).totalSize();
            long heapMemory = GraphLayout.parseInstance(heap).totalSize();

            writer.write("DynamicArray," + n + "," + arrayMemory + "\n");
            writer.write("MyLinkedList," + n + "," + listMemory + "\n");
            writer.write("MinHeap," + n + "," + heapMemory + "\n");

            System.out.println("Memory measured for n=" + n);
        }

        writer.close();

        System.out.println("Memory results saved to results/memory.csv");
    }
}