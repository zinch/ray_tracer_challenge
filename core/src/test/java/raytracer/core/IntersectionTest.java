package raytracer.core;

import org.junit.jupiter.api.Test;
import raytracer.core.geometry.Point;
import raytracer.core.geometry.Sphere;
import raytracer.core.geometry.Vector;

import static org.assertj.core.api.Assertions.assertThat;

class IntersectionTest {
    private final Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));

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

    @Test
    void when_an_intersection_occurs_on_the_outside() {
        var shape = new Sphere();
        var intersection = new Intersection(ray, 4, shape);

        var comps = intersection.prepareComputations();

        assertThat(comps.inside()).isFalse();
    }
}