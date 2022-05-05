package raytracer.core.geometry;

import raytracer.core.light.Material;

public interface Shape3d {
    Vector normalAt(Point p);

    Material material();
}
