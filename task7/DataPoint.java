package task7;

import Vector.Vector;

public class DataPoint {
    private final Vector features;
    private final String className;
    public int clusterId;

    public DataPoint(Vector features, String className) {
        this.features = features;
        this.className = className;
    }

    public double distance(DataPoint other) {
        return other.distance(features);
    }

    public double distance(Vector v) {
        return features.sub(v).len();
    }

    public double[] getFeatures() {
        return features.getAsArray();
    }

    public Vector getFeaturesVector() {
        return features;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "features=" + features +
                ", className='" + className + '\'' +
                ", clusterId=" + clusterId +
                '}';
    }
}
