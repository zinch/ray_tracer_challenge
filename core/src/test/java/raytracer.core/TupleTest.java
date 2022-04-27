package raytracer.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class TupleTest {
    @Test
    void newPoint_creates_a_tuple_with_w_1() {
        var t = Tuple.point(4.3, -4.2, 3.1);
        assertThat(t.x).isEqualTo(4.3);
        assertThat(t.y).isEqualTo(-4.2);
        assertThat(t.z).isEqualTo(3.1);
        assertThat(t.w).isEqualTo(1.0);
        assertThat(t).isEqualTo(new Tuple(4.3, -4.2, 3.1, 1.0));
    }

    @Test
    void newVector_creates_a_tuple_with_w_0() {
        var t = Tuple.vector(4.3, -4.2, 3.1);
        assertThat(t.x).isEqualTo(4.3);
        assertThat(t.y).isEqualTo(-4.2);
        assertThat(t.z).isEqualTo(3.1);
        assertThat(t.w).isEqualTo(0.0);
        assertThat(t).isEqualTo(new Tuple(4.3, -4.2, 3.1, 0.0));
    }

    @Test
    void adding_two_tuples() {
        var x = Tuple.point(3, -2, 5);
        var y = Tuple.vector(-2, 3, 1);
        assertThat(x.plus(y)).isEqualTo(new Tuple(1, 1, 6, 1));
    }

    @Test
    void prevent_adding_two_points() {
        var x = Tuple.point(1, 2, 3);
        var y = Tuple.point(1, 2, 3);
        assertThatThrownBy(() -> x.plus(y)).isInstanceOf(Tuple.IllegalOperation.class);
    }

    @Test
    void subtracting_two_points() {
        var p1 = Tuple.point(3, 2, 1);
        var p2 = Tuple.point(5, 6, 7);
        assertThat(p1.minus(p2)).isEqualTo(Tuple.vector(-2, -4, -6));
    }

    @Test
    void subtracting_vector_from_a_point() {
        var p = Tuple.point(3, 2, 1);
        var v = Tuple.vector(5, 6, 7);
        assertThat(p.minus(v)).isEqualTo(Tuple.point(-2, -4, -6));
    }

    @Test
    void subtracting_two_vectors() {
        var v1 = Tuple.vector(3, 2, 1);
        var v2 = Tuple.vector(5, 6, 7);
        assertThat(v1.minus(v2)).isEqualTo(Tuple.vector(-2, -4, -6));
    }

    @Test
    void prevent_subtracting_point_from_a_vector() {
        var p = Tuple.point(1, 2, 3);
        var v = Tuple.vector(1, 0, 0);
        assertThatThrownBy(() -> v.minus(p)).isInstanceOf(Tuple.IllegalOperation.class);
    }
}
