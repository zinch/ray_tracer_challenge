package raytracer.core.graphics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CanvasTest {
    private Canvas canvas;

    @BeforeEach
    void setUp() {
        canvas = new Canvas(10, 20);
    }

    @Test
    void creating_a_canvas() {
        assertThat(canvas.width).isEqualTo(10);
        assertThat(canvas.height).isEqualTo(20);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                assertThat(canvas.pixelAt(i, j)).isEqualTo(new Color(0, 0, 0));
            }
        }
    }

    @Test
    void writing_pixels_to_canvas() {
        var red = new Color(1, 0, 0);
        canvas.writePixelAt(red, 2, 3);
        assertThat(canvas.pixelAt(2, 3)).isEqualTo(red);
    }
}