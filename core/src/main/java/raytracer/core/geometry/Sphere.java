package raytracer.core.geometry;

import raytracer.core.Ray;
import raytracer.core.light.Material;

public class Sphere implements Shape3d {
    private static final Point ORIGIN = new Point(0, 0, 0);
    private final Point origin = new Point(0, 0, 0);
    private final Matrix transform;
    private final Material material;
    private Matrix inverse;
    private Matrix inverseTransposed;

    public Sphere() {
        this(Matrix.IDENTITY, Material.DEFAULT);
    }

    public Sphere(Matrix t) {
        this(t, Material.DEFAULT);
    }

    public Sphere(Matrix t, Material m) {
        this.transform = t;
        this.material = m;
    }

    public Point origin() {
        return origin;
    }

    public Matrix transform() {
        return transform;
    }

    public Sphere withMaterial(Material material) {
        return new Sphere(transform, material);
    }

    @Override
    public Material material() {
        return material;
    }

    @Override
    public Vector normalAt(Point point) {
        var objectPoint = inverse().times(point);

        var objectNormal = objectPoint.minus(ORIGIN);
        return inverseTransposed().times(objectNormal).normalize();
    }

    @Override
    public Ray.Intersections intersect(Ray ray) {
        return ray.intersect(this);
    }

    private Matrix inverse() {
        if (inverse != null) {
            return inverse;
        }
        inverse = transform.inverse();
        return inverse;
    }

    private Matrix inverseTransposed() {
        if (inverseTransposed != null) {
            return inverseTransposed;
        }
        inverseTransposed = inverse().transpose();
        return inverseTransposed;
    }
}
