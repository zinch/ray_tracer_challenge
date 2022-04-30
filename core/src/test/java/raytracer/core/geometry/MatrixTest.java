package raytracer.core.geometry;

import org.junit.jupiter.api.Test;
import raytracer.core.geometry.Matrix;

import static org.assertj.core.api.Assertions.assertThat;

class MatrixTest {
    @Test
    void constructing_a_4x4_matrix() {
        double[][] values = {
                {1, 2, 3, 4},
                {5.5, 6.5, 7.5, 8.5},
                {9, 10, 11, 12},
                {13.5, 14.5, 15.5, 16.5}
        };
        var matrix = new Matrix(values);

        values[1][1] = 100.5;
        assertThat(matrix.at(1, 1)).isEqualTo(6.5);
        assertThat(matrix.at(0, 0)).isEqualTo(1);
        assertThat(matrix.at(0, 3)).isEqualTo(4);
        assertThat(matrix.at(2, 2)).isEqualTo(11);
        assertThat(matrix.at(3, 0)).isEqualTo(13.5);
        assertThat(matrix.at(3, 2)).isEqualTo(15.5);
        assertThat(matrix.at(3, 3)).isEqualTo(16.5);

    }
}