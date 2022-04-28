package raytracer.core.graphics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CanvasTest {
    private Canvas canvas;
    private final static int WIDTH = 10;
    private final static int HEIGHT = 20;

    @BeforeEach
    void setUp() {
        canvas = new Canvas(WIDTH, HEIGHT);
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

    @ParameterizedTest
    @MethodSource("pixelCoordinates")
    void writing_pixels_to_canvas(int x, int y) {
        var red = new Color(1, 0, 0);
        canvas.writePixelAt(red, x, y);
        assertThat(canvas.pixelAt(x, y)).isEqualTo(red);
    }

    static Stream<Arguments> pixelCoordinates() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(WIDTH - 1, HEIGHT - 1)
        );
    }
}