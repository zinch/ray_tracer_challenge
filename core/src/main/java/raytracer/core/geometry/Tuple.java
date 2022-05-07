package raytracer.core.geometry;

import raytracer.utils.MathUtils;

import java.util.Objects;

public class Tuple {
    public final double x;
    public final double y;
    public final double z;
    public final double w;

    Tuple(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public static Point point(double x, double y, double z) {
        return new Point(x, y, z);
    }

    public static Vector vector(double x, double y, double z) {
        return new Vector(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple tuple = (Tuple) o;
        return MathUtils.areEqual(tuple.x, x) &&
                MathUtils.areEqual(tuple.y, y) &&
                MathUtils.areEqual(tuple.z, z) &&
                Double.compare(tuple.w, w) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ", " + w + ')';
    }
}
