package raytracer.core.geometry;

import static raytracer.core.geometry.Tuple.point;

public class Sphere {
    private final Point origin;

    public Sphere() {
        this.origin = point(0, 0, 0);
    }

    public Point origin() {
        return origin;
    }
}
