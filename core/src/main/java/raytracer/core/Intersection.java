package raytracer.core;

import raytracer.core.geometry.Point;
import raytracer.core.geometry.Shape3d;
import raytracer.core.geometry.Vector;

public final class Intersection {

    private final Ray ray;
    public final double t;

    public final Shape3d object;

    Intersection(Ray r, double t, Shape3d obj) {
        this.ray = r;
        this.t = t;
        this.object = obj;
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "t=" + t +
                ", object=" + object +
                '}';
    }

    public Computations prepareComputations() {
        var point = ray.positionAt(t);
        var eyeVector = ray.direction().negate();
        var normalVector = object.normalAt(point);
        var inside = normalVector.dot(eyeVector) < 0;

        return new Computations(t, object, point, eyeVector,
                inside ? normalVector.negate() : normalVector, inside);
    }

    public record Computations(double t, Shape3d object, Point point, Vector eyeVector, Vector normalVector,
                               boolean inside) {
    }
}
