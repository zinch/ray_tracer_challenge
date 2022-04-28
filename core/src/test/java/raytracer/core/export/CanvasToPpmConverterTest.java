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

        var lines = ppm.split("\n");
        assertThat(lines[0]).isEqualTo("P3");
        assertThat(lines[1]).isEqualTo("5 3");
        assertThat(lines[2]).isEqualTo("255");
    }

    @Test
    void constructing_ppm_pixel_data() {
        canvas.writePixelAt(new Color(1.5, 0, 0), 0, 0);
        canvas.writePixelAt(new Color(0, 0.5, 0), 2, 1);
        canvas.writePixelAt(new Color(-0.5, 0, 1), 4, 2);

        var ppm = CanvasToPpmConverter.convert(canvas);

        var lines = ppm.split("\n");
        assertThat(lines[3]).isEqualTo("255 0 0 0 0 0 0 0 0 0 0 0 0 0 0");
        assertThat(lines[4]).isEqualTo("0 0 0 0 0 0 0 128 0 0 0 0 0 0 0");
        assertThat(lines[5]).isEqualTo("0 0 0 0 0 0 0 0 0 0 0 0 0 0 255");
    }

    @Test
    void splitting_long_lines_in_ppm_files() {
        var canvas = new Canvas(10, 2);
        var color = new Color(1, 0.8, 0.6);
        canvas.fill(color);

        var ppm = CanvasToPpmConverter.convert(canvas);

        var lines = ppm.split("\n");
        assertThat(lines[3]).isEqualTo("255 204 153 255 204 153 255 204 153 255 204 153 255 204 153 255 204");
        assertThat(lines[4]).isEqualTo("153 255 204 153 255 204 153 255 204 153 255 204 153");
        assertThat(lines[5]).isEqualTo("255 204 153 255 204 153 255 204 153 255 204 153 255 204 153 255 204");
        assertThat(lines[6]).isEqualTo("153 255 204 153 255 204 153 255 204 153 255 204 153");
    }

    @Test
    void ppm_files_are_terminated_by_a_newline_character() {
        var ppm = CanvasToPpmConverter.convert(canvas);
        assertThat(String.valueOf(ppm.charAt(ppm.length() - 1))).isEqualTo(System.getProperty("line.separator"));
    }
}