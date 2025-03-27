import java.util.ArrayList;
import java.util.List;

public class Layer {
    public ArrayList<Perceptron> perceptrons = new ArrayList<>();

    public List<Double> layerCompute(Vector data) {
        return perceptrons.stream()
                .map(p -> p.compute(data))
                .toList();
    }

    public void layerUpdate(Vector data, List<Double> decision) {
        for (int i = 0; i < perceptrons.size(); i++) {
            perceptrons.get(i).update(data, decision.get(i));
        }
    }

    public void train(List<Lang> langs, int iterations) {
        perceptrons = new ArrayList<>();
        for (var lang : langs) {
            perceptrons.add(new Perceptron(5000, 0.1)); // Hardcoded dimensions (chars)
        }
        for (int i = 0; i < iterations; i++) {
            for (int j = 0; j < langs.size(); j++) {
                var lang = langs.get(j);
                var perceptron = perceptrons.get(j);
                var paragraphs = lang.getAsVectors();
                var expectedDecisions = ;
                for (var para : paragraphs) {

                }
            }
            List<Double> currentResult = layerCompute();
        }
    }

}
