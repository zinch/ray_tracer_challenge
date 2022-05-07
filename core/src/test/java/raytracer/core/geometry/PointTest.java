package raytracer.core.geometry;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PointTest {
    @Test
    void creating_a_point() {
        var t = new Point(4.3, -4.2, 3.1);
        assertThat(t.x()).isEqualTo(4.3);
        assertThat(t.y()).isEqualTo(-4.2);
        assertThat(t.z()).isEqualTo(3.1);
        assertThat(t.w()).isEqualTo(1.0);
        assertThat(t).isEqualTo(new Point(4.3, -4.2, 3.1));
    }

    @Test
    void adding_vector_to_a_point() {
        var x = new Point(3, -2, 5);
        var y = new Vector(-2, 3, 1);
        assertThat(x.plus(y)).isEqualTo(new Point(1, 1, 6));
    }

    @Test
    void subtracting_two_points() {
        var p1 = new Point(3, 2, 1);
        var p2 = new Point(5, 6, 7);
        assertThat(p1.minus(p2)).isEqualTo(new Vector(-2, -4, -6));
    }

    @Test
    void subtracting_vector_from_a_point() {
        var p = new Point(3, 2, 1);
        var v = new Vector(5, 6, 7);
        assertThat(p.minus(v)).isEqualTo(new Point(-2, -4, -6));
    }
}
