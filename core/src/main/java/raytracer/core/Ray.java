package raytracer.core;

import raytracer.core.geometry.Point;
import raytracer.core.geometry.Sphere;
import raytracer.core.geometry.Vector;

public record Ray(Point origin, Vector direction) {

    public static final class Intersection {
        public final double t;
        public final Object object;

        public Intersection(double t, Object obj) {
            this.t = t;
            this.object = obj;
        }
    }

    public static final class Intersections {
        private final Intersection[] values;

        public Intersections(Intersection... is) {
            this.values = is;
        }

        public int count() {
            return values.length;
        }

        public Intersection get(int index) {
            return values[index];
        }
    }

    public Point positionAt(double t) {
        return direction.times(t).plus(origin);
    }

    public Intersections intersect(Sphere s) {
        var sphereToRay = origin.minus(s.origin());
        var a = direction.dot(direction);
        var b = 2 * direction.dot(sphereToRay);
        var c = sphereToRay.dot(sphereToRay) - 1;
        var discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return new Intersections();
        }
        var t1 = (-b - Math.sqrt(discriminant)) / (2 * a);
        var t2 = (-b + Math.sqrt(discriminant)) / (2 * a);
        return new Intersections(new Intersection(t1, s), new Intersection(t2, s));
    }

}