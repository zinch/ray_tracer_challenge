package raytracer.core.geometry;

import raytracer.utils.MathUtils;

public sealed interface ProtoTuple permits Point, Vector {
    double x();

    double y();

    double z();

    double w();

    default boolean isEqualTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var point = (ProtoTuple) o;
        return MathUtils.areEqual(point.x(), x()) &&
                MathUtils.areEqual(point.y(), y()) &&
                MathUtils.areEqual(point.z(), z()) &&
                MathUtils.areEqual(point.w(), w());
    }
}
