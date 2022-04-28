package raytracer.core.graphics;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ColorTest {
    @Test
    void color_is_a_red_green_blue_tuple() {
        var c = new Color(0.5, 0.4, 0.7);
        assertThat(c.red()).isEqualTo(0.5);
        assertThat(c.green()).isEqualTo(0.4);
        assertThat(c.blue()).isEqualTo(0.7);
    }

    @Test
    void adding_colors() {
        var c1 = new Color(0.9, 0.6, 0.75);
        var c2 = new Color(0.7, 0.1, 0.25);
        assertThat(c1.plus(c2)).isEqualTo(new Color(1.6, 0.7, 1.0));
    }

    @Test
    void subtracting_colors() {
        var c1 = new Color(0.9, 0.6, 0.75);
        var c2 = new Color(0.7, 0.1, 0.25);
        assertThat(c1.minus(c2)).isEqualTo(new Color(0.2, 0.5, 0.5));
    }

    @Test
    void multiply_color_by_scalar() {
        var c = new Color(0.2, 0.3, 0.4);
        assertThat(c.times(2)).isEqualTo(new Color(0.4, 0.6, 0.8));
    }

    @Test
    void multiply_two_colors() {
        var c1 = new Color(1, 0.2, 0.4);
        var c2 = new Color(0.9, 1, 0.1);
        assertThat(c1.times(c2)).isEqualTo(new Color(0.9, 0.2, 0.04));
    }
}