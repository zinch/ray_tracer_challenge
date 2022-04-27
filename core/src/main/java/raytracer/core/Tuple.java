package raytracer.core;

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

    public static Tuple point(double x, double y, double z) {
        return new Tuple(x, y, z, 1.0);
    }

    public static Tuple vector(double x, double y, double z) {
        return new Tuple(x, y, z, 0.0);
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

    public Tuple plus(Tuple o) {
        double w = this.w + o.w;
        if (w < 0 || w > 1.0) throw new IllegalOperation();
        return new Tuple(x + o.x, y + o.y, z + o.z, w);
    }

    public Tuple minus(Tuple o) {
        return plus(new Tuple(-o.x, -o.y, -o.z, -o.w));
    }

    public static final class IllegalOperation extends RuntimeException {
    }
}
