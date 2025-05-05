package task7;

import Vector.Vector;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("iris/iris.data"));
        var dataPoints = parseLines(lines);
        System.out.println("Data points: " + dataPoints);
        var algo = new KMeansAlgorithm(3, dataPoints);
        algo.initializeCentroids();
        algo.run(100);
        System.out.println("Data points after: " + dataPoints);
        SwingUtilities.invokeLater(() -> {
            var visualisation = new ClusterVisualization(dataPoints, algo.getCentroids());
            var window = new JFrame();
            window.setSize(800, 800);
            window.add(visualisation);
            window.setVisible(true);
        });
    }

    private static ArrayList<DataPoint> parseLines(List<String> lines) {
        return lines
                .stream()
                .map((line) -> Arrays.stream(line.split(";")).toList())
                .map((values) -> new DataPoint(
                        new Vector(values.subList(0, values.size() - 1).stream().map(Double::valueOf).toList()),
                        values.getLast()
                ))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
