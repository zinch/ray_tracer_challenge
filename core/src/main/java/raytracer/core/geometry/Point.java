package raytracer.core.geometry;

public final class Point extends Tuple {
    Point(double x, double y, double z) {
        super(x, y, z, 1.0);
    }

    public Point plus(Vector v) {
        return new Point(x + v.x, y + v.y, z + v.z);
    }

    public Point minus(Vector v) {
        return plus(new Vector(-v.x, -v.y, -v.z));
    }

    public Vector minus(Point p) {
        return new Vector(x - p.x, y - p.y, z - p.z);
    }
}
