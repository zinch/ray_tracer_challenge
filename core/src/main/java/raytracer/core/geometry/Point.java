package raytracer.core.geometry;

public record Point(double x, double y, double z) implements Tuple {

    public Point plus(Vector v) {
        return new Point(x + v.x(), y + v.y(), z + v.z());
    }

    public Point minus(Vector v) {
        return plus(v.negate());
    }

    public Vector minus(Point p) {
        return new Vector(x - p.x, y - p.y, z - p.z);
    }

    @Override
    public double w() {
        return 1d;
    }

    @Override
    public boolean equals(Object o) {
        return isEqualTo(o);
    }

    @Override
    public String toString() {
        return asString();
    }
}
