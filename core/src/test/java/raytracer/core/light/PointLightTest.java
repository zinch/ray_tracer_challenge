package raytracer.core.light;

import org.junit.jupiter.api.Test;
import raytracer.core.graphics.Color;

import static org.assertj.core.api.Assertions.assertThat;
import static raytracer.core.geometry.Tuple.point;

class PointLightTest {
    @Test
    void a_point_light_has_a_position_and_intensity() {
        var intensity = new Color(1, 1, 1);
        var position = point(0, 0, 0);

        var light = new PointLight(position, intensity);

        assertThat(light.position()).isEqualTo(point(0, 0, 0));
        assertThat(light.intensity()).isEqualTo(new Color(1, 1, 1));
    }
}