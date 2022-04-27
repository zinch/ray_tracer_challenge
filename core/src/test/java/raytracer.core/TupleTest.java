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
}
