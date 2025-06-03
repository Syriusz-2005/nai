package task11;

import task7.ClusterVisualization;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        var scheduler = new GreedyScheduler();
        var tasksRandom = IntStream.range(0, 35)
                .mapToObj((a) -> {
                    Random rand = new Random();
                    var start = rand.nextInt(1000);
                    return new Task(start, start + rand.nextInt(60, 90));
                })
                .collect(Collectors.toCollection(ArrayList::new));
        var tasks = List.of(new Task(0, 5), new Task(5, 11), new Task(10, 16), new Task(15, 20));
        var result = scheduler.selectMaxNonOverlappingTasks(tasksRandom);
        System.out.printf("Result: %s", result);
        SwingUtilities.invokeLater(() -> {
            var visualisation = new TaskVisualizer(tasksRandom, result);
            var window = new JFrame();
            window.setSize(800, 800);
            window.add(visualisation);
            window.setVisible(true);
        });
    }
}
