package raytracer.core;

import raytracer.core.geometry.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public record Ray(Point origin, Vector direction) {

    public static final class Intersection {

        public final double t;

        public final Shape3d object;

        Intersection(double t, Shape3d obj) {
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
            this.values = is;
        }

        public int count() {
            return values.length;
        }

        public Intersection get(int index) {
            return values[index];
        }

        public Optional<Intersection> hit() {
            Intersection hit = null;
            var result = Optional.<Intersection>empty();
            for (var intersection : values) {
                if (intersection.t < 0) {
                    continue;
                }
                if (hit == null || hit.t > intersection.t) {
                    hit = intersection;
                }
            }
            return Optional.ofNullable(hit);
        }

        public Intersections compose(Intersections xs) {
            var newValues = new Intersection[values.length + xs.values.length];
            System.arraycopy(xs.values, 0, newValues, 0, xs.values.length);
            System.arraycopy(values, 0, newValues, xs.values.length, values.length);
            Arrays.sort(newValues, Comparator.comparingDouble(i -> i.t));
            return new Intersections(newValues);
        }
    }

    public Point positionAt(double t) {
        return direction.times(t).plus(origin);
    }

    public Intersections intersect(Sphere s) {
        var transformedRay = this.transform(s.transform().inverse());

        var sphereToRay = transformedRay.origin.minus(s.origin());
        var a = transformedRay.direction.dot(transformedRay.direction);
        var b = 2 * transformedRay.direction.dot(sphereToRay);
        var c = sphereToRay.dot(sphereToRay) - 1;
        var discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return new Intersections();
        }
        var t1 = (-b - Math.sqrt(discriminant)) / (2 * a);
        var t2 = (-b + Math.sqrt(discriminant)) / (2 * a);
        return new Intersections(new Intersection(t1, s), new Intersection(t2, s));
    }

    public Intersections intersect(World world) {
        var intersections = new Intersections();
        for (Shape3d shape : world.objects()) {
            var xs = shape.intersect(this);
            intersections = intersections.compose(xs);
        }
        return intersections;
    }

    public Ray transform(Matrix m) {
        return new Ray(m.times(origin), m.times(direction));
    }
}