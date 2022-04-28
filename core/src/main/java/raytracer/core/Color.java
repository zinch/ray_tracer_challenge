package raytracer.core;

public final class Color extends Tuple {
    private Color(double red, double green, double blue) {
        super(red, green, blue, 0);
    }

    public static Color rgb(double red, double green, double blue) {
        return new Color(red, green, blue);
    }

    public double red() {
        return x;
    }

    public double green() {
        return y;
    }

    public double blue() {
        return z;
    }

    public Color plus(Color c) {
        return new Color(x + c.x, y + c.y, z + c.z);
    }

    public Color minus(Color c) {
        return new Color(x - c.x, y - c.y, z - c.z);
    }

    @Override
    public String toString() {
        return "rgb(" + x + ", " + y + ", " + z + ")";
    }
}
