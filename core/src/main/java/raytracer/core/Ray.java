package raytracer.core;

import raytracer.core.geometry.Point;
import raytracer.core.geometry.Vector;

public record Ray(Point origin, Vector direction) {
    public Point positionAt(double t) {
        return direction.times(t).plus(origin);
    }
}