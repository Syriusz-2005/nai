package task11;

public record Task(int start, int end) implements Comparable<Task> {
    @Override
    public int compareTo(Task o) {
        return start - o.start;
    }
}
