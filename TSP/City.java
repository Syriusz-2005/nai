package TSP;

import Vector.Vector;

public class City {
    public char id;
    public double x;
    public double y;

    public City(char id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    double distanceTo(City other) {
        return Vector.dist(pos(), other.pos());
    }

    public Vector pos() {
        return new Vector(x, y);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                '}';
    }
}
