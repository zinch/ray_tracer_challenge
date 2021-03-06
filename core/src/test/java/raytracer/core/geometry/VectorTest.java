package raytracer.core.geometry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class VectorTest {

    @Test
    void creating_a_vector() {
        var t = new Vector(4.3, -4.2, 3.1);
        assertThat(t.x()).isEqualTo(4.3);
        assertThat(t.y()).isEqualTo(-4.2);
        assertThat(t.z()).isEqualTo(3.1);
        assertThat(t.w()).isEqualTo(0.0);
        assertThat(t).isEqualTo(new Vector(4.3, -4.2, 3.1));
    }

    @Test
    void adding_two_vectors() {
        var v1 = new Vector(1, 2, 4);
        var v2 = new Vector(0, 1, 10);
        assertThat(v1.plus(v2)).isEqualTo(new Vector(1, 3, 14));
    }

    @Test
    void subtracting_two_vectors() {
        var v1 = new Vector(3, 2, 1);
        var v2 = new Vector(5, 6, 7);
        assertThat(v1.minus(v2)).isEqualTo(new Vector(-2, -4, -6));
    }

    @Test
    void negating_a_vector() {
        var v = new Vector(1, -2, 3);
        assertThat(v.negate()).isEqualTo(new Vector(-1, 2, -3));
    }

    @Test
    void multiply_by_scalar() {
        var v = new Vector(1, -2, 3);
        assertThat(v.times(3.5)).isEqualTo(new Vector(3.5, -7.0, 10.5));
    }

    @Test
    void multiply_by_fraction() {
        var v = new Vector(1, -2, 3);
        assertThat(v.times(0.5)).isEqualTo(new Vector(0.5, -1, 1.5));
    }

    @Test
    void divide_by_scalar() {
        var v = new Vector(1, -2, 3);
        assertThat(v.divide(2)).isEqualTo(new Vector(0.5, -1, 1.5));
    }

    @ParameterizedTest
    @MethodSource("vectorsWithDifferentMagnitude")
    void compute_magnitude(Vector v, double expectedMagnitude) {
        assertThat(v.magnitude()).isEqualTo(expectedMagnitude);
    }

    static Stream<Arguments> vectorsWithDifferentMagnitude() {
        return Stream.of(
                Arguments.of(new Vector(1, 0, 0), 1.0),
                Arguments.of(new Vector(0, 1, 0), 1.0),
                Arguments.of(new Vector(0, 0, 1), 1.0),
                Arguments.of(new Vector(1, 2, 3), Math.sqrt(14)),
                Arguments.of(new Vector(-1, -2, -3), Math.sqrt(14))
        );
    }

    @ParameterizedTest
    @MethodSource("vectorsForNormalization")
    void normalize(Vector v, Vector normalized) {
        assertThat(v.normalize()).isEqualTo(normalized);
        assertThat(v.normalize().magnitude()).isEqualTo(1.0);
    }

    static Stream<Arguments> vectorsForNormalization() {
        return Stream.of(
                Arguments.of(new Vector(4, 0, 0), new Vector(1, 0, 0)),
                Arguments.of(new Vector(1, 2, 3), new Vector(0.26726, 0.53452, 0.80178))
        );
    }

    @Test
    void dot_product() {
        var v1 = new Vector(1, 2, 3);
        var v2 = new Vector(2, 3, 4);
        assertThat(v1.dot(v2)).isEqualTo(20);
    }

    @Test
    void cross_product() {
        var v1 = new Vector(1, 2, 3);
        var v2 = new Vector(2, 3, 4);
        assertThat(v1.cross(v2)).isEqualTo(new Vector(-1, 2, -1));
        assertThat(v2.cross(v1)).isEqualTo(new Vector(1, -2, 1));
    }

    @Test
    void reflecting_a_vector_approaching_45_deg() {
        var v = new Vector(1, -1, 0);
        var n = new Vector(0, 1, 0);
        assertThat(v.reflect(n)).isEqualTo(new Vector(1, 1, 0));
    }

    @Test
    void reflecting_a_vector_off_a_slanted_surface() {
        var v = new Vector(0, -1, 0);
        var n = new Vector(Math.sqrt(2) / 2, Math.sqrt(2) / 2, 0);
        assertThat(v.reflect(n)).isEqualTo(new Vector(1, 0, 0));
    }
}