package raytracer.core;

import raytracer.core.geometry.Point;
import raytracer.core.geometry.Sphere;
import raytracer.core.geometry.Vector;

import java.util.List;

public record Ray(Point origin, Vector direction) {
    public Point positionAt(double t) {
        return direction.times(t).plus(origin);
    }

    public double[] intersect(Sphere s) {
        var sphereToRay = origin.minus(s.origin());
        var a = direction.dot(direction);
        var b = 2 * direction.dot(sphereToRay);
        var c = sphereToRay.dot(sphereToRay) - 1;
        var discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return new double[0];
        }
        var t1 = (-b - Math.sqrt(discriminant)) / (2 * a);
        var t2 = (-b + Math.sqrt(discriminant)) / (2 * a);
        return new double[]{t1, t2};
    }

}