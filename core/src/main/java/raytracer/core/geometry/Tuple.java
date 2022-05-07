package raytracer.core.geometry;

import raytracer.utils.MathUtils;

public sealed interface Tuple permits Point, Vector {
    double x();

    double y();

    double z();

    double w();

    default boolean isEqualTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var point = (Tuple) o;
        return MathUtils.areEqual(point.x(), x()) &&
                MathUtils.areEqual(point.y(), y()) &&
                MathUtils.areEqual(point.z(), z()) &&
                MathUtils.areEqual(point.w(), w());
    }

    default String asString() {
        return "(" + x() + ", " + y() + ", " + z() + ", " + w() + ')';
    }

    static Vector vector(double x, double y, double z) {
        return new Vector(x, y, z);
    }
}
