package raytracer.projectile;

import raytracer.core.export.CanvasToPpmConverter;
import raytracer.core.geometry.Point;
import raytracer.core.geometry.Vector;
import raytracer.core.graphics.Canvas;
import raytracer.core.graphics.Color;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) {
        System.out.println("Launching projectile!");
        var p = new Projectile(new Point(0, 1, 0), new Vector(1.7, 3.5, 0).normalize().times(6));
        var e = new Environment(new Vector(0, -0.1, 0), new Vector(-0.01, 0, 0));
        int width = 300;
        int height = 400;
        var canvas = new Canvas(width, height);
        var color = new Color(0.9, 0.1, 0.1);
        canvas.writePixelAt(color, 0, height - 1);
        while (p.position().y() > 0) {
            p = tick(e, p);
            int x = (int) Math.ceil(p.position().x());
            int y = (int) Math.ceil(p.position().y());
            if (x >= 0 && y >= 0 && x <= width && y <= height) {
                canvas.writePixelAt(color, x, height - y);
            }
        }
        System.out.println("Projectile's position: " + p.position());
        var ppm = CanvasToPpmConverter.convert(canvas);
        try {
            var filePath = Path.of(System.getProperty("user.home"), "canvas.ppm");
            Files.writeString(filePath, ppm);
            System.out.println("Projectile's trajectory saved to: " + filePath);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Projectile tick(Environment env, Projectile proj) {
        return proj.atNextPosition(env);
    }
}
