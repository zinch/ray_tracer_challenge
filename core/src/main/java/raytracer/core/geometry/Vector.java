package raytracer.core.geometry;

public final class Vector extends Tuple {
    Vector(double x, double y, double z) {
        super(x, y, z, 0.0);
    }

    public Point plus(Point p) {
        return p.plus(this);
    }

    public Vector plus(Vector v) {
        return new Vector(x + v.x, y + v.y, z + v.z);
    }

    public Vector minus(Vector v) {
        return plus(new Vector(-v.x, -v.y, -v.z));
    }

    public Vector negate() {
        return new Vector(-x, -y, -z);
    }

    public Vector times(double scalar) {
        return new Vector(scalar * x, scalar * y, scalar * z);
    }

    public Vector divide(double scalar) {
        return new Vector(x / scalar, y / scalar, z / scalar);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vector normalize() {
        var m = magnitude();
        return new Vector(x / m, y / m, z / m);
    }

    public double dot(Vector v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public Vector cross(Vector v) {
        return new Vector(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x
        );
    }

    public Vector reflect(Vector normal) {
        return this.minus(normal.times(2 * this.dot(normal)));
    }
}
