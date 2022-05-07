package raytracer.core;

import raytracer.core.geometry.Matrix;
import raytracer.core.geometry.Shape3d;
import raytracer.core.geometry.Sphere;
import raytracer.core.graphics.Color;
import raytracer.core.light.Material;
import raytracer.core.light.PointLight;

import java.util.List;
import java.util.Objects;

import static raytracer.core.geometry.Matrix.scaling;
import static raytracer.core.geometry.Tuple.point;

public class World {

    public static final World DEFAULT;

    static {
        var light = new PointLight(point(-10, 10, -10), Color.WHITE);
        var firstSphere = new Sphere().withMaterial(Material.builder()
                .diffuse(0.7)
                .specular(0.2)
                .color(new Color(0.8, 1.0, 0.6))
                .build());
        var objects = List.of(firstSphere, new Sphere(scaling(0.5, 0.5, 0.5)));
        DEFAULT = new World(light, objects);
    }

    private final PointLight light;
    private final List<Shape3d> objects;

    public World(PointLight light, List<? extends Shape3d> objects) {
        this.light = Objects.requireNonNull(light);
        this.objects = List.copyOf(objects);
    }

    public List<Shape3d> objects() {
        return objects;
    }

    public PointLight light() {
        return light;
    }

    public World withLight(PointLight light) {
        return new World(light, objects);
    }

    public Color shadeHit(Ray.Computations comps) {
        return light.lightningAt(comps.point(), comps.object().material(), comps.normalVector(), comps.eyeVector());
    }
}
