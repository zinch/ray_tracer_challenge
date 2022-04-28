package raytracer.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ColorTest {
    @Test
    void color_is_a_red_green_blue_tuple() {
        var c = Color.rgb(0.5, 0.4, 0.7);
        assertThat(c.red()).isEqualTo(0.5);
        assertThat(c.green()).isEqualTo(0.4);
        assertThat(c.blue()).isEqualTo(0.7);
    }

    @Test
    void adding_colors() {
        var c1 = Color.rgb(0.9, 0.6, 0.75);
        var c2 = Color.rgb(0.7, 0.1, 0.25);
        assertThat(c1.plus(c2)).isEqualTo(Color.rgb(1.6, 0.7, 1.0));
    }

    @Test
    void subtracting_colors() {
        var c1 = Color.rgb(0.9, 0.6, 0.75);
        var c2 = Color.rgb(0.7, 0.1, 0.25);
        assertThat(c1.minus(c2)).isEqualTo(Color.rgb(0.2, 0.5, 0.5));
    }
}