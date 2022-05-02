package raytracer.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raytracer.core.geometry.Sphere;

import static org.assertj.core.api.Assertions.assertThat;
import static raytracer.core.geometry.Tuple.point;
import static raytracer.core.geometry.Tuple.vector;

class RayTest {

    private Sphere sphere;

    @BeforeEach
    void setUp() {
        sphere = new Sphere();
    }

    @Test
    void an_intersection_encapsulates_t_and_object() {
        var intersection = new Ray.Intersection(3.5, sphere);
        assertThat(intersection.t).isEqualTo(3.5);
        assertThat(intersection.object).isSameAs(sphere);
    }

    @Test
    void aggregating_intersections() {
        var i1 = new Ray.Intersection(1, sphere);
        var i2 = new Ray.Intersection(2, sphere);

        var xs = new Ray.Intersections(i1, i2);

        assertThat(xs.count()).isEqualTo(2);
        assertThat(xs.get(0).t).isEqualTo(1);
        assertThat(xs.get(1).t).isEqualTo(2);
    }

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

        assertThat(intersections.count()).isEqualTo(2);
        assertThat(intersections.get(0).object).isSameAs(sphere);
        assertThat(intersections.get(0).t).isEqualTo(4.0);
        assertThat(intersections.get(1).object).isSameAs(sphere);
        assertThat(intersections.get(1).t).isEqualTo(6.0);
    }

    @Test
    void a_ray_intersects_a_sphere_at_a_tangent() {
        var ray = new Ray(point(0, 1, -5), vector(0, 0, 1));
        var sphere = new Sphere();

        var intersections = ray.intersect(sphere);

        assertThat(intersections.count()).isEqualTo(2);
        assertThat(intersections.get(0).t).isEqualTo(5.0);
        assertThat(intersections.get(1).t).isEqualTo(5.0);
    }

    @Test
    void a_ray_misses_a_sphere() {
        var ray = new Ray(point(0, 2, -5), vector(0, 0, 1));
        var sphere = new Sphere();

        var intersections = ray.intersect(sphere);

        assertThat(intersections.count()).isEqualTo(0);
    }

    @Test
    void a_ray_originates_inside_a_sphere() {
        var ray = new Ray(point(0, 0, 0), vector(0, 0, 1));
        var sphere = new Sphere();

        var intersections = ray.intersect(sphere);

        assertThat(intersections.count()).isEqualTo(2);
        assertThat(intersections.get(0).t).isEqualTo(-1.0);
        assertThat(intersections.get(1).t).isEqualTo(1.0);
    }

    @Test
    void a_sphere_is_behind_a_ray() {
        var ray = new Ray(point(0, 0, 5), vector(0, 0, 1));
        var sphere = new Sphere();

        var intersections = ray.intersect(sphere);

        assertThat(intersections.count()).isEqualTo(2);
        assertThat(intersections.get(0).t).isEqualTo(-6.0);
        assertThat(intersections.get(1).t).isEqualTo(-4.0);
    }
}