package raytracer.core.light;

import raytracer.core.geometry.Point;
import raytracer.core.graphics.Color;

public record PointLight(Point position, Color intensity) {
}
