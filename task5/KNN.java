package task5;

import Vector.Vector;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



public class KNN {
    List<Observation> observations;

    public void train(List<Observation> observations) {
        this.observations = observations;
    }

    public double calculateDistance(Vector vecA, Vector vecB) {
        return vecA.sub(vecB).len();
    }

    public int predict(Vector observation, int k) {
        var kNearest = observations.stream()
                .sorted(Comparator.comparingDouble(o -> calculateDistance(observation, o.v)))
                .toList()
                .subList(0, k);

        var groupedRows = kNearest.stream()
                .collect(Collectors.groupingBy(
                        Observation::getOutput,
                        HashMap::new,
                        Collectors.counting()
                ));

        var mostCommonEntries = groupedRows
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .toList();

        return mostCommonEntries.getFirst().getKey();
    }
}
