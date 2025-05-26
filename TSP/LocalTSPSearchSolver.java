package TSP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

abstract class LocalTSPSearchSolver {
    protected final List<City> cities;

    public LocalTSPSearchSolver(List<City> cities) {
        this.cities = cities;
    }

    public TSPSolution generateInitialSolution() {
        var shuffledPath = new ArrayList<>(cities.stream().toList());
        Collections.shuffle(shuffledPath);
        shuffledPath.add(shuffledPath.getFirst());
        return new TSPSolution(shuffledPath).evaluate();
    }

    public abstract List<TSPSolution> getNeighbors(TSPSolution current);

    public TSPSolution localSearch(int maxIterations, TSPSolution solution) {
        var currentSolution = solution.clone();
        for (int i = 0; i < maxIterations; i++) {
            var neighbors = getNeighbors(solution);
            var shortestNeighbor = neighbors.stream()
                    .sorted(Comparator.comparingDouble(TSPSolution::getTotalDistance))
                    .toList()
                    .getLast();
            if (currentSolution.getTotalDistance() > shortestNeighbor.getTotalDistance()) {
                currentSolution = shortestNeighbor;
            }
        }
        return currentSolution;
    }
}

class SwapLocalTSPSearchSolver extends LocalTSPSearchSolver {

    public SwapLocalTSPSearchSolver(List<City> cities) {
        super(cities);
    }

    @Override
    public List<TSPSolution> getNeighbors(TSPSolution current) {
        return IntStream.range(1, cities.size())
                .mapToObj((i) -> current.clone().swap(i).evaluate())
                .toList();
    }
}

class TwoOptTSPSearchSolver extends LocalTSPSearchSolver {

    public TwoOptTSPSearchSolver(List<City> cities) {
        super(cities);
    }

    @Override
    public List<TSPSolution> getNeighbors(TSPSolution current) {
        return IntStream.range(1, cities.size())
                .mapToObj((i) -> {
                    var start = RandomGenerator.getDefault().nextInt(1, cities.size() - 2);
                    var end = RandomGenerator.getDefault().nextInt(start + 2, cities.size());
                    var nextSolution = current.clone();
                    var nextTour = nextSolution.getTour();
                    for (int j = 0; j < (end - start) / 2; j++) {
                        var e = nextTour.get(start + j);
                        nextTour.set(start + j, nextTour.get(end - j - 1));
                        nextTour.set(end - j - 1, e);
                    }
                    nextSolution.evaluate();
                    return nextSolution;
                })
                .toList();
    }
}

