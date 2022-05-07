package raytracer;

import raytracer.core.export.CanvasToPpmConverter;
import raytracer.core.geometry.Matrix;
import raytracer.core.geometry.Point;
import raytracer.core.graphics.Canvas;
import raytracer.core.graphics.Color;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Clock {
    public static void main(String[] args) {
        int size = 400;
        var canvas = new Canvas(size, size);
        var color = new Color(1, 0, 1);
        var p = new Point(0, 1, 0);
        /*
            Clock coordinate system:
            Y
            ^    +Z
            |    /
            |   /
            |  /
            | /
            -----------> X

            Canvas coordinate system:
            -----------> X
            |
            |
            |
            |
            Y
         */
        drawAxis(size, size, canvas);
        for (int i = 0; i < 12; i++) {
            var hourRotation = Matrix.IDENTITY
                    .rotateZ(-i * Math.PI / 6)
                    // Flip y, because Y points down the screen
                    .scale(size * 3.0 / 8, -size * 3.0 / 8, 0)
                    // Move center of the clock to the center of the canvas
                    .translate(size / 2.0, size / 2.0, 0);

            var transformed = hourRotation.times(p);
            canvas.writePixelAt(color, transformed.x(), transformed.y());
        }
        var ppm = CanvasToPpmConverter.convert(canvas);
        try {
            var filePath = Path.of(System.getProperty("user.home"), "clock.ppm");
            Files.writeString(filePath, ppm);
            System.out.println("Clock saved to: " + filePath);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void drawAxis(int width, int height, Canvas canvas) {
        var green = new Color(0, 1, 0.3);
        // Center
        canvas.writePixelAt(green, width / 2, height / 2);
        // X axis
        for (int x = 0, step = (int) (width * 0.1); x < width; x += step) {
            canvas.writePixelAt(green, x, height / 2);
        }
        canvas.writePixelAt(green, width - 1, height / 2);
        // Y axis
        for (int y = 0, step = (int) (height * 0.1); y < height; y += step) {
            canvas.writePixelAt(green, width / 2, y);
        }
        canvas.writePixelAt(green, width / 2, height - 1);
    }
}
