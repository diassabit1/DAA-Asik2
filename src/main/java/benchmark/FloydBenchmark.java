package benchmark;

import structures.MinHeap;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FloydBenchmark {

    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final int WARMUP = 1;
    private static final int RUNS = 5;

    public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("results/floyd.csv");

        writer.write("method,n,time_ms\n");

        for (int n : SIZES) {
            int[] data = generateData(n);

            for (int i = 0; i < WARMUP; i++) {
                buildByInsert(data);
                buildByFloyd(data);
            }

            double insertTime = medianInsert(data);
            double floydTime = medianFloyd(data);

            writer.write("insert," + n + "," +
                    String.format(java.util.Locale.US, "%.4f", insertTime) + "\n");

            writer.write("floyd," + n + "," +
                    String.format(java.util.Locale.US, "%.4f", floydTime) + "\n");

            System.out.println("n=" + n +
                    " insert=" + insertTime +
                    " ms, floyd=" + floydTime + " ms");
        }

        writer.close();

        System.out.println("Floyd results saved to results/floyd.csv");
    }

    private static double medianInsert(int[] data) {
        double[] times = new double[RUNS];

        for (int i = 0; i < RUNS; i++) {
            long start = System.nanoTime();

            buildByInsert(data);

            long end = System.nanoTime();
            times[i] = (end - start) / 1_000_000.0;
        }

        return median(times);
    }

    private static double medianFloyd(int[] data) {
        double[] times = new double[RUNS];

        for (int i = 0; i < RUNS; i++) {
            long start = System.nanoTime();

            buildByFloyd(data);

            long end = System.nanoTime();
            times[i] = (end - start) / 1_000_000.0;
        }

        return median(times);
    }

    private static void buildByInsert(int[] data) {
        MinHeap heap = new MinHeap();

        for (int value : data) {
            heap.insert(value);
        }
    }

    private static void buildByFloyd(int[] data) {
        MinHeap heap = new MinHeap();
        heap.buildHeap(data);
    }

    private static double median(double[] values) {
        java.util.Arrays.sort(values);

        return values[values.length / 2];
    }

    private static int[] generateData(int n) {
        Random random = new Random(42);
        int[] data = new int[n];

        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt(1_000_000);
        }

        return data;
    }
}