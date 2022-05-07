package raytracer.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import raytracer.core.geometry.Point;
import raytracer.core.geometry.Sphere;
import raytracer.core.geometry.Vector;

import static org.assertj.core.api.Assertions.assertThat;
import static raytracer.core.geometry.Matrix.scaling;
import static raytracer.core.geometry.Matrix.translation;

class RayTest {

    private final Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
    private Sphere sphere;

    @BeforeEach
    void setUp() {
        sphere = new Sphere();
    }

    @Test
    void an_intersection_encapsulates_t_and_object() {
        var intersection = new Intersection(ray, 3.5, sphere);
        assertThat(intersection.t).isEqualTo(3.5);
        assertThat(intersection.object).isSameAs(sphere);
    }

    @Test
    void aggregating_intersections() {
        var i1 = new Intersection(ray, 1, sphere);
        var i2 = new Intersection(ray, 2, sphere);

        var xs = new Ray.Intersections(i1, i2);

        assertThat(xs.count()).isEqualTo(2);
        assertThat(xs.get(0).t).isEqualTo(1);
        assertThat(xs.get(1).t).isEqualTo(2);
    }

    @Test
    void creating_and_querying_a_ray() {
        var origin = new Point(1, 2, 3);
        var direction = new Vector(4, 5, 6);
        var ray = new Ray(origin, direction);
        assertThat(ray.origin()).isEqualTo(new Point(1, 2, 3));
        assertThat(ray.direction()).isEqualTo(new Vector(4, 5, 6));
    }

    @Test
    void computing_a_point_from_a_distance() {
        var ray = new Ray(new Point(2, 3, 4), new Vector(1, 0, 0));
        assertThat(ray.positionAt(0)).isEqualTo(new Point(2, 3, 4));
        assertThat(ray.positionAt(1)).isEqualTo(new Point(3, 3, 4));
        assertThat(ray.positionAt(-1)).isEqualTo(new Point(1, 3, 4));
        assertThat(ray.positionAt(2.5)).isEqualTo(new Point(4.5, 3, 4));
    }

    @Test
    void a_ray_intersects_a_sphere_at_two_points() {
        var ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));

        var intersections = ray.intersect(sphere);

        assertThat(intersections.count()).isEqualTo(2);
        assertThat(intersections.get(0).object).isSameAs(sphere);
        assertThat(intersections.get(0).t).isEqualTo(4.0);
        assertThat(intersections.get(1).object).isSameAs(sphere);
        assertThat(intersections.get(1).t).isEqualTo(6.0);
    }

    @Test
    void a_ray_intersects_a_sphere_at_a_tangent() {
        var ray = new Ray(new Point(0, 1, -5), new Vector(0, 0, 1));
        var sphere = new Sphere();

        var intersections = ray.intersect(sphere);

        assertThat(intersections.count()).isEqualTo(2);
        assertThat(intersections.get(0).t).isEqualTo(5.0);
        assertThat(intersections.get(1).t).isEqualTo(5.0);
    }

    @Test
    void a_ray_misses_a_sphere() {
        var ray = new Ray(new Point(0, 2, -5), new Vector(0, 0, 1));
        var intersections = ray.intersect(sphere);
        assertThat(intersections.count()).isEqualTo(0);
    }

    @Test
    void a_ray_originates_inside_a_sphere() {
        var ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));

        var intersections = ray.intersect(sphere);

        assertThat(intersections.count()).isEqualTo(2);
        assertThat(intersections.get(0).t).isEqualTo(-1.0);
        assertThat(intersections.get(1).t).isEqualTo(1.0);
    }

    @Test
    void a_sphere_is_behind_a_ray() {
        var ray = new Ray(new Point(0, 0, 5), new Vector(0, 0, 1));

        var intersections = ray.intersect(sphere);

        assertThat(intersections.count()).isEqualTo(2);
        assertThat(intersections.get(0).t).isEqualTo(-6.0);
        assertThat(intersections.get(1).t).isEqualTo(-4.0);
    }

    @Nested
    @DisplayName("Hit")
    class HitTest {
        @Test
        void when_all_intersections_have_positive_t() {
            var i1 = new Intersection(ray, 1, sphere);
            var i2 = new Intersection(ray, 2, sphere);
            var xs = new Ray.Intersections(i2, i1);
            var hit = xs.hit();
            assertThat(hit).contains(i1);
        }

        @Test
        void when_some_intersections_have_negative_t() {
            var i1 = new Intersection(ray, -1, sphere);
            var i2 = new Intersection(ray, 1, sphere);
            var xs = new Ray.Intersections(i1, i2);
            var hit = xs.hit();
            assertThat(hit).contains(i2);
        }

        @Test
        void when_all_intersections_have_negative_t() {
            var i1 = new Intersection(ray, -2, sphere);
            var i2 = new Intersection(ray, -1, sphere);
            var xs = new Ray.Intersections(i2, i1);
            var hit = xs.hit();
            assertThat(hit).isEmpty();
        }

        @Test
        void is_always_the_lowest_nonnegative_intersection() {
            var i1 = new Intersection(ray, 5, sphere);
            var i2 = new Intersection(ray, 7, sphere);
            var i3 = new Intersection(ray, -3, sphere);
            var i4 = new Intersection(ray, 2, sphere);
            var xs = new Ray.Intersections(i1, i2, i3, i4);
            var hit = xs.hit();
            assertThat(hit).contains(i4);
        }

        @Test
        void when_an_intersection_occurs_on_the_outside() {
            var shape = new Sphere();
            var intersection = new Intersection(ray, 4, shape);

            var comps = intersection.prepareComputations();

            assertThat(comps.inside()).isFalse();
        }

        @Test
        void when_an_intersection_occurs_on_the_inside() {
            var ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
            var shape = new Sphere();
            var intersection = new Intersection(ray, 1, shape);

            var comps = intersection.prepareComputations();

            assertThat(comps.point()).isEqualTo(new Point(0, 0, 1));
            assertThat(comps.eyeVector()).isEqualTo(new Vector(0, 0, -1));
            assertThat(comps.normalVector()).isEqualTo(new Vector(0, 0, -1));
            assertThat(comps.inside()).isTrue();
        }
    }

    @Test
    void translating_a_ray() {
        var ray = new Ray(new Point(1, 2, 3), new Vector(0, 1, 0));
        var m = translation(3, 4, 5);
        var transformedRay = ray.transform(m);
        assertThat(transformedRay.origin()).isEqualTo(new Point(4, 6, 8));
        assertThat(transformedRay.direction()).isEqualTo(new Vector(0, 1, 0));
    }

    @Test
    void scaling_a_ray() {
        var ray = new Ray(new Point(1, 2, 3), new Vector(0, 1, 0));
        var m = scaling(2, 3, 4);
        var transformedRay = ray.transform(m);
        assertThat(transformedRay.origin()).isEqualTo(new Point(2, 6, 12));
        assertThat(transformedRay.direction()).isEqualTo(new Vector(0, 3, 0));
    }

    @Test
    void intersecting_a_scaled_sphere_with_a_ray() {
        var sphere = new Sphere(scaling(2, 2, 2));

        var xs = ray.intersect(sphere);

        assertThat(xs.count()).isEqualTo(2);
        assertThat(xs.get(0).t).isEqualTo(3);
        assertThat(xs.get(1).t).isEqualTo(7);
    }

    @Test
    void intersecting_a_translated_sphere_with_a_ray() {
        var sphere = new Sphere(translation(5, 0, 0));

        var xs = ray.intersect(sphere);

        assertThat(xs.count()).isEqualTo(0);
    }

    @Test
    void precomputing_the_state_of_an_intersection() {
        var shape = new Sphere();
        var intersection = new Intersection(ray, 4, shape);

        var computations = intersection.prepareComputations();

        assertThat(computations.t()).isEqualTo(4);
        assertThat(computations.object()).isSameAs(intersection.object);
        assertThat(computations.point()).isEqualTo(new Point(0, 0, -1));
        assertThat(computations.eyeVector()).isEqualTo(new Vector(0, 0, -1));
        assertThat(computations.normalVector()).isEqualTo(new Vector(0, 0, -1));
    }
}