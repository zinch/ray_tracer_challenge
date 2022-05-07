package raytracer.core;

import raytracer.core.geometry.Point;
import raytracer.core.geometry.Shape3d;
import raytracer.core.geometry.Sphere;
import raytracer.core.graphics.Color;
import raytracer.core.light.Material;
import raytracer.core.light.PointLight;

import java.util.List;

import static raytracer.core.geometry.Matrix.scaling;

public class World {

    public static final World DEFAULT;

    static {
        var light = new PointLight(new Point(-10, 10, -10), Color.WHITE);
        var firstSphere = new Sphere().withMaterial(Material.builder()
                .diffuse(0.7)
                .specular(0.2)
                .color(new Color(0.8, 1.0, 0.6))
                .build());
        var objects = List.of(firstSphere, new Sphere(scaling(0.5, 0.5, 0.5)));
        DEFAULT = new World(List.of(light), objects);
    }

    private final List<PointLight> lights;
    private final List<Shape3d> objects;

    public World(List<PointLight> lights, List<? extends Shape3d> objects) {
        this.lights = List.copyOf(lights);
        this.objects = List.copyOf(objects);
    }

    public List<Shape3d> objects() {
        return objects;
    }

    public World withObjects(List<? extends Shape3d> objects) {
        return new World(lights, objects);
    }

    public List<PointLight> lights() {
        return lights;
    }

    public World withLights(List<PointLight> lights) {
        return new World(lights, objects);
    }

    public Color shadeHit(Ray.Computations cs) {
        var color = Color.BLACK;
        for (PointLight light : lights) {
            color = color.plus(light.lightningAt(cs.point(), cs.object().material(), cs.normalVector(), cs.eyeVector()));
        }
        return color;
    }

    public Color colorAt(Ray ray) {
        var xs = ray.intersect(this);
        return xs.hit()
                .map(Intersection::prepareComputations)
                .map(this::shadeHit)
                .orElse(Color.BLACK);
    }
}
