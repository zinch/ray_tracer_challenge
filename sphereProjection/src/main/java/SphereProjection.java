import raytracer.core.Ray;
import raytracer.core.export.CanvasToPpmConverter;
import raytracer.core.geometry.Matrix;
import raytracer.core.geometry.Point;
import raytracer.core.geometry.Sphere;
import raytracer.core.graphics.Canvas;
import raytracer.core.graphics.Color;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static raytracer.core.geometry.Matrix.identity;
import static raytracer.core.geometry.Matrix.scaling;
import static raytracer.core.geometry.Tuple.point;
import static raytracer.core.geometry.Tuple.vector;

public class SphereProjection {
    public static void main(String[] args) {
        int size = 400;
        var canvas = new Canvas(size, size);
        var grey = new Color(0.1, 0.1, 0.1);
        var green = new Color(0, 0.7, 0.3);
        var wallZ = 10.0;
        var wallSize = 20.0;
        var sphere = new Sphere();
        var lightSource = point(0, 0, -5);

        var pixelSize = wallSize / size;
        var half = wallSize / 2;

        for (int y = 0; y < size; y++) {
            // Compute the world  coordinate (top = +half, bottom = -half)
            var worldY = half - pixelSize * y;
            for (int x = 0; x < size; x++) {
                // Compute the world coordinates (left = -half, right = +half)
                var worldX = -half + pixelSize * x;
                var position = point(worldX, worldY, wallZ);
                var ray = new Ray(lightSource, position.minus(lightSource).normalize());
                var xs = ray.intersect(sphere);
                var color = (xs.count() == 0) ? grey : green;
                canvas.writePixelAt(color, x, y);
            }
        }

        var ppm = CanvasToPpmConverter.convert(canvas);
        try {
            var filePath = Path.of(System.getProperty("user.home"), "sphereProjection.ppm");
            Files.writeString(filePath, ppm);
            System.out.println("Sphere projection saved to: " + filePath);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
