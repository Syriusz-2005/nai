import java.util.ArrayList;

public class Perceptron {
    public Vector weights;
    public double threshold;
    public double alpha;

    public Perceptron(Vector weights, double threshold, double alpha) {
        this.weights = weights;
        this.threshold = threshold;
        this.alpha = alpha;
    }

    public Perceptron(int size, double alpha) {
        var weights = new Vector(new ArrayList<>());
        for (int i = 0; i < size; i++) {
            weights.components.add(Math.random() * 2.0 - 1);
        }
        this.weights = weights;
        this.threshold = 0.0;
        this.alpha = alpha;
    }

    public double computeNet(Vector input) {
        return input.dot(weights) - threshold;
    }

    public double compute(Vector data) {
        double net = computeNet(data);
        return 1 / (1 + Math.exp(-net));
    }

    public void update(Vector data, double decision) {
        double result = compute(data);
        // Pseudocode: weights = weights + (decision - result) * result * (1 - result) * alpha
        weights = weights.add(data.mul((decision - result) * result * (1 - result) * alpha));
        threshold -= alpha * (decision - result) * result * (1 - result);
    }

    @Override
    public String toString() {
        return "\nPerceptron{" +
                "weights=" + weights +
                ", threshold=" + threshold +
                ", alpha=" + alpha +
                "}";
    }
}
