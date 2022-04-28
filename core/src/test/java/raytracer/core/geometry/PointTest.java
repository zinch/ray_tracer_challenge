package raytracer.core.geometry;

import org.junit.jupiter.api.Test;
import raytracer.core.geometry.Point;

import static org.assertj.core.api.Assertions.*;
import static raytracer.core.geometry.Tuple.point;
import static raytracer.core.geometry.Tuple.vector;

class PointTest {
    @Test
    void creating_a_point() {
        var t = point(4.3, -4.2, 3.1);
        assertThat(t.x).isEqualTo(4.3);
        assertThat(t.y).isEqualTo(-4.2);
        assertThat(t.z).isEqualTo(3.1);
        assertThat(t.w).isEqualTo(1.0);
        assertThat(t).isEqualTo(new Point(4.3, -4.2, 3.1));
    }

    @Test
    void adding_vector_to_a_point() {
        var x = point(3, -2, 5);
        var y = vector(-2, 3, 1);
        assertThat(x.plus(y)).isEqualTo(new Point(1, 1, 6));
    }

    @Test
    void subtracting_two_points() {
        var p1 = point(3, 2, 1);
        var p2 = point(5, 6, 7);
        assertThat(p1.minus(p2)).isEqualTo(vector(-2, -4, -6));
    }

    @Test
    void subtracting_vector_from_a_point() {
        var p = point(3, 2, 1);
        var v = vector(5, 6, 7);
        assertThat(p.minus(v)).isEqualTo(point(-2, -4, -6));
    }
}
