package raytracer.core.graphics;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CanvasTest {
    @Test
    void creating_a_canvas() {
        var c = new Canvas(10, 20);
        assertThat(c.width).isEqualTo(10);
        assertThat(c.height).isEqualTo(20);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                assertThat(c.colorAt(i, j)).isEqualTo(new Color(0, 0, 0));
            }
        }
    }
}