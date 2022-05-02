package raytracer.core;

import org.junit.jupiter.api.Test;
import raytracer.core.geometry.Sphere;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static raytracer.core.geometry.Tuple.point;
import static raytracer.core.geometry.Tuple.vector;

class RayTest {
    @Test
    void creating_and_querying_a_ray() {
        var origin = point(1, 2, 3);
        var direction = vector(4, 5, 6);
        var ray = new Ray(origin, direction);
        assertThat(ray.origin()).isEqualTo(point(1, 2, 3));
        assertThat(ray.direction()).isEqualTo(vector(4, 5, 6));
    }

    @Test
    void computing_a_point_from_a_distance() {
        var ray = new Ray(point(2, 3, 4), vector(1, 0, 0));
        assertThat(ray.positionAt(0)).isEqualTo(point(2, 3, 4));
        assertThat(ray.positionAt(1)).isEqualTo(point(3, 3, 4));
        assertThat(ray.positionAt(-1)).isEqualTo(point(1, 3, 4));
        assertThat(ray.positionAt(2.5)).isEqualTo(point(4.5, 3, 4));
    }

    @Test
    void a_ray_intersects_a_sphere_at_two_points() {
        var ray = new Ray(point(0, 0, -5), vector(0, 0, 1));
        var sphere = new Sphere();

        var intersections = ray.intersect(sphere);

        assertThat(intersections).hasSize(2);
        assertThat(Arrays.equals(new double[]{4.0, 6.0}, intersections)).isTrue();
    }

    @Test
    void a_ray_intersects_a_sphere_at_a_tangent() {
        var ray = new Ray(point(0, 1, -5), vector(0, 0, 1));
        var sphere = new Sphere();

        var intersections = ray.intersect(sphere);

        assertThat(intersections).hasSize(2);
        assertThat(Arrays.equals(new double[]{5.0, 5.0}, intersections)).isTrue();
    }

    @Test
    void a_ray_misses_a_sphere() {
        var ray = new Ray(point(0, 2, -5), vector(0, 0, 1));
        var sphere = new Sphere();

        var intersections = ray.intersect(sphere);

        assertThat(intersections).isEmpty();
    }

    @Test
    void a_ray_originates_inside_a_sphere() {
        var ray = new Ray(point(0, 0, 0), vector(0, 0, 1));
        var sphere = new Sphere();

        var intersections = ray.intersect(sphere);

        assertThat(intersections).hasSize(2);
        assertThat(Arrays.equals(new double[]{-1.0, 1.0}, intersections)).isTrue();
    }

    @Test
    void a_sphere_is_behind_a_ray() {
        var ray = new Ray(point(0, 0, 5), vector(0, 0, 1));
        var sphere = new Sphere();

        var intersections = ray.intersect(sphere);

        assertThat(intersections).hasSize(2);
        assertThat(Arrays.equals(new double[]{-6.0, -4.0}, intersections)).isTrue();
    }
}