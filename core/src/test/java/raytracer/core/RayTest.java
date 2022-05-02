package raytracer.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static raytracer.core.geometry.Tuple.point;
import static raytracer.core.geometry.Tuple.vector;

class RayTest {
    @Test
    void creating_and_querying_a_ray() {
        var origin = point(1, 2, 3);
        var direction = vector(4, 5, 6);
        var ray = new Ray(origin, direction);
        assertThat(ray.origin()).isEqualTo(point(1, 2, 3));
        assertThat(ray.direction()).isEqualTo(vector(4, 5, 6));
    }
}