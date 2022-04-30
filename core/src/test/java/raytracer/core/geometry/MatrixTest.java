package raytracer.core.geometry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static raytracer.core.geometry.Tuple.point;

class MatrixTest {

    private double[][] matrixValues4x4;
    private double[][] identityMatrix4x4;

    @BeforeEach
    void setUp() {
        matrixValues4x4 =
                new double[][]{
                        {1, 2, 3, 4},
                        {5.5, 6.5, 7.5, 8.5},
                        {9, 10, 11, 12},
                        {13.5, 14.5, 15.5, 16.5}
                };
        identityMatrix4x4 = new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
    }

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

    @ParameterizedTest
    @MethodSource("invalidMatrixValues")
    void allow_only_square_matrices(double[][] values) {
        assertThatThrownBy(() ->
                new Matrix(values)).isInstanceOf(IllegalArgumentException.class).hasMessage("Must provide a square matrix!");
    }

    static Stream<Arguments> invalidMatrixValues() {
        return Stream.of(
                Arguments.of((Object) new double[][]{}),
                Arguments.of((Object) new double[][]{{1, 2}, {3, 4}, {5, 6}}),
                Arguments.of((Object) new double[][]{{1, 2, 10}, {3, 4}, {5, 6}})
        );
    }

    @Test
    void matrix_equality() {
        var m1 = new Matrix(matrixValues4x4);
        var m2 = new Matrix(matrixValues4x4);
        assertEquals(m1, m2);
    }

    @Test
    void matrix_inequality() {
        var m1 = new Matrix(matrixValues4x4);
        matrixValues4x4[2][2] += 0.01;
        var m2 = new Matrix(matrixValues4x4);
        assertNotEquals(m1, m2);
    }

    @Test
    void multiplying_two_2x2_matrices() {
        var m1 = new Matrix(new double[][]{
                {1, 2},
                {3, 4}
        });
        var m2 = new Matrix(new double[][]{
                {-2, 1},
                {1.5, -0.5}
        });

        assertThat(m1.times(m2)).isEqualTo(new Matrix(new double[][]{
                {1, 0},
                {0, 1}
        }));
    }

    @Test
    void multiplying_two_4x4_matrices() {
        var m1 = new Matrix(new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 8, 7, 6},
                {5, 4, 3, 2}
        });
        var m2 = new Matrix(new double[][]{
                {-2, 1, 2, 3},
                {3, 2, 1, -1},
                {4, 3, 6, 5},
                {1, 2, 7, 8}
        });

        assertThat(m1.times(m2)).isEqualTo(new Matrix(new double[][]{
                {20, 22, 50, 48},
                {44, 54, 114, 108},
                {40, 58, 110, 102},
                {16, 26, 46, 42}
        }));
    }

    @Test
    void multiply_matrix_by_a_point() {
        var m = new Matrix(new double[][]{
                {1, 2, 3, 4},
                {2, 4, 4, 2},
                {8, 6, 4, 1},
                {0, 0, 0, 1}
        });

        var p = point(1, 2, 3);
        assertThat(m.times(p)).isEqualTo(point(18, 24, 33));
    }

    @Test
    void multiplying_matrix_by_identity_matrix() {
        var m = new Matrix(matrixValues4x4);
        var identity = new Matrix(identityMatrix4x4);
        assertThat(m.times(identity)).isEqualTo(new Matrix(matrixValues4x4));
    }

    @Test
    void multiplying_point_by_identity_matrix() {
        var p = point(1, 2, 3);
        var identity = new Matrix(identityMatrix4x4);
        assertThat(identity.times(p)).isEqualTo(point(1, 2, 3));
    }
}