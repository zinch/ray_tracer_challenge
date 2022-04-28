package raytracer.core.export;

import org.junit.jupiter.api.Test;
import raytracer.core.graphics.Canvas;

import static org.assertj.core.api.Assertions.assertThat;

class CanvasToPpmConverterTest {
    @Test
    void constructing_ppm_header() {
        var canvas = new Canvas(5, 3);
        var ppm = CanvasToPpmConverter.convert(canvas);
        var tokens = ppm.split("\n");
        assertThat(tokens[0]).isEqualTo("P3");
        assertThat(tokens[1]).isEqualTo("5 3");
        assertThat(tokens[2]).isEqualTo("255");
    }
}