package task5;

import Vector.Vector;

public class Observation {
    public Vector v;
    public int output;

    public Observation(Vector v, int output) {
        this.v = v;
        this.output = output;
    }

    public int getOutput() {
        return output;
    }
}