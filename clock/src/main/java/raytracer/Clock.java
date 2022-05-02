package raytracer;

import raytracer.core.export.CanvasToPpmConverter;
import raytracer.core.graphics.Canvas;
import raytracer.core.graphics.Color;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static raytracer.core.geometry.Matrix.identity;
import static raytracer.core.geometry.Tuple.point;

public class Clock {
    public static void main(String[] args) {
        int width = 400;
        int height = 400;
        var canvas = new Canvas(width, height);
        var color = new Color(1, 0, 1);
        var p = point(0, 1, 0);
        drawAxis(width, height, canvas);
        for (int i = 0; i < 12; i++) {
            var hourRotation = identity()
                    .rotateZ(-i * Math.PI / 6)
                    .scale(height * 3.0 / 8, height * 3.0 / 8, 0);

            var transformed = hourRotation.times(p);
            try {
                var x = (int) (width / 2 + transformed.x);
                var y = (int) (height / 2 - transformed.y);
                System.out.println("" + i + ": " + transformed + ", x=" + x + ", y=" + y);
                canvas.writePixelAt(color, x, y);
//                if (i >= 1) break;
            } catch (Exception e) {
                System.err.println("" + i + ": " + e.getMessage());
            }
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
        canvas.writePixelAt(green, width / 2, height / 2);
        canvas.writePixelAt(green, width - 10, height / 2);
        canvas.writePixelAt(green, 10, height / 2);
        canvas.writePixelAt(green, width / 2, height - 10);
        canvas.writePixelAt(green, width / 2, 10);
    }
}
