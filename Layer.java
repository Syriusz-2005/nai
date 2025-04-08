import Vector.Vector;

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
            perceptrons.add(new Perceptron(Lang.chars.length(), 0.1));
        }
        for (int i = 0; i < iterations; i++) {
            for (int p = 0; p < 5; p++) {
                for (int j = 0; j < langs.size(); j++) {
                    var lang = langs.get(j);
                    var paragraphs = lang.getAsVectors();
                    var expectedDecisions = getExpectedDecision(langs, j);
                    var para = paragraphs.get(p);
                    layerUpdate(para, expectedDecisions);
                }
            }
            if (i % 100 == 0) {
                System.out.println(perceptrons);
            }
        }
    }

    private List<Double> getExpectedDecision(List<Lang> langs, int correctLangIndex) {
        List<Double> decision = new ArrayList<>(langs.stream().map((_lang) -> 0.0).toList());
        decision.set(correctLangIndex, 1.0);
        return decision;
    }

    @Override
    public String toString() {
        return "Layer perceptrons: " + perceptrons;
    }
}
