package raytracer.core.light;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import raytracer.core.geometry.Point;
import raytracer.core.geometry.Vector;
import raytracer.core.graphics.Color;

import static org.assertj.core.api.Assertions.assertThat;

class PointLightTest {
    @Test
    void a_point_light_has_a_position_and_intensity() {
        var intensity = Color.WHITE;
        var position = new Point(0, 0, 0);

        var light = new PointLight(position, intensity);

        assertThat(light.position()).isEqualTo(new Point(0, 0, 0));
        assertThat(light.intensity()).isEqualTo(Color.WHITE);
    }

    @DisplayName("Lightning")
    @Nested
    class Lightning {
        private final Material material = Material.DEFAULT;
        private final Point position = new Point(0, 0, 0);
        private final PointLight light = new PointLight(new Point(0, 0, -10), Color.WHITE);

        @Test
        void lighting_with_the_eye_between_the_light_and_the_surface() {
            var eye = new Vector(0, 0, -1);
            var normal = new Vector(0, 0, -1);

            assertThat(light.lightningAt(position, material, normal, eye))
                    .isEqualTo(new Color(1.9, 1.9, 1.9));
        }

        @Test
        void lighting_with_the_eye_between_light_and_surface_and_eye_offset_45_deg() {
            double z = -Math.sqrt(2) / 2;
            var eye = new Vector(0, Math.sqrt(2) / 2, z);
            var normal = new Vector(0, 0, -1);

            assertThat(light.lightningAt(position, material, normal, eye))
                    .isEqualTo(new Color(1.0, 1.0, 1.0));
        }

        @Test
        void lightning_with_the_eye_opposite_surface_and_light_offset_45_deg() {
            var eye = new Vector(0, 0, -1);
            var normal = new Vector(0, 0, -1);
            var light = new PointLight(new Point(0, 10, -10), Color.WHITE);

            assertThat(light.lightningAt(position, material, normal, eye))
                    .isEqualTo(new Color(0.7364, 0.7364, 0.7364));
        }

        @Test
        void lightning_with_eye_in_the_path_of_the_reflection_vector() {
            double y = -Math.sqrt(2) / 2;
            double z = -Math.sqrt(2) / 2;
            var eye = new Vector(0, y, z);
            var normal = new Vector(0, 0, -1);
            var light = new PointLight(new Point(0, 10, -10), Color.WHITE);

            assertThat(light.lightningAt(position, material, normal, eye))
                    .isEqualTo(new Color(1.6364, 1.6364, 1.6364));
        }

        @Test
        void lighting_with_the_light_behind_the_surface() {
            var eye = new Vector(0, 0, -1);
            var normal = new Vector(0, 0, -1);
            var light = new PointLight(new Point(0, 0, 10), Color.WHITE);

            assertThat(light.lightningAt(position, material, normal, eye))
                    .isEqualTo(new Color(0.1, 0.1, 0.1));
        }
    }
}