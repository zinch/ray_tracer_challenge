package raytracer.core;

import raytracer.core.geometry.Point;
import raytracer.core.geometry.Sphere;
import raytracer.core.geometry.Vector;

import java.util.Optional;

public record Ray(Point origin, Vector direction) {

    public static final class Intersection {
        public final double t;
        public final Object object;

        Intersection(double t, Object obj) {
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
    }

    public static final class Intersections {
        private final Intersection[] values;

        Intersections(Intersection... is) {
            // Intersections must be sorted by increasing t
            this.values = is;
        }

        public int count() {
            return values.length;
        }

        public Intersection get(int index) {
            return values[index];
        }

        public Optional<Intersection> hit() {
            var result = Optional.<Intersection>empty();
            for (var intersection : values) {
                if (intersection.t >= 0) {
                    return Optional.of(intersection);
                }
            }
            return result;
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