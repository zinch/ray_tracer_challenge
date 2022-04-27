package raytracer.core;

public final class Vector extends Tuple {
    Vector(double x, double y, double z) {
        super(x, y, z, 0.0);
    }

    public Point plus(Point p) {
        return new Point(x + p.x, y + p.y, z + p.z);
    }

    public Vector plus(Vector v) {
        return new Vector(x + v.x, y + v.y, z + v.z);
    }

    public Vector minus(Vector v) {
        return plus(new Vector(-v.x, -v.y, -v.z));
    }
}
