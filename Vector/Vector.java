package Vector;

import java.util.ArrayList;
import java.util.List;

public class Vector {
    List<Double> components;

    public Vector(List<Double> components) {
        this.components = components;
    }

    public List<Double> getComponents() {
        return components;
    }

    public double[] getAsArray() {
        return components.stream().mapToDouble(c -> c).toArray();
    }

    public double dot(Vector v) {
        double out = 0;
        for (int i = 0; i < components.size(); i++) {
            out += components.get(i) * v.components.get(i);
        }
        return out;
    }

    public Vector add(Vector v) {
        Vector out = new Vector(new ArrayList<>());
        for (int i = 0; i < components.size(); i++) {
            out.components.add(components.get(i) + v.components.get(i));
        }
        return out;
    }

    public Vector sub(Vector v) {
        Vector out = new Vector(new ArrayList<>());
        for (int i = 0; i < components.size(); i++) {
            out.components.add(components.get(i) - v.components.get(i));
        }
        return out;
    }


    public double len() {
        return Math.sqrt(components.stream().mapToDouble(v -> (double) v * v).sum());
    }

    public Vector add(double v) {
        return new Vector(components.stream().map(c -> c + v).toList());
    }

    public Vector mul(double s) {
        return new Vector(components.stream().map(c -> c * s).toList());
    }

    public Vector div(double s) {
        return new Vector(components.stream().map(c -> c / s).toList());
    }

    public static Vector getCentroid(List<Vector> vectors) {
        var sum = new Vector(vectors.getFirst().getComponents());
        for (int i = 1; i < vectors.size(); i++) {
            sum = sum.add(vectors.get(i));
        }
        return sum.div(vectors.size());
    }
}
