package raytracer.core.geometry;

import static raytracer.core.geometry.Matrix.*;
import static raytracer.core.geometry.Tuple.point;

public class Sphere {
    private final Point origin = point(0, 0, 0);
    private final Matrix transform;

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
}
