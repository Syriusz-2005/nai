package task11;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GreedyScheduler {
    public ArrayList<Task> selectMaxNonOverlappingTasks(List<Task> tasks) {
        var sortedTasks = tasks
                .stream()
                .sorted()
                .toList();
        var output = new ArrayList<Task>();
        output.add(sortedTasks.getFirst());
        var currentEnd = sortedTasks.getFirst().end();
        for (int i = 0; i < sortedTasks.size() - 1; i++) {
            int finalCurrentEnd = currentEnd;
            var nextTasks = sortedTasks.subList(i + 1, sortedTasks.size())
                    .stream()
                    .filter((task) -> task.start() >= finalCurrentEnd);
            var shortestTask = nextTasks.min(Comparator.comparingInt(Task::end));
            if (shortestTask.isPresent()) {
                currentEnd = shortestTask.get().end();
                output.add(shortestTask.get());
            }
        }
        return output;
    }
}
