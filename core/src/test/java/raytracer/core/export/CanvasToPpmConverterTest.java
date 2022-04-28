package raytracer.core.export;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raytracer.core.graphics.Canvas;
import raytracer.core.graphics.Color;

import static org.assertj.core.api.Assertions.assertThat;

class CanvasToPpmConverterTest {
    private Canvas canvas;

    @BeforeEach
    void setUp() {
        canvas = new Canvas(5, 3);
    }

    @Test
    void constructing_ppm_header() {
        var ppm = CanvasToPpmConverter.convert(canvas);
        var tokens = ppm.split("\n");
        assertThat(tokens[0]).isEqualTo("P3");
        assertThat(tokens[1]).isEqualTo("5 3");
        assertThat(tokens[2]).isEqualTo("255");
    }

    @Test
    void constructing_ppm_pixel_data() {
        canvas.writePixelAt(new Color(1.5, 0, 0), 0, 0);
        canvas.writePixelAt(new Color(0, 0.5, 0), 2, 1);
        canvas.writePixelAt(new Color(-0.5, 0, 1), 4, 2);
        var ppm = CanvasToPpmConverter.convert(canvas);
        var tokens = ppm.split("\n");
        assertThat(tokens[3]).isEqualTo("255 0 0 0 0 0 0 0 0 0 0 0 0 0 0");
        assertThat(tokens[4]).isEqualTo("0 0 0 0 0 0 0 128 0 0 0 0 0 0 0");
        assertThat(tokens[5]).isEqualTo("0 0 0 0 0 0 0 0 0 0 0 0 0 0 255");
    }
}