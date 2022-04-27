package raytracer.utils;

public class MathUtils {
    private MathUtils() {}

    public static final double EPSILON = 1e-5;

    public static boolean areEqual(double v1, double v2) {
        return java.lang.Math.abs(v1 - v2) < EPSILON;
    }
}
