package raytracer.core.geometry;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static raytracer.core.geometry.Tuple.point;
import static raytracer.core.geometry.Tuple.vector;

class SphereTest {
    private final Sphere sphere = new Sphere();

    @Test
    void default_transformation_of_a_sphere() {
        assertThat(sphere.transform()).isEqualTo(Matrix.identity());
    }

    @Test
    void changing_a_transformation_of_a_sphere() {
        var t = Matrix.translation(2, 3, 4);
        var s = new Sphere(t);
        assertThat(s.transform()).isEqualTo(Matrix.translation(2, 3, 4));
    }

    @Test
    void the_normal_on_a_sphere_at_a_point_on_the_x_axis() {
        assertThat(sphere.normalAt(point(1, 0, 0))).isEqualTo(vector(1, 0, 0));
    }

    @Test
    void the_normal_on_a_sphere_at_a_point_on_the_y_axis() {
        assertThat(sphere.normalAt(point(0, 1, 0))).isEqualTo(vector(0, 1, 0));
    }

    @Test
    void the_normal_on_a_sphere_at_a_point_on_the_z_axis() {
        assertThat(sphere.normalAt(point(0, 0, 1))).isEqualTo(vector(0, 0, 1));
    }

    @Test
    void the_normal_is_a_normalized_vector() {
        var normal = sphere.normalAt(point(Math.sqrt(3) / 3, Math.sqrt(3) / 3, Math.sqrt(3) / 3));
        assertThat(normal).isEqualTo(normal.normalize());
    }
}