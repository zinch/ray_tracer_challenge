package raytracer.core.geometry;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static raytracer.core.geometry.Tuple.point;

public class MatrixTransformationsTest {
    @Test
    void multiplying_by_a_translation_matrix() {
        var transform = Matrix.translation(5, -3, 2);
        var p = point(-3, 4, 5);
        assertThat(transform.times(p)).isEqualTo(point(2, 1, 7));
    }

    @Test
    void multiplying_by_the_inverse_of_a_translation_matrix() {
        var inverse = Matrix.translation(5, -3, 2).inverse();
        var p = point(2, 1, 7);
        assertThat(inverse.times(p)).isEqualTo(point(-3, 4, 5));
    }
}
