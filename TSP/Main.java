package TSP;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        var cities = List.of(
                new City('a', 20, 20),
                new City('b', 100, 50),
                new City('c', 130, 50),
                new City('d', 10, 10),
                new City('e', 100, 110),
                new City('f', 120, 5),
                new City('g', 14, 130),
                new City('h', 70, 100),
                new City('i', 140, 130)
        );
        var solver = new SwapLocalTSPSearchSolver(cities);
        var initialSolution = solver.generateInitialSolution();
        System.out.println("Initial: " + initialSolution.getTotalDistance());
        var swapSolution = solver.localSearch(2_000, initialSolution.clone());
        System.out.println("Dist: " + swapSolution.getTotalDistance());
        System.out.println(swapSolution.getTour());
        var solverTwoOpt = new TwoOptTSPSearchSolver(cities);
        var twoOptSolution = solverTwoOpt.localSearch(2_000, initialSolution.clone());
        System.out.println("=== Two Opt === ");
        System.out.println("Dist: " + twoOptSolution.getTotalDistance());
        System.out.println(twoOptSolution.getTour());
        SwingUtilities.invokeLater(() -> {
            var visualisation = new TSPVisualizer(cities, twoOptSolution.getTour());
            var window = new JFrame();
            window.setSize(800, 800);
            window.add(visualisation);
            window.setVisible(true);
        });
    }
}
