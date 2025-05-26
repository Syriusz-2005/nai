package TSP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

public class TSPSolution {
    private final List<City> tour;
    private double totalDistance;

    public TSPSolution(List<City> tour) {
        this.tour = tour;
    }

    private TSPSolution(List<City> tour, double totalDistance) {
        this(tour);
        this.totalDistance = totalDistance;
    }

    public TSPSolution evaluate() {
        totalDistance = IntStream.range(1, tour.size())
                .mapToDouble((i) -> tour.get(i - 1).distanceTo(tour.get(i)))
                .reduce(0.0, Double::sum);
        return this;
    }

    public TSPSolution clone() {
        return new TSPSolution(new ArrayList<>(tour.stream().toList()), totalDistance);
    }

    public TSPSolution swap(int i) {
        Collections.swap(tour, i, RandomGenerator.getDefault().nextInt(tour.size() - 1));
        return this;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public List<City> getTour() {
        return tour;
    }
};
