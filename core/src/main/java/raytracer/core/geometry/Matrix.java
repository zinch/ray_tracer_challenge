package raytracer.core.geometry;

import raytracer.utils.MathUtils;

import java.util.Arrays;

public record Matrix(double[][] values) {
    public Matrix(double[][] values) {
        var M = values.length;
        var N = values[0].length;
        if (N != M) {
            throw new IllegalArgumentException("Must provide a square matrix!");
        }
        this.values = new double[N][M];
        for (int i = 0; i < N; i++) {
            this.values[i] = Arrays.copyOf(values[i], N);
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
