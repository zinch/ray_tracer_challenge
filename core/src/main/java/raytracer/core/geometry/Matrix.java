package raytracer.core.geometry;

import raytracer.utils.MathUtils;

import java.util.Arrays;

public record Matrix(double[][] values) {
    public Matrix(double[][] values) {
        validate(values);
        var M = values.length;
        var N = values[0].length;
        this.values = new double[N][M];
        for (int i = 0; i < N; i++) {
            this.values[i] = Arrays.copyOf(values[i], N);
        }
    }

    private void validate(double[][] values) {
        var M = values.length;
        if (M == 0) {
            throw new IllegalArgumentException("Must provide a square matrix!");
        }
        var N = values[0].length;
        if (N != M) {
            throw new IllegalArgumentException("Must provide a square matrix!");
        }
        for (int i = 1; i < M; i++) {
            if (values[i].length != N) {
                throw new IllegalArgumentException("Must provide a square matrix!");
            }
        }
    }

    public double at(int i, int j) {
        return values[i][j];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        var M = values.length;
        var N = values[0].length;
        if (M != matrix.values.length || N != matrix.values[0].length) {
            return false;
        }
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (!MathUtils.areEqual(at(i, j), matrix.at(i, j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
