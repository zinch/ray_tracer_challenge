package raytracer.core.graphics;

import java.util.Arrays;

public class Canvas {
    public final int width;
    public final int height;

    final Color[] pixels;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new Color[width * height];
        fill(new Color(0, 0, 0));
    }

    public Color pixelAt(int x, int y) {
        return pixels[idx(x, y)];
    }

    public void writePixelAt(Color c, int x, int y) {
        pixels[idx(x, y)] = c;
    }

    public void writePixelAt(Color c, double x, double y) {
        pixels[idx((int) Math.floor(x), (int) Math.floor(y))] = c;
    }

    private int idx(int x, int y) {
        return x + width * y;
    }

    public void fill(Color color) {
        Arrays.fill(pixels, color);
    }
}
