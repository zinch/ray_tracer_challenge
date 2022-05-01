package raytracer.core.geometry;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static raytracer.core.geometry.Tuple.point;
import static raytracer.core.geometry.Tuple.vector;

public class MatrixTransformationsTest {
    private final Matrix translation = Matrix.translation(5, -3, 2);
    private final Matrix scaling = Matrix.scaling(2, 3, 4);

    @Test
    void multiplying_by_a_translation_matrix() {
        var p = point(-3, 4, 5);
        assertThat(translation.times(p)).isEqualTo(point(2, 1, 7));
    }

    @Test
    void multiplying_by_the_inverse_of_a_translation_matrix() {
        var inverse = translation.inverse();
        var p = point(2, 1, 7);
        assertThat(inverse.times(p)).isEqualTo(point(-3, 4, 5));
    }

    @Test
    void translation_does_not_affect_vectors() {
        var v = vector(-3, 4, 5);
        assertThat(translation.times(v)).isEqualTo(vector(-3, 4, 5));
    }

    @Test
    void applying_a_scaling_matrix_to_a_point() {
        var p = point(-4, 6, 8);
        assertThat(scaling.times(p)).isEqualTo(point(-8, 18, 32));
    }

    @Test
    void applying_a_scaling_matrix_to_a_vector() {
        var v = vector(-4, 6, 8);
        assertThat(scaling.times(v)).isEqualTo(vector(-8, 18, 32));
    }

    @Test
    void multiplying_by_the_inverse_of_a_scaling_matrix() {
        var inv = scaling.inverse();
        var v = vector(-4, 6, 8);
        assertThat(inv.times(v)).isEqualTo(vector(-2, 2, 2));
    }
}
