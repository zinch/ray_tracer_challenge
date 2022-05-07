import raytracer.core.Ray;
import raytracer.core.export.CanvasToPpmConverter;
import raytracer.core.geometry.Point;
import raytracer.core.geometry.Sphere;
import raytracer.core.graphics.Canvas;
import raytracer.core.graphics.Color;
import raytracer.core.light.Material;
import raytracer.core.light.PointLight;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static raytracer.core.geometry.Matrix.scaling;

public class SphereProjection {
    public static void main(String[] args) {
        int size = 600;
        var canvas = new Canvas(size, size);
        var wallZ = 10.0;
        var wallSize = 20.0;
        var sphere = makeSphere();
        var light = makeLight();

        var pixelSize = wallSize / size;
        var half = wallSize / 2;

        var rayPosition = new Point(0, 0, -5);
        for (int y = 0; y < size; y++) {
            // Compute the world  coordinate (top = +half, bottom = -half)
            var worldY = half - pixelSize * y;
            for (int x = 0; x < size; x++) {
                // Compute the world coordinates (left = -half, right = +half)
                var worldX = -half + pixelSize * x;
                var position = new Point(worldX, worldY, wallZ);
                var rayDirection = position.minus(rayPosition).normalize();
                var ray = new Ray(rayPosition, rayDirection);
                var xs = ray.intersect(sphere);
                if (xs.count() > 0) {
                    var hit = xs.hit().orElseThrow();
                    var point = ray.positionAt(hit.t);
                    var normal = hit.object.normalAt(point);
                    var eye = rayDirection.negate();

                    var color = light.lightningAt(point, hit.object.material(), normal, eye);
                    canvas.writePixelAt(color, x, y);
                }
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

    private static Sphere makeSphere() {
        var color = new Color(1, 0.25, 0.85);
        var material = Material.builder().color(color).build();
        var transform = scaling(1.5, 0.6, 1.5).rotateX(-Math.PI / 8);
        return new Sphere(transform, material);
    }

    private static PointLight makeLight() {
        var lightPosition = new Point(-10, 10, -10);
        return new PointLight(lightPosition, Color.WHITE);
    }
}
