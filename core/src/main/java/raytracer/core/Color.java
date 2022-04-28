package raytracer.core;

public final class Color {
    private final double red;
    private final double green;
    private final double blue;

    private Color(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static Color rgb(double red, double green, double blue) {
        return new Color(red, green, blue);
    }

    public double red() {
        return red;
    }

    public double green() {
        return green;
    }

    public double blue() {
        return blue;
    }
}
