package benchmark;

public class BenchmarkResult {
    private final String workload;
    private final String variant;
    private final String structure;
    private final int n;
    private final double timeMs;
    private final long steps;
    private final long moves;
    private final long comparisons;

    public BenchmarkResult(
            String workload,
            String variant,
            String structure,
            int n,
            double timeMs,
            long steps,
            long moves,
            long comparisons) {

        this.workload = workload;
        this.variant = variant;
        this.structure = structure;
        this.n = n;
        this.timeMs = timeMs;
        this.steps = steps;
        this.moves = moves;
        this.comparisons = comparisons;
    }

    public String toCsv() {
        return workload + "," +
                variant + "," +
                structure + "," +
                n + "," +
                String.format(java.util.Locale.US, "%.4f", timeMs) + "," +
                steps + "," +
                moves + "," +
                comparisons;
    }
}