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

        var p = new Point(1, 2, 3);
        assertThat(m.times(p)).isEqualTo(new Point(18, 24, 33));
    }

    @Test
    void multiplying_matrix_by_identity_matrix() {
        var m = new Matrix(matrixValues4x4);
        var identity = new Matrix(identityMatrix4x4);
        assertThat(m.times(identity)).isEqualTo(new Matrix(matrixValues4x4));
    }

    @Test
    void multiplying_point_by_identity_matrix() {
        var p = new Point(1, 2, 3);
        var identity = new Matrix(identityMatrix4x4);
        assertThat(identity.times(p)).isEqualTo(new Point(1, 2, 3));
    }

    @Test
    void transposing_a_matrix() {
        var m = new Matrix(new double[][]{
                {0, 9, 3, 0},
                {9, 8, 0, 8},
                {1, 8, 5, 3},
                {0, 0, 5, 8}
        });
        assertThat(m.transpose()).isEqualTo(new Matrix(new double[][]{
                {0, 9, 1, 0},
                {9, 8, 8, 0},
                {3, 0, 5, 5},
                {0, 8, 3, 8}
        }));
    }

    @Test
    void transposing_identity_matrix() {
        assertThat(new Matrix(identityMatrix4x4).transpose()).isEqualTo(new Matrix(identityMatrix4x4));
    }

    @Test
    void submatrix_of_a_3x3_matrix_is_a_2x2_matrix() {
        var m = new Matrix(new double[][]{
                {1, 5, 0},
                {-3, 2, 7},
                {0, 6, -3}
        });
        assertThat(m.submatrix(0, 2)).isEqualTo(new Matrix(new double[][]{
                {-3, 2},
                {0, 6}
        }));
    }

    @Test
    void submatrix_of_a_4x4_matrix_is_a_3x3_matrix() {
        var m = new Matrix(new double[][]{
                {-6, 1, 1, 6},
                {-8, 5, 8, 6},
                {-1, 0, 8, 2},
                {-7, 1, -1, 1}
        });
        assertThat(m.submatrix(2, 1)).isEqualTo(new Matrix(new double[][]{
                {-6, 1, 6},
                {-8, 8, 6},
                {-7, -1, 1}
        }));
    }

    @Test
    void calculating_determinant_of_2x2_matrix() {
        var m = new Matrix(new double[][]{
                {1, 5},
                {-3, 2}
        });

        assertThat(m.determinant()).isEqualTo(17);
    }

    @Test
    void calculating_a_minor_of_a_3x3_matrix() {
        var m = new Matrix(new double[][]{
                {3, 5, 0},
                {2, -1, -7},
                {6, -1, 5}
        });
        assertThat(m.minor(1, 0)).isEqualTo(25);
    }

    @Test
    void calculating_a_cofactor_of_a_3x3_matrix() {
        var m = new Matrix(new double[][]{
                {3, 5, 0},
                {2, -1, -7},
                {6, -1, 5}
        });
        assertThat(m.minor(0, 0)).isEqualTo(-12);
        assertThat(m.cofactor(0, 0)).isEqualTo(-12);
        assertThat(m.minor(1, 0)).isEqualTo(25);
        assertThat(m.cofactor(1, 0)).isEqualTo(-25);
    }

    @Test
    void calculating_the_determinant_of_a_3x3_matrix() {
        var m = new Matrix(new double[][]{
                {1, 2, 6},
                {-5, 8, -4},
                {2, 6, 4}
        });
        assertThat(m.cofactor(0, 0)).isEqualTo(56);
        assertThat(m.cofactor(0, 1)).isEqualTo(12);
        assertThat(m.cofactor(0, 2)).isEqualTo(-46);
        assertThat(m.determinant()).isEqualTo(-196);
    }

    @Test
    void calculating_the_determinant_of_a_4x4_matrix() {
        var m = new Matrix(new double[][]{
                {-2, -8, 3, 5},
                {-3, 1, 7, 3},
                {1, 2, -9, 6},
                {-6, 7, 7, -9}
        });
        assertThat(m.cofactor(0, 0)).isEqualTo(690);
        assertThat(m.cofactor(0, 1)).isEqualTo(447);
        assertThat(m.cofactor(0, 2)).isEqualTo(210);
        assertThat(m.cofactor(0, 3)).isEqualTo(51);
        assertThat(m.determinant()).isEqualTo(-4071);
    }

    @Test
    void testing_an_invertible_matrix_for_invertibility() {
        var m = new Matrix(new double[][]{
                {6, 4, 4, 4},
                {5, 5, 7, 6},
                {4, -9, 3, -7},
                {9, 1, 7, -6}
        });
        assertThat(m.determinant()).isEqualTo(-2120);
        assertThat(m.isInvertible()).isTrue();
    }

    @Test
    void testing_a_noninvertible_matrix_for_invertibility() {
        var m = new Matrix(new double[][]{
                {-4, 2, -2, -3},
                {9, 6, 2, 6},
                {0, -5, 1, -5},
                {0, 0, 0, 0}
        });
        assertThat(m.determinant()).isEqualTo(0);
        assertThat(m.isInvertible()).isFalse();
    }

    @Test
    void calculating_the_inverse_of_a_matrix() {
        var m = new Matrix(new double[][]{
                {-5, 2, 6, -8},
                {1, -5, 1, 8},
                {7, 7, -6, -7},
                {1, -3, 7, 4}
        });
        assertThat(m.determinant()).isEqualTo(532);
        assertThat(m.cofactor(2, 3)).isEqualTo(-160);

        var inverse = m.inverse();
        assertThat(inverse.at(3, 2)).isEqualTo(-160.0 / 532);

        assertThat(m.cofactor(3, 2)).isEqualTo(105);
        assertThat(inverse.at(2, 3)).isEqualTo(105.0 / 532);

        assertThat(inverse).isEqualTo(new Matrix(new double[][]{
                {0.21805, 0.45113, 0.24060, -0.04511},
                {-0.80827, -1.45677, -0.44361, 0.52068},
                {-0.07895, -0.22368, -0.05263, 0.19737},
                {-0.52256, -0.81391, -0.30075, 0.30639}
        }));
    }

    @Test
    void calculating_the_inverse_of_another_matrix() {
        var m = new Matrix(new double[][]{
                {8, -5, 9, 2},
                {7, 5, 6, 1},
                {-6, 0, 9, 6},
                {-3, 0, -9, -4}
        });
        assertThat(m.inverse()).isEqualTo(new Matrix(new double[][]{
                {-0.15385, -0.15385, -0.28205, -0.53846},
                {-0.07692, 0.12308, 0.02564, 0.03077},
                {0.35897, 0.35897, 0.43590, 0.92308},
                {-0.69231, -0.69231, -0.76923, -1.92308}
        }));
    }

    @Test
    void calculating_the_inverse_of_the_third_matrix() {
        var m = new Matrix(new double[][]{
                {9, 3, 0, 9},
                {-5, -2, -6, -3},
                {-4, 9, 6, 4},
                {-7, 6, 6, 2}
        });
        assertThat(m.inverse()).isEqualTo(new Matrix(new double[][]{
                {-0.04074, -0.07778, 0.14444, -0.22222},
                {-0.07778, 0.03333, 0.36667, -0.33333},
                {-0.02901, -0.14630, -0.10926, 0.12963},
                {0.17778, 0.06667, -0.26667, 0.33333}
        }));
    }

    @Test
    void multiplying_a_product_by_its_inverse() {
        var A = new Matrix(new double[][]{
                {3, -9, 7, 3},
                {3, -8, 2, -9},
                {-4, 4, 4, 1},
                {-6, 5, -1, 1}
        });
        var B = new Matrix(new double[][]{
                {8, 2, 2, 2},
                {3, -1, 7, 0},
                {7, 0, 5, 4},
                {6, -2, 0, 5}
        });
        var C = A.times(B);
        assertThat(C.times(B.inverse())).isEqualTo(A);
    }
}