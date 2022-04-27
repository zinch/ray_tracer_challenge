package raytracer.core;

public class Tuple {
    public final double x;
    public final double y;
    public final double z;
    public final double w;

    private Tuple(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public static Tuple newPoint(double x, double y, double z) {
        return new Tuple(x, y, z, 1.0);
    }

    public static Tuple newVector(double x, double y, double z) {
        return new Tuple(x, y, z, 0.0);
    }
}
