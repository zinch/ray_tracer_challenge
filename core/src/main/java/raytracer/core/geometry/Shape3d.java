package raytracer.core.geometry;

import raytracer.core.Ray;
import raytracer.core.light.Material;

import java.util.List;

public interface Shape3d {
    Vector normalAt(Point p);

    Material material();

    Ray.Intersections intersect(Ray ray);
}
