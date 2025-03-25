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

    public double dot(Vector v) {
        double out = 0;
        for (int i = 0; i < components.size(); i++) {
            out += components.get(i) * v.components.get(i);
        }
        return out;
    }

    public Vector add(Vector v) {
        Vector out = new Vector(components);
        for (int i = 0; i < components.size(); i++) {
            out.components.add(components.get(i) + v.components.get(i));
        }
        return out;
    }

    public Vector add(double v) {
        return new Vector(components.stream().map(c -> c + v).toList());
    }
}
