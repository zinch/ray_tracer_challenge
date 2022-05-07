package raytracer.core;

import org.junit.jupiter.api.Test;
import raytracer.core.geometry.Matrix;
import raytracer.core.geometry.Point;
import raytracer.core.geometry.Sphere;
import raytracer.core.geometry.Vector;
import raytracer.core.graphics.Color;
import raytracer.core.light.Material;
import raytracer.core.light.PointLight;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WorldTest {

    private final Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));

    @Test
    void the_default_world() {
        var w = World.DEFAULT;

        assertThat(w.lights()).hasSize(1);
        var light = w.lights().get(0);
        assertThat(light.position()).isEqualTo(new Point(-10, 10, -10));
        assertThat(light.intensity()).isEqualTo(Color.WHITE);

        var objects = w.objects();
        assertThat(objects).hasSize(2);
        assertThat(objects.get(0)).isInstanceOf(Sphere.class);
        var firstSphere = (Sphere) objects.get(0);
        assertThat(firstSphere.material()).isEqualTo(Material.builder()
                .color(new Color(0.8, 1.0, 0.6))
                .diffuse(0.7)
                .specular(0.2)
                .build());

        assertThat(objects.get(1)).isInstanceOf(Sphere.class);
        Sphere secondSphere = (Sphere) objects.get(1);
        assertThat(secondSphere.transform()).isEqualTo(new Matrix(new double[][]{
                {0.5, 0, 0, 0},
                {0, 0.5, 0, 0},
                {0, 0, 0.5, 0},
                {0, 0, 0, 1}
        }));
    }

    @Test
    void intersect_a_world_with_a_ray() {
        var xs = ray.intersect(World.DEFAULT);
        assertThat(xs.count()).isEqualTo(4);
        assertThat(xs.get(0).t).isEqualTo(4);
        assertThat(xs.get(1).t).isEqualTo(4.5);
        assertThat(xs.get(2).t).isEqualTo(5.5);
        assertThat(xs.get(3).t).isEqualTo(6);
    }

    @Test
    void shading_an_intersection() {
        var xs = ray.intersect(World.DEFAULT);
        var intersection = xs.get(0);
        var comps = intersection.prepareComputations();
        assertThat(World.DEFAULT.shadeHit(comps)).isEqualTo(new Color(0.38066, 0.47583, 0.2855));
    }

    @Test
    void shading_an_intersection_from_the_inside() {
        var light = new PointLight(new Point(0, 0.25, 0), new Color(1, 1, 1));
        var world = World.DEFAULT.withLights(List.of(light));
        var ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        var xs = ray.intersect(world);
        var intersection = xs.get(2);
        var comps = intersection.prepareComputations();
        assertThat(world.shadeHit(comps)).isEqualTo(new Color(0.90498, 0.90498, 0.90498));
    }

    @Test
    void the_color_when_a_ray_misses() {
        var ray = new Ray(new Point(0, 0, -5), new Vector(0, 1, 0));
        assertThat(World.DEFAULT.colorAt(ray)).isEqualTo(Color.BLACK);
    }

    @Test
    void the_color_when_a_ray_hits() {
        assertThat(World.DEFAULT.colorAt(ray)).isEqualTo(new Color(0.38066, 0.47583, 0.2855));
    }

    @Test
    void the_color_with_an_intersection_behind_the_ray() {
        var firstSphere = (Sphere) World.DEFAULT.objects().get(0);
        var secondSphere = (Sphere) World.DEFAULT.objects().get(1);

        var lightedFirstSphere = firstSphere.withMaterial(
                Material.builder(firstSphere.material()).ambient(1).build());
        var lightedSecondSphere = secondSphere.withMaterial(
                Material.builder(secondSphere.material()).ambient(1).build());

        var objects = List.of(lightedFirstSphere, lightedSecondSphere);
        var world = World.DEFAULT.withObjects(objects);

        var ray = new Ray(new Point(0, 0, 0.75), new Vector(0, 0, -1));
        assertThat(world.colorAt(ray)).isEqualTo(secondSphere.material().color());
    }
}