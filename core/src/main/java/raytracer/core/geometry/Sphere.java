package raytracer.core.geometry;

import static raytracer.core.geometry.Matrix.*;
import static raytracer.core.geometry.Tuple.point;

public class Sphere {
    private static final Point ORIGIN = point(0, 0, 0);
    private final Point origin = point(0, 0, 0);
    private final Matrix transform;
    private Matrix inverse;
    private Matrix inverseTransposed;

    public Sphere() {
        this.transform = identity();
    }

    public Sphere(Matrix t) {
        this.transform = t;
    }

    public Point origin() {
        return origin;
    }

    public Matrix transform() {
        return transform;
    }

    public Vector normalAt(Point point) {
        var objectPoint = inverse().times(point);

        var objectNormal = objectPoint.minus(ORIGIN);
        return inverseTransposed().times(objectNormal).normalize();
    }

    private Matrix inverse() {
        if (inverse != null) {
            return inverse;
        }
        inverse = transform.inverse();
        return inverse;
    }

    private Matrix inverseTransposed() {
        if (inverseTransposed != null) {
            return inverseTransposed;
        }
        inverseTransposed = inverse().transpose();
        return inverseTransposed;
    }
}
