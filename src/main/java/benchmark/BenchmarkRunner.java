package benchmark;

import metrics.Metrics;
import structures.DynamicArray;
import structures.MyLinkedList;
import structures.MinHeap;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;

public class BenchmarkRunner {

    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final int WARMUP_RUNS = 1;
    private static final int MEASURED_RUNS = 5;

    public static void main(String[] args) throws IOException {
        File resultsDirectory = new File("results");
        if (!resultsDirectory.exists()) {
            resultsDirectory.mkdirs();
        }

        FileWriter writer = new FileWriter("results/results.csv");

        writer.write("workload,variant,structure,n,time_ms,steps,moves,comparisons\n");

        runRandomAccess(writer);
        runSearch(writer);
        runInsertRemove(writer);
        runPriorityProcessing(writer);

        writer.close();

        System.out.println("Benchmark completed.");
        System.out.println("Results saved to results/results.csv");
    }

    private static void runRandomAccess(FileWriter writer) throws IOException {
        for (int n : SIZES) {
            int[] data = generateData(n);
            int[] indexes = generateIndexes(n);

            BenchmarkResult arrayResult = measureRandomAccessArray(data, indexes);
            BenchmarkResult listResult = measureRandomAccessList(data, indexes);

            writer.write(arrayResult.toCsv() + "\n");
            writer.write(listResult.toCsv() + "\n");

            System.out.println("W1 n=" + n);
        }
    }

    private static void runSearch(FileWriter writer) throws IOException {
        for (int n : SIZES) {
            int[] data = generateData(n);
            int[] queries = generateQueries(data);

            BenchmarkResult arrayResult = measureSearchArray(data, queries);
            BenchmarkResult listResult = measureSearchList(data, queries);

            writer.write(arrayResult.toCsv() + "\n");
            writer.write(listResult.toCsv() + "\n");

            System.out.println("W2 n=" + n);
        }
    }

    private static void runInsertRemove(FileWriter writer) throws IOException {
        for (int n : SIZES) {
            int[] data = generateData(n);

            BenchmarkResult arrayHead = measureInsertRemoveArray(data, "head");
            BenchmarkResult listHead = measureInsertRemoveList(data, "head");

            BenchmarkResult arrayMiddle = measureInsertRemoveArray(data, "middle");
            BenchmarkResult listMiddle = measureInsertRemoveList(data, "middle");

            writer.write(arrayHead.toCsv() + "\n");
            writer.write(listHead.toCsv() + "\n");
            writer.write(arrayMiddle.toCsv() + "\n");
            writer.write(listMiddle.toCsv() + "\n");

            System.out.println("W3 n=" + n);
        }
    }

    private static void runPriorityProcessing(FileWriter writer) throws IOException {
        for (int n : SIZES) {
            int[] data = generateData(n);

            BenchmarkResult result = measurePriorityProcessing(data);

            writer.write(result.toCsv() + "\n");

            System.out.println("W4 n=" + n);
        }
    }

    private static BenchmarkResult measureRandomAccessArray(int[] data, int[] indexes) {
        double[] times = new double[MEASURED_RUNS];
        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < WARMUP_RUNS + MEASURED_RUNS; run++) {
            Metrics metrics = new Metrics();
            DynamicArray array = new DynamicArray(metrics);

            for (int value : data) {
                array.add(value);
            }

            metrics.reset();

            long start = System.nanoTime();

            for (int index : indexes) {
                array.get(index);
            }

            long end = System.nanoTime();

            if (run >= WARMUP_RUNS) {
                times[run - WARMUP_RUNS] = (end - start) / 1_000_000.0;
                steps = metrics.getSteps();
                moves = metrics.getMoves();
                comparisons = metrics.getComparisons();
            }
        }

        return createResult("W1", "-", "DynamicArray", data.length,
                median(times), steps, moves, comparisons);
    }

    private static BenchmarkResult measureRandomAccessList(int[] data, int[] indexes) {
        double[] times = new double[MEASURED_RUNS];
        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < WARMUP_RUNS + MEASURED_RUNS; run++) {
            Metrics metrics = new Metrics();
            MyLinkedList list = new MyLinkedList(metrics);

            for (int value : data) {
                list.add(value);
            }

            metrics.reset();

            long start = System.nanoTime();

            for (int index : indexes) {
                list.get(index);
            }

            long end = System.nanoTime();

            if (run >= WARMUP_RUNS) {
                times[run - WARMUP_RUNS] = (end - start) / 1_000_000.0;
                steps = metrics.getSteps();
                moves = metrics.getMoves();
                comparisons = metrics.getComparisons();
            }
        }

        return createResult("W1", "-", "MyLinkedList", data.length,
                median(times), steps, moves, comparisons);
    }

    private static BenchmarkResult measureSearchArray(int[] data, int[] queries) {
        double[] times = new double[MEASURED_RUNS];
        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < WARMUP_RUNS + MEASURED_RUNS; run++) {
            Metrics metrics = new Metrics();
            DynamicArray array = new DynamicArray(metrics);

            for (int value : data) {
                array.add(value);
            }

            metrics.reset();

            long start = System.nanoTime();

            for (int query : queries) {
                array.contains(query);
            }

            long end = System.nanoTime();

            if (run >= WARMUP_RUNS) {
                times[run - WARMUP_RUNS] = (end - start) / 1_000_000.0;
                steps = metrics.getSteps();
                moves = metrics.getMoves();
                comparisons = metrics.getComparisons();
            }
        }

        return createResult("W2", "-", "DynamicArray", data.length,
                median(times), steps, moves, comparisons);
    }

    private static BenchmarkResult measureSearchList(int[] data, int[] queries) {
        double[] times = new double[MEASURED_RUNS];
        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < WARMUP_RUNS + MEASURED_RUNS; run++) {
            Metrics metrics = new Metrics();
            MyLinkedList list = new MyLinkedList(metrics);

            for (int value : data) {
                list.add(value);
            }

            metrics.reset();

            long start = System.nanoTime();

            for (int query : queries) {
                list.contains(query);
            }

            long end = System.nanoTime();

            if (run >= WARMUP_RUNS) {
                times[run - WARMUP_RUNS] = (end - start) / 1_000_000.0;
                steps = metrics.getSteps();
                moves = metrics.getMoves();
                comparisons = metrics.getComparisons();
            }
        }

        return createResult("W2", "-", "MyLinkedList", data.length,
                median(times), steps, moves, comparisons);
    }

    private static BenchmarkResult measureInsertRemoveArray(int[] data, String variant) {
        double[] times = new double[MEASURED_RUNS];
        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < WARMUP_RUNS + MEASURED_RUNS; run++) {
            Metrics metrics = new Metrics();
            DynamicArray array = new DynamicArray(metrics);

            for (int value : data) {
                array.add(value);
            }

            metrics.reset();

            int index = variant.equals("head") ? 0 : data.length / 2;

            long start = System.nanoTime();

            for (int i = 0; i < 1000; i++) {
                array.add(index, -i - 1);
            }

            for (int i = 0; i < 1000; i++) {
                array.remove(index);
            }

            long end = System.nanoTime();

            if (run >= WARMUP_RUNS) {
                times[run - WARMUP_RUNS] = (end - start) / 1_000_000.0;
                steps = metrics.getSteps();
                moves = metrics.getMoves();
                comparisons = metrics.getComparisons();
            }
        }

        return createResult("W3", variant, "DynamicArray", data.length,
                median(times), steps, moves, comparisons);
    }

    private static BenchmarkResult measureInsertRemoveList(int[] data, String variant) {
        double[] times = new double[MEASURED_RUNS];
        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < WARMUP_RUNS + MEASURED_RUNS; run++) {
            Metrics metrics = new Metrics();
            MyLinkedList list = new MyLinkedList(metrics);

            for (int value : data) {
                list.add(value);
            }

            metrics.reset();

            int index = variant.equals("head") ? 0 : data.length / 2;

            long start = System.nanoTime();

            for (int i = 0; i < 1000; i++) {
                list.add(index, -i - 1);
            }

            for (int i = 0; i < 1000; i++) {
                list.remove(index);
            }

            long end = System.nanoTime();

            if (run >= WARMUP_RUNS) {
                times[run - WARMUP_RUNS] = (end - start) / 1_000_000.0;
                steps = metrics.getSteps();
                moves = metrics.getMoves();
                comparisons = metrics.getComparisons();
            }
        }

        return createResult("W3", variant, "MyLinkedList", data.length,
                median(times), steps, moves, comparisons);
    }

    private static BenchmarkResult measurePriorityProcessing(int[] data) {
        double[] times = new double[MEASURED_RUNS];
        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < WARMUP_RUNS + MEASURED_RUNS; run++) {
            Metrics metrics = new Metrics();
            MinHeap heap = new MinHeap(metrics);

            long start = System.nanoTime();

            for (int value : data) {
                heap.insert(value);
            }

            int previous = Integer.MIN_VALUE;

            for (int i = 0; i < data.length; i++) {
                int current = heap.extractMin();

                if (current < previous) {
                    throw new IllegalStateException("Heap output is not sorted");
                }

                previous = current;
            }

            long end = System.nanoTime();

            if (run >= WARMUP_RUNS) {
                times[run - WARMUP_RUNS] = (end - start) / 1_000_000.0;
                steps = metrics.getSteps();
                moves = metrics.getMoves();
                comparisons = metrics.getComparisons();
            }
        }

        return createResult("W4", "-", "MinHeap", data.length,
                median(times), steps, moves, comparisons);
    }

    private static int[] generateData(int n) {
        Random random = new Random(42);
        int[] data = new int[n];

        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt(1_000_000);
        }

        return data;
    }

    private static int[] generateIndexes(int n) {
        Random random = new Random(42);
        int[] indexes = new int[10_000];

        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = random.nextInt(n);
        }

        return indexes;
    }

    private static int[] generateQueries(int[] data) {
        Random random = new Random(42);
        int[] queries = new int[1_000];

        for (int i = 0; i < 500; i++) {
            queries[i * 2] = data[random.nextInt(data.length)];
            queries[i * 2 + 1] = -i - 1;
        }

        return queries;
    }

    private static double median(double[] values) {
        double[] copy = values.clone();

        for (int i = 0; i < copy.length - 1; i++) {
            for (int j = i + 1; j < copy.length; j++) {
                if (copy[j] < copy[i]) {
                    double temp = copy[i];
                    copy[i] = copy[j];
                    copy[j] = temp;
                }
            }
        }

        return copy[copy.length / 2];
    }

    private static BenchmarkResult createResult(
            String workload,
            String variant,
            String structure,
            int n,
            double time,
            long steps,
            long moves,
            long comparisons) {

        return new BenchmarkResult(
                workload,
                variant,
                structure,
                n,
                time,
                steps,
                moves,
                comparisons
        );
    }
}