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
}