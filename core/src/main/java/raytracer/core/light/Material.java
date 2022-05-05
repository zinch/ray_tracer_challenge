package raytracer.core.light;

import raytracer.core.graphics.Color;

import java.util.Objects;

public class Material {

    private final Color color;
    private final double ambient;
    private final double diffuse;
    private final double specular;
    private final double shininess;

    public final static class MaterialBuilder {

        private Color color = new Color(1, 1, 1);
        private double ambient = 0.1;
        private double diffuse = 0.9;
        private double specular = 0.9;
        private double shininess = 200;

        public Material build() {
            return new Material(color, ambient, diffuse, specular, shininess);
        }

        public MaterialBuilder color(Color value) {
            this.color = Objects.requireNonNull(value);
            return this;
        }

        public MaterialBuilder ambient(double value) {
            this.ambient = value;
            return this;
        }

        public MaterialBuilder diffuse(double value) {
            this.diffuse = value;
            return this;
        }

        public MaterialBuilder specular(double value) {
            this.specular = value;
            return this;
        }

        public MaterialBuilder shininess(double value) {
            this.shininess = value;
            return this;
        }
    }

    private Material(Color color, double ambient, double diffuse, double specular, double shininess) {
        this.color = color;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }

    public static MaterialBuilder builder() {
        return new MaterialBuilder();
    }

    public Color color() {
        return color;
    }

    public double ambient() {
        return ambient;
    }

    public double diffuse() {
        return diffuse;
    }

    public double specular() {
        return specular;
    }

    public double shininess() {
        return shininess;
    }
}
