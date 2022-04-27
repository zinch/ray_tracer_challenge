package raytracer.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static raytracer.core.Tuple.vector;

class VectorTest {

    @Test
    void creating_a_vector() {
        var t = vector(4.3, -4.2, 3.1);
        assertThat(t.x).isEqualTo(4.3);
        assertThat(t.y).isEqualTo(-4.2);
        assertThat(t.z).isEqualTo(3.1);
        assertThat(t.w).isEqualTo(0.0);
        assertThat(t).isEqualTo(new Vector(4.3, -4.2, 3.1));
    }

    @Test
    void adding_two_vectors() {
        var v1 = vector(1, 2, 4);
        var v2 = vector(0, 1, 10);
        assertThat(v1.plus(v2)).isEqualTo(vector(1, 3, 14));
    }

    @Test
    void subtracting_two_vectors() {
        var v1 = vector(3, 2, 1);
        var v2 = vector(5, 6, 7);
        assertThat(v1.minus(v2)).isEqualTo(vector(-2, -4, -6));
    }

    @Test
    void negating_a_vector() {
        var v = vector(1, -2, 3);
        assertThat(v.negate()).isEqualTo(vector(-1, 2, -3));
    }
}