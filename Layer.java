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

}
