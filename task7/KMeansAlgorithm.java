package task7;

import Vector.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KMeansAlgorithm {
    private final int k;
    private final List<DataPoint> dataPoints;
    private List<Vector> centroids = new ArrayList<>();

    public KMeansAlgorithm(int k, List<DataPoint> dataPoints) {
        this.k = k;
        this.dataPoints = dataPoints;
    }

    public void initializeCentroids() {
        var availableDataPoints = new ArrayList<>(dataPoints);
        centroids = new ArrayList<>(IntStream.range(0, k)
                .mapToObj((i) -> {
                    var randomPointIndex = RandomGenerator.getDefault().nextInt(availableDataPoints.size());
                    var selectedPoint = availableDataPoints.remove(randomPointIndex);
                    return selectedPoint.getFeaturesVector();
                })
                .toList()
        );
    }

    public void run(int maxIterations) {
        for (int i = 0; i < maxIterations; i++) {
            boolean hasChanged = assignClusters();
            if (!hasChanged) return;
            updateCentroids();
        }
    }

    public double[][] getCentroids() {
        return centroids.stream()
                .map(c -> c.getAsArray())
                .toArray(double[][]::new);
    }

    private int findNearestClusterId(DataPoint p) {
        int nearestId = -1;
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < centroids.size(); i++) {
            var d = p.distance(centroids.get(i));
            if (d < minDistance) {
                nearestId = i;
                minDistance = d;
            }
        }
        return nearestId;
    }

    private boolean assignClusters() {
        boolean hasChanged = false;
        for (var p : dataPoints) {
            var newClusterId = findNearestClusterId(p);
            if (newClusterId != p.clusterId) hasChanged = true;
            p.clusterId = newClusterId;
        }
        return hasChanged;
    }

    private void updateCentroids() {
        dataPoints
                .stream()
                .collect(Collectors.groupingBy(
                        dataPoint -> dataPoint.clusterId
                ))
                .forEach((clusterId, groupedDataPoints) -> {
                    var newCentroid = Vector.getCentroid(groupedDataPoints.stream().map(DataPoint::getFeaturesVector).toList());
                    centroids.set(clusterId, newCentroid);
                });
    }
}
