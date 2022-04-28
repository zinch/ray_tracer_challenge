package raytracer.core.graphics;

import raytracer.utils.MathUtils;

public record Color(double red, double green, double blue) {

    public Color plus(Color c) {
        return new Color(red + c.red, green + c.green, blue + c.blue);
    }

    public Color minus(Color c) {
        return new Color(red - c.red, green - c.green, blue - c.blue);
    }

    public Color times(double scalar) {
        return new Color(red * scalar, green * scalar, blue * scalar);
    }

    public Color times(Color c) {
        return new Color(red * c.red, green * c.green, blue * c.blue);
    }

    @Override
    public String toString() {
        return "rgb(" + red + ", " + green + ", " + blue + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return MathUtils.areEqual(color.red, red) && MathUtils.areEqual(color.green, green) && MathUtils.areEqual(color.blue, blue);
    }

}
