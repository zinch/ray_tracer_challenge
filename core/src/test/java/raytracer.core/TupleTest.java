package raytracer.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class TupleTest {
    @Test
    void newPoint_creates_a_tuple_with_w_1() {
        var t = Tuple.newPoint(4.3, -4.2, 3.1);
        assertThat(t.x).isEqualTo(4.3);
        assertThat(t.y).isEqualTo(-4.2);
        assertThat(t.z).isEqualTo(3.1);
        assertThat(t.w).isEqualTo(1.0);
        assertThat(t).isEqualTo(new Tuple(4.3, -4.2, 3.1, 1.0));
    }

    @Test
    void newVector_creates_a_tuple_with_w_0() {
        var t = Tuple.newVector(4.3, -4.2, 3.1);
        assertThat(t.x).isEqualTo(4.3);
        assertThat(t.y).isEqualTo(-4.2);
        assertThat(t.z).isEqualTo(3.1);
        assertThat(t.w).isEqualTo(0.0);
        assertThat(t).isEqualTo(new Tuple(4.3, -4.2, 3.1, 0.0));
    }

    @Test
    void adding_two_tuples() {
        var x = Tuple.newPoint(3, -2, 5);
        var y = Tuple.newVector(-2, 3, 1);
        assertThat(x.plus(y)).isEqualTo(new Tuple(1, 1, 6, 1));
    }

    @Test
    void prevent_adding_two_points() {
        var x = Tuple.newPoint(1, 2, 3);
        var y = Tuple.newPoint(1, 2, 3);
        assertThatThrownBy(() -> x.plus(y)).isInstanceOf(Tuple.IllegalOperation.class);
    }

    @Test
    void subtracting_two_points() {
        var p1 = Tuple.newPoint(3, 2, 1);
        var p2 = Tuple.newPoint(5, 6, 7);
        assertThat(p1.minus(p2)).isEqualTo(Tuple.newVector(-2, -4, -6));
    }

    @Test
    void subtracting_vector_from_a_point() {
        var p = Tuple.newPoint(3, 2, 1);
        var v = Tuple.newVector(5, 6, 7);
        assertThat(p.minus(v)).isEqualTo(Tuple.newPoint(-2, -4, -6));
    }

    @Test
    void subtracting_two_vectors() {
        var v1 = Tuple.newVector(3, 2, 1);
        var v2 = Tuple.newVector(5, 6, 7);
        assertThat(v1.minus(v2)).isEqualTo(Tuple.newVector(-2, -4, -6));
    }

    @Test
    void prevent_subtracting_point_from_a_vector() {
        var p = Tuple.newPoint(1, 2, 3);
        var v = Tuple.newVector(1, 0, 0);
        assertThatThrownBy(() -> v.minus(p)).isInstanceOf(Tuple.IllegalOperation.class);
    }
}
