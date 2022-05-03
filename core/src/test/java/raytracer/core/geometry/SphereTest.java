package raytracer.core.geometry;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SphereTest {
    @Test
    void default_transformation_of_a_sphere() {
        var s = new Sphere();
        assertThat(s.transform()).isEqualTo(Matrix.identity());
    }

    @Test
    void changing_a_transformation_of_a_sphere() {
        var t = Matrix.translation(2, 3, 4);
        var s = new Sphere(t);
        assertThat(s.transform()).isEqualTo(Matrix.translation(2, 3, 4));
    }
}