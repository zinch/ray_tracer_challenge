package raytracer.core.geometry;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatrixTest {

    private final double[][] matrixValues4x4 = new double[][]{
            {1, 2, 3, 4},
            {5.5, 6.5, 7.5, 8.5},
            {9, 10, 11, 12},
            {13.5, 14.5, 15.5, 16.5}
    };

    @Test
    void constructing_4x4_matrix() {
        var matrix = new Matrix(matrixValues4x4);

        matrixValues4x4[1][1] = 100.5;
        assertThat(matrix.at(1, 1)).isEqualTo(6.5);
        assertThat(matrix.at(0, 0)).isEqualTo(1);
        assertThat(matrix.at(0, 3)).isEqualTo(4);
        assertThat(matrix.at(2, 2)).isEqualTo(11);
        assertThat(matrix.at(3, 0)).isEqualTo(13.5);
        assertThat(matrix.at(3, 2)).isEqualTo(15.5);
        assertThat(matrix.at(3, 3)).isEqualTo(16.5);
    }

    @Test
    void constructing_2x2_matrix() {
        var matrix = new Matrix(new double[][]{
                {-3, 5},
                {1, -2}
        });
        assertThat(matrix.at(0, 0)).isEqualTo(-3);
        assertThat(matrix.at(0, 1)).isEqualTo(5);
        assertThat(matrix.at(1, 0)).isEqualTo(1);
        assertThat(matrix.at(1, 1)).isEqualTo(-2);
    }

    @Test
    void constructing_3x3_matrix() {
        var matrix = new Matrix(new double[][]{
                {-3, 5, 0},
                {1, -2, -7},
                {0, 1, -1}
        });
        assertThat(matrix.at(0, 0)).isEqualTo(-3);
        assertThat(matrix.at(1, 1)).isEqualTo(-2);
        assertThat(matrix.at(2, 2)).isEqualTo(-1);
    }

    @Test
    void allow_only_square_matrices() {
        assertThatThrownBy(() ->
                new Matrix(new double[][]{
                        {1, 2},
                        {3, 4},
                        {5, 6}
                })).isInstanceOf(IllegalArgumentException.class).hasMessage("Must provide a square matrix!");
    }

    @Test
    void matrix_equality() {
        var m1 = new Matrix(matrixValues4x4);
        var m2 = new Matrix(matrixValues4x4);
        assertTrue(m1.equals(m2));
    }
}