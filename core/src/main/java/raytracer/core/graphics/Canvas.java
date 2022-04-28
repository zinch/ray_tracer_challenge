package raytracer.core.graphics;

import java.util.Arrays;

public class Canvas {
    final int width;
    final int height;

    final Color[] pixels;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new Color[width * height];
        Arrays.fill(pixels, new Color(0, 0, 0));
    }

    public Color colorAt(int x, int y) {
        return pixels[x * width + y];
    }
}
