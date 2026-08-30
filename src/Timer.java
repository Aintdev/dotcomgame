public class Timer {
    private long start;

    public Timer() {
        reset();
    }

    public void reset() {
        start = System.nanoTime();
    }

    public long elapsedNanos() {
        return System.nanoTime() - start;
    }

    public long elapsedMillis() {
        return elapsedNanos() / 1_000_000;
    }
}