package task5;

import java.util.ArrayList;
import java.util.List;

record PredictionResult(Observation observation, int predictedOutput) {}

public class CrossValidation {
    List<Observation> observations;

    public CrossValidation(List<Observation> observations) {
        this.observations = observations;
    }

    public void kFoldCrossValidation(int k) {
        ArrayList<List<Observation>> partitionedObservations = new ArrayList<>();
        for (int i = 0; i < observations.size(); i += (observations.size() / k)) {
            partitionedObservations.add(observations.subList(i, Math.min(observations.size(), i + k)));
        }

        for (int i = 0; i < partitionedObservations.size(); i++) {
            List<Observation> testDataset = partitionedObservations.get(i);
            List<Observation> trainingDataset = observations.subList(0, i);
            trainingDataset.addAll(observations.subList(i + 1, partitionedObservations.size()));
            var knnPredictor = new KNN();
            knnPredictor.train(trainingDataset);
            var predictionResults = testDataset
                    .stream()
                    .map(observation -> new PredictionResult(observation, knnPredictor.predict(observation.v, 5)))
                    .toList();

            var truePositives = predictionResults
                    .stream()
                    .filter(result -> result.predictedOutput() == result.observation().getOutput() && result.predictedOutput() == 1)
                    .count();
            var falsePositives = predictionResults
                    .stream()
                    .filter(result -> result.predictedOutput() != result.observation().getOutput() && result.predictedOutput() == 1)
                    .count();
            var falseNegatives = predictionResults
                    .stream()
                    .filter(result -> result.predictedOutput() != result.observation().getOutput() && result.predictedOutput() == 0)
                    .count();

            var p = truePositives / (truePositives + falsePositives);
            var r = truePositives / (truePositives + falseNegatives);

            var f = 2 * (p * r) / (p + r);

            System.out.printf("\nPartition %s:", i);
            System.out.printf("\nF=%s", f);
        }
    }
}
